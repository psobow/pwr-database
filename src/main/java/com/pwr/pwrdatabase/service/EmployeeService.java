package com.pwr.pwrdatabase.service;

import com.pwr.pwrdatabase.domain.Employee;
import com.pwr.pwrdatabase.domain.dao.EmployeeDao;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService
{
    @Autowired private EmployeeDao repository;

    public Set<Employee> findAll()
    {
        return repository.findAll();
    }

    public Set<Employee> findAll(final Set<Long> IDs)
    {
        return repository.findAll(IDs);
    }

    public Employee findOne(final Long ID)
    {
        return repository.findOne(ID);
    }

}
