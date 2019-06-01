package com.pwr.pwrdatabase.service;

import com.pwr.pwrdatabase.domain.Department;
import com.pwr.pwrdatabase.domain.Employee;
import com.pwr.pwrdatabase.domain.dao.EmployeeDao;
import java.util.Iterator;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService
{
    @Autowired private EmployeeDao repository;

    public Long count()
    {
        return repository.count();
    }

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

    public Employee saveAndRefreshAllOtherEntities(final Employee EMPLOYEE)
    {
        if (EMPLOYEE == null)
        {
            throw new IllegalArgumentException("Employee is null.");
        }

        String exceptionMessage = "Invalid employee. Caused by:";
        if (EMPLOYEE.getEmploymentContract() == null)
        {
            exceptionMessage += " Invalid Employment Contract (null)";
        }

        if (EMPLOYEE.getDepartments().size() == 0)
        {
            exceptionMessage += " Invalid Departments (size 0)";
        }

        if (EMPLOYEE.getPhoneNumber().matches("^[0-9]*$") == false
                         || EMPLOYEE.getPhoneNumber().length() < 9)
        {
            exceptionMessage += " Invalid phone number";
        }

        if (exceptionMessage.equals("Invalid employee. Caused by:") == false)
        {
            throw new IllegalArgumentException(exceptionMessage);
        }
        return repository.save(EMPLOYEE);
    }


    public void delete(final Employee EMPLOYEE)
    {
        if (EMPLOYEE == null)
        {
            throw new IllegalArgumentException("Employee is null.");
        }
        if (repository.findOne(EMPLOYEE.getId()) == null)
        {
            throw new IllegalArgumentException("Employee with ID: " + EMPLOYEE.getId() + "does not exist in database.");
        }

        // Break relation Employee - Contract
        EMPLOYEE.getEmploymentContract().getEmployees().remove(EMPLOYEE);
        EMPLOYEE.setEmploymentContract(null);

        // Break relation Employee - Departments
        Iterator<Department> it = EMPLOYEE.getDepartments().iterator();
        Department department = null;
        while (it.hasNext())
        {
            department = it.next();
            department.getEmployees().remove(EMPLOYEE);
            it.remove();
        }

        // Refresh data in database
        Employee employeeAfterRefresh = repository.save(EMPLOYEE);

        // Delete Employee with Absents, Reports, Events
        repository.delete(employeeAfterRefresh.getId());
    }

    public void delete(final Long ID)
    {
        Employee employeeFromDatabase = repository.findOne(ID);
        this.delete(employeeFromDatabase);
    }

}
