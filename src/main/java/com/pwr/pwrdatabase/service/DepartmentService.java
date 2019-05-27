package com.pwr.pwrdatabase.service;

import com.pwr.pwrdatabase.domain.Department;
import com.pwr.pwrdatabase.domain.dao.DepartmentDao;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService
{
    @Autowired private DepartmentDao repository;

    public Set<Department> findAll()
    {
        return repository.findAll();
    }

    public Set<Department> findAll(final Set<Long> IDs)
    {
        return repository.findAll(IDs);
    }

    public Department findOne(final Long ID)
    {
        return repository.findOne(ID);
    }
}
