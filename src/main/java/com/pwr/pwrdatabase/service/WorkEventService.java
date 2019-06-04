package com.pwr.pwrdatabase.service;

import com.pwr.pwrdatabase.domain.DailyEmployeeReport;
import com.pwr.pwrdatabase.domain.Employee;
import com.pwr.pwrdatabase.domain.EmployeeAbsent;
import com.pwr.pwrdatabase.domain.WorkStartFinishEvent;
import com.pwr.pwrdatabase.domain.dao.EmployeeAbsentDao;
import com.pwr.pwrdatabase.domain.dao.WorkStartFinishEventDao;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.NoSuchElementException;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkEventService
{
    @Autowired private WorkStartFinishEventDao repository;
    @Autowired private EmployeeAbsentDao absentRepository;
    @Autowired private ReportService reportService;
    @Autowired private EmployeeService employeeService;

    public Long count()
    {
        return repository.count();
    }

    public Set<WorkStartFinishEvent> findAll()
    {
        return repository.findAll();
    }

    public Set<WorkStartFinishEvent> findAll(final Set<Long> IDs)
    {
        return repository.findAll(IDs);
    }

    public WorkStartFinishEvent findOne(final Long ID)
    {
        return repository.findOne(ID);
    }

    public WorkStartFinishEvent save(final WorkStartFinishEvent EVENT)
    {
        checkIfAndThrowException(EVENT == null, "Event is null.");
        checkIfAndThrowException(EVENT.getEmployee() == null, "Invalid event. Can not persist event without Employee.");

        Set<EmployeeAbsent> absents = absentRepository.findAllByEmployee(EVENT.getEmployee());
        boolean result2 = false;
        for (EmployeeAbsent a : absents)
        {
            if (LocalDate.now().isAfter(a.getAbsentStartDate()) && LocalDate.now().isBefore(a.getAbsentStartDate().plusDays(a.getAbsentDurationInDays())))
            {
                result2 = true;
            }
        }
        checkIfAndThrowException(result2, "Employee on leave can not start work.");



        EVENT.setEventDate(LocalDate.now());
        EVENT.setEventTime(LocalTime.now());

        Set<WorkStartFinishEvent> result = repository.findAllByEmployeeAndEventDate(EVENT.getEmployee(), EVENT.getEventDate());

        checkIfAndThrowException(result.size() == 0 && EVENT.isBeginning() == false, "Invalid event. can not persist initial event with beginning == false");

        checkIfAndThrowException(result.size() == 1 && EVENT.isBeginning() == true, "Invalid event. can not persist terminal event with beginning == true");

        checkIfAndThrowException(result.size() == 2, "Can not persist more than two events for one day.");

        if (result.size() == 1 && EVENT.isBeginning() == false)
        {
            // Persist Report
            DailyEmployeeReport report = new DailyEmployeeReport();
            report.setReportDate(LocalDate.now());

            // Calculate work time
            WorkStartFinishEvent initialEvent = result.stream().findFirst().orElseThrow(() -> new NoSuchElementException());
            Duration duration = Duration.between(initialEvent.getEventTime(), EVENT.getEventTime());
            long diff = Math.abs(duration.toMinutes());
            long hours = diff % 60;
            long minutes = diff - (hours * 60);
            report.setWorkTime(LocalTime.of((int) hours, (int) minutes, 0, 0 ));

            // Calculate earned cash
            Employee employee = employeeService.findOne(EVENT.getEmployee().getId());
            double earnedCash = hours * employee.getEmploymentContract().getHourPay() + (minutes/60) * employee.getEmploymentContract().getHourPay();
            report.setEarnedCash(earnedCash);

            // Calculate latnessTime
            if (initialEvent.getEventTime().isAfter(EVENT.getEmployee().getEmploymentContract().getShiftBegin()))
            {
                report.setLateness(true);
                Duration lateness = Duration.between(initialEvent.getEventTime(),
                                                     EVENT.getEmployee().getEmploymentContract().getShiftBegin());
                long latenessMinutes = Math.abs(lateness.toMinutes());
                report.setLatenessMinutes((int)latenessMinutes);
            }
            else
            {
                report.setLateness(false);
            }
            report.setEmployee(EVENT.getEmployee());

            reportService.save(report);
        }

        return repository.save(EVENT);
    }

    private void checkIfAndThrowException(boolean b, String s)
    {
        if (b)
        {
            throw new IllegalArgumentException(s);
        }
    }

    public void delete(final WorkStartFinishEvent EVENT)
    {
        checkIfAndThrowException(EVENT == null, "Event is null.");
        if(repository.findOne(EVENT.getId()) == null)
        {
            throw new IllegalArgumentException("Event with ID: " + EVENT.getId() + " does not exist in database.");
        }

        // Break relation Employee - Event
        EVENT.getEmployee().getWorkStartFinishEvents().remove(EVENT);

        repository.delete(EVENT.getId());
    }

    public void delete(final Long ID)
    {
        WorkStartFinishEvent event = repository.findOne(ID);
        delete(event);
    }
}
