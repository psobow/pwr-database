package com.pwr.pwrdatabase.service;

import com.pwr.pwrdatabase.domain.DailyEmployeeReport;
import com.pwr.pwrdatabase.domain.Employee;
import com.pwr.pwrdatabase.domain.WorkStartFinishEvent;
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
        if (EVENT == null)
        {
            throw new IllegalArgumentException("Event is null.");
        }
        if (EVENT.getEmployee() == null)
        {
            throw new IllegalArgumentException("Invalid event. Can not persist event without Employee.");
        }
        if (EVENT.getEventDate().isEqual(LocalDate.now()) == false)
        {
            throw new IllegalArgumentException("Invalid event. Can not persist event with day diffrent than today.");
        }
        Set<WorkStartFinishEvent> result = repository.findAllByEmployeeAndEventDate(EVENT.getEmployee(), EVENT.getEventDate());
        if (result.size() == 0 && EVENT.isBeginning() == false)
        {
            throw new IllegalArgumentException("Invalid event. can not persist initial event with beginning == false");
        }
        if (result.size() == 1 && EVENT.isBeginning() == true)
        {
            throw new IllegalArgumentException("Invalid event. can not persist terminal event with beginning == true");
        }
        if (result.size() == 2)
        {
            throw new IllegalArgumentException("Can not persist more than two events for one day.");
        }

        if (result.size() == 1 && EVENT.isBeginning() == false)
        {
            // Persist Report with ReportService
            DailyEmployeeReport report = new DailyEmployeeReport();
            report.setReportDate(EVENT.getEventDate());

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

    public void delete(final WorkStartFinishEvent EVENT)
    {
        if (EVENT == null)
        {
            throw new IllegalArgumentException("Event is null.");
        }
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
