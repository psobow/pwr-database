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

    public Department save(final Department department)
    {
        return repository.save(department);
    }

    public void delete(final Department department)
    {
        if (department.getEmployees().size() != 0)
        {
            throw new IllegalArgumentException("Invalid department. Can not delete department with related employees.");
        }
        repository.delete(department);
    }
}
