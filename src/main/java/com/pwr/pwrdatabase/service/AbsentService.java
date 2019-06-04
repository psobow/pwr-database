package com.pwr.pwrdatabase.service;

import com.pwr.pwrdatabase.domain.EmployeeAbsent;
import com.pwr.pwrdatabase.domain.dao.EmployeeAbsentDao;
import java.time.LocalDate;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AbsentService
{
    @Autowired private EmployeeAbsentDao repository;
    private final int MAXIMUM_AMOUNT_OF_DAYS_FOR_TAKING_LEAVE_IN_ADVANCE = 10;

    public Long count()
    {
        return repository.count();
    }

    public Set<EmployeeAbsent> findAll()
    {
        return repository.findAll();
    }

    public Set<EmployeeAbsent> findAll(final Set<Long> IDs)
    {
        return repository.findAll(IDs);
    }

    public EmployeeAbsent findOne(final Long ID)
    {
        return repository.findOne(ID);
    }

    public EmployeeAbsent save(final EmployeeAbsent ABSENT)
    {
        checkIfAndThrowException(ABSENT == null, "Absent is null.");
        checkIfAndThrowException(ABSENT.getEmployee() == null, "Invalid absent. Can not persist Absent without Employee.");

        Set<EmployeeAbsent> absents = repository.findAllByEmployee(ABSENT.getEmployee());

        for (EmployeeAbsent a : absents)
        {
            checkIfAndThrowException(LocalDate.now().isAfter(a.getAbsentStartDate())
                                             && LocalDate.now().isBefore(a.getAbsentStartDate().plusDays(a.getAbsentDurationInDays())),
                                     "Can not persist Two absents in the same time.");
        }


        checkIfAndThrowException(ABSENT.getAbsentStartDate().isBefore(LocalDate.now()),
                                 "Invalid absent. Can not persist Absent with start date before " + LocalDate.now().toString());

        checkIfAndThrowException(ABSENT.getAbsentStartDate().isAfter(LocalDate.now().plusDays(MAXIMUM_AMOUNT_OF_DAYS_FOR_TAKING_LEAVE_IN_ADVANCE)),
                                 "Invalid absent. Can not persist Absent with start date after " + MAXIMUM_AMOUNT_OF_DAYS_FOR_TAKING_LEAVE_IN_ADVANCE
                                         + " days from " + LocalDate.now().toString());

        checkIfAndThrowException(ABSENT.getAbsentDurationInDays() <= 0, "Invalid absent. Can not persist absent with non-positive duration date.");

        return repository.save(ABSENT);
    }

    public void delete(final EmployeeAbsent ABSENT)
    {
        checkIfAndThrowException(ABSENT == null, "Absent is null.");
        checkIfAndThrowException(repository.findOne(ABSENT.getId()) == null, "Absent with ID: " + ABSENT.getId() + " does not exist in database.");

        // Break relation Employee - Absent
        ABSENT.getEmployee().getEmployeeAbsents().remove(ABSENT);

        repository.delete(ABSENT.getId());
    }

    public void delete(final Long ID)
    {
        EmployeeAbsent absent = repository.findOne(ID);
        checkIfAndThrowException(absent == null, "Absent with ID: " + ID + " does not exist in database.");
        delete(absent);
    }



    private void checkIfAndThrowException(boolean b, String s)
    {
        if (b)
        {
            throw new IllegalArgumentException(s);
        }
    }
}
