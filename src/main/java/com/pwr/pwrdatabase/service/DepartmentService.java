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

    public Long count()
    {
        return repository.count();
    }

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

    public Department save(final Department DEPARTMENT)
    {
        checkIfAndThrowException(DEPARTMENT == null, "Department is null.");

        Set<Department> departments = repository.findAll();
        for (Department d : departments)
        {
            checkIfAndThrowException(d.equals(DEPARTMENT), "Department already exist in database.");
        }


        checkIfAndThrowException(DEPARTMENT.getZipCode().matches("^[0-9]{2}(?:-[0-9]{3})?$") == false,
                                 "Invalid department. Invalid zip-code.");
        return repository.save(DEPARTMENT);
    }

    public void delete(final Department DEPARTMENT)
    {
        checkIfAndThrowException(DEPARTMENT == null, "Department is null.");
        checkIfAndThrowException(repository.findOne(DEPARTMENT.getId()) == null, "Department with ID: " + DEPARTMENT.getId() + " does not exist in database.");
        checkIfAndThrowException(DEPARTMENT.getEmployees().size() != 0, "Invalid department. Can not delete department with related employees.");

        repository.delete(DEPARTMENT.getId());
    }

    public void delete(final Long ID)
    {
        Department departmentFromDatabase = repository.findOne(ID);
        checkIfAndThrowException(departmentFromDatabase == null, "Department with ID: " + ID + " does not exist in database.");
        this.delete(departmentFromDatabase);
    }

    private void checkIfAndThrowException(boolean b, String s)
    {
        if (b)
        {
            throw new IllegalArgumentException(s);
        }
    }
}
