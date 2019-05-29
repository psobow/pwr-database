package com.pwr.pwrdatabase.domain;

import com.pwr.pwrdatabase.domain.dao.DepartmentDao;
import com.pwr.pwrdatabase.domain.dao.EmployeeDao;
import com.pwr.pwrdatabase.domain.dao.EmploymentContractDao;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DepartmentTest
{
    @Autowired private EmployeeDao employeeDao;
    @Autowired private EmploymentContractDao employmentContractDao;
    @Autowired private DepartmentDao departmentDao;

    @Test
    public void persistDepartment()
    {
        // Given
        Department department = new Department();
        department.setCity("Warszawa");
        department.setZipCode("50-200");
        department.setLocalNumber("49B");

        // Save size of entity
        long initSizeOfDepartment = departmentDao.count();

        // When
        departmentDao.save(department);

        long sizeOfDepartment = departmentDao.count();

        // Clean up
        departmentDao.delete(department.getId());

        // Then
        long terminalSizeOfDepartment = departmentDao.count();

        Assert.assertEquals(initSizeOfDepartment, terminalSizeOfDepartment);
        Assert.assertEquals(initSizeOfDepartment + 1, sizeOfDepartment);
    }

    @Test
    public void persistDepartmentWithEmployeeAndContract()
    {
        // Given
        Department department = new Department();
        department.setCity("Warszawa");
        department.setZipCode("50-200");
        department.setLocalNumber("49B");

        EmploymentContract contract = new EmploymentContract();
        contract.setEmploymentType("Employment Type");
        contract.setHourPay(10.5);
        contract.setShiftBegin(LocalTime.now());
        contract.setShiftEnd(LocalTime.now());
        contract.setQuantityOfFullWorkDaysForOneHoliday(10);

        Employee employee = new Employee();
        employee.setFirstName("Patryk");
        employee.setSecondName("Tak");
        employee.setPESEL("12345678901");
        employee.setPhoneNumber("489 090 787");
        employee.setHireDate(LocalDate.now());

        // Set fields responsible for foreign keys
        employee.setEmploymentContract(contract);
        contract.getEmployees().add(employee);

        employee.getDepartments().add(department);
        department.getEmployees().add(employee);

        // Save size of entities
        long initSizeOfContract = employmentContractDao.count();
        long initSizeOfEmployee = employeeDao.count();
        long initSizeOfDepartment = departmentDao.count();

        // When
        employmentContractDao.save(contract);
        departmentDao.save(department);
        employeeDao.save(employee);
        // Employee is owner of department - Employee relation. That is why department has to be persisted first
        // Same situation betwen Employee - EmploymentContract

        // Clean up
        // Break relation betwen contract - employee
        contract.getEmployees().remove(employee);
        employee.setEmploymentContract(null);

        // Break relation betwen department - employee
        department.getEmployees().remove(employee);
        employee.getDepartments().remove(department);

        employeeDao.save(employee); // Refresh employee, contract, department in database. Since now we removed employee - department from JOIN_TABLE

        employmentContractDao.delete(contract.getId()); // delete contract from database
        departmentDao.delete(department); // department has to be removed before employee becouse Employee is relation owner
        employeeDao.delete(employee.getId());

        // Then
        long terminalSizeOfContract = employmentContractDao.count();
        long terminalSizeOfEmployee = employeeDao.count();
        long terminalSizeOfDepartment = departmentDao.count();

        Assert.assertEquals(initSizeOfContract, terminalSizeOfContract);
        Assert.assertEquals(initSizeOfEmployee, terminalSizeOfEmployee);
        Assert.assertEquals(initSizeOfDepartment, terminalSizeOfDepartment);
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void shouldThrowException()
    {
        // Given
        Department department = new Department();
        department.setCity("Warszawa");
        department.setZipCode("50-200");
        department.setLocalNumber("49B");

        EmploymentContract contract = new EmploymentContract();
        contract.setEmploymentType("Employment Type");
        contract.setHourPay(10.5);
        contract.setShiftBegin(LocalTime.now());
        contract.setShiftEnd(LocalTime.now());
        contract.setQuantityOfFullWorkDaysForOneHoliday(10);

        Employee employee = new Employee();
        employee.setFirstName("Patryk");
        employee.setSecondName("Tak");
        employee.setPESEL("12345678901");
        employee.setPhoneNumber("489 090 787");
        employee.setHireDate(LocalDate.now());

        // Set fields responsible for foreign keys
        employee.setEmploymentContract(contract);
        contract.getEmployees().add(employee);

        employee.getDepartments().add(department);
        department.getEmployees().add(employee);

        // When
        employmentContractDao.save(contract);

        employeeDao.save(employee); // this should be persisted last
        departmentDao.save(department);
    }
}