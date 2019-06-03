package com.pwr.pwrdatabase.service;

import com.pwr.pwrdatabase.domain.Department;
import com.pwr.pwrdatabase.domain.Employee;
import com.pwr.pwrdatabase.domain.dao.EmployeeDao;
import java.time.LocalDate;
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
        checkIfAndThrowException(EMPLOYEE == null, "Employee is null.");

        checkIfAndThrowException(EMPLOYEE.getEmploymentContract() == null,
                                 "Invalid employee. Invalid Employment Contract (null)");

        checkIfAndThrowException(EMPLOYEE.getDepartments().size() == 0, "Invalid employee. Departments size 0");

        checkIfAndThrowException(EMPLOYEE.getFirstName().chars().allMatch(Character::isLetter) == false
                                 || EMPLOYEE.getSecondName().chars().allMatch(Character::isLetter) == false,
                                 "Invalid employee. First name and second name can not contain digits");

        if (repository.findOne(EMPLOYEE.getId()) == null)
        {
            checkIfAndThrowException(EMPLOYEE.getHireDate().isEqual(LocalDate.now()) == false,
                                     "Invalid employee. Hire date can not be diffrent than " + LocalDate.now());
        }

        checkIfAndThrowException(EMPLOYEE.getPhoneNumber().matches("^[0-9]*$") == false
                                 || EMPLOYEE.getPhoneNumber().length() < 9,
                                 "Invalid employee. Invalid phone number");


        return repository.save(EMPLOYEE); // Ta metoda prawdopodobnie zupdate'uje Absent, Report, Event pomijając logike z ich serwisów
        // ponieważ encja Employee ma CascadeType refresh do wszstkich pozostałych encji.
    }

    private void checkIfAndThrowException(boolean b, String s)
    {
        if (b)
        {
            throw new IllegalArgumentException(s);
        }
    }


    public void delete(final Employee EMPLOYEE)
    {
        checkIfAndThrowException(EMPLOYEE == null, "Employee is null.");
        checkIfAndThrowException(repository.findOne(EMPLOYEE.getId()) == null, "Employee with ID: " + EMPLOYEE.getId() + "does not exist in database.");

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
        checkIfAndThrowException(employeeFromDatabase == null, "Employee with ID: " + ID + "does not exist in database.");
        this.delete(employeeFromDatabase);
    }

}
