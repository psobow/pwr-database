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

    public Employee saveAndRefreshAllOtherEntities(final Employee employee)
    {
        if (employee.getEmploymentContract() == null ||
            employee.getDepartments().size() == 0)
        {
            throw new IllegalArgumentException("Invalid employee. can not persist employee without Contract or Department.");
        }
        return repository.save(employee);
    }

    public void delete(final Employee employee)
    {
        // Break relation Employee - Contract
        employee.getEmploymentContract().getEmployees().remove(employee);
        employee.setEmploymentContract(null);

        // Break relation Employee - Departments
        Iterator<Department> it = employee.getDepartments().iterator();
        while (it.hasNext())
        {
            Department department = it.next();
            department.getEmployees().remove(employee);
            it.remove();
        }

        // Refresh data in database
        repository.save(employee);

        // Delete Employee with Absents, Reports, Events
        repository.delete(employee);
    }

}
