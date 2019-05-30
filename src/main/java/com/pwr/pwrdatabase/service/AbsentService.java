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

    public EmployeeAbsent save(final EmployeeAbsent absent)
    {
        if (absent.getEmployee() == null)
        {
            throw new IllegalArgumentException("Invalid absent. Can not persist Absent without Employee.");
        }
        return repository.save(absent);
    }
}
