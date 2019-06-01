package com.pwr.pwrdatabase.service;

import com.pwr.pwrdatabase.domain.EmployeeAbsent;
import com.pwr.pwrdatabase.domain.dao.EmployeeAbsentDao;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AbsentService
{
    @Autowired private EmployeeAbsentDao repository;

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
        if (ABSENT == null)
        {
            throw new IllegalArgumentException("Absent is null.");
        }

        if (ABSENT.getEmployee() == null)
        {
            throw new IllegalArgumentException("Invalid absent. Can not persist Absent without Employee.");
        }
        return repository.save(ABSENT);
    }

    public void delete(final EmployeeAbsent ABSENT)
    {
        if (ABSENT == null)
        {
            throw new IllegalArgumentException("Absent is null.");
        }
        if (repository.findOne(ABSENT.getId()) == null)
        {
            throw new IllegalArgumentException("Absent with ID: " + ABSENT.getId() + " does not exist in database.");
        }

        // Break relation Employee - Absent
        ABSENT.getEmployee().getEmployeeAbsents().remove(ABSENT);

        repository.delete(ABSENT.getId());
    }

    public void delete(final Long ID)
    {
        EmployeeAbsent absent = repository.findOne(ID);
        delete(absent);
    }
}
