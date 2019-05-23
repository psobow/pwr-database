package com.pwr.pwrdatabase.domain;

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
public class EmploymentContractTest
{
    @Autowired private EmploymentContractDao employmentContractDao;
    @Autowired private EmployeeDao employeeDao;
    @Test
    public void persistEmploymentContract()
    {
        // Given
        EmploymentContract contract = new EmploymentContract();
        contract.setEmploymentType("Employment Type");
        contract.setHourPay(10.5);
        contract.setShiftBegin(LocalTime.now());
        contract.setShiftEnd(LocalTime.now());
        contract.setQuantityOfFullWorkDaysForOneHoliday(10);

        // Save size of entities
        long sizeOfContractBefore = employmentContractDao.count();

        // When
        employmentContractDao.save(contract);

        // Clean up
        employmentContractDao.delete(contract.getId());

        // Then
        long sizeOfContractAfter = employmentContractDao.count();

        Assert.assertEquals(sizeOfContractBefore, sizeOfContractAfter);
    }

    @Test
    public void shouldDeleteContractWithoutDeletingRelatedEmployee()
    {
        // Given
        EmploymentContract contract1 = new EmploymentContract();
        contract1.setEmploymentType("Employment Type One");
        contract1.setHourPay(10.5);
        contract1.setShiftBegin(LocalTime.now());
        contract1.setShiftEnd(LocalTime.now());
        contract1.setQuantityOfFullWorkDaysForOneHoliday(10);

        EmploymentContract contract2 = new EmploymentContract();
        contract2.setEmploymentType("Employment Type Two");
        contract2.setHourPay(20.5);
        contract2.setShiftBegin(LocalTime.now());
        contract2.setShiftEnd(LocalTime.now());
        contract2.setQuantityOfFullWorkDaysForOneHoliday(20);

        Employee employee = new Employee();
        employee.setFirstName("Patryk");
        employee.setSecondName("Tak");
        employee.setPESEL("12345678901");
        employee.setPhoneNumber("489 090 787");
        employee.setHireDate(LocalDate.now());

        // Set fields responsible for foreign keys
        employee.setEmploymentContract(contract1);
        contract1.getEmployees().add(employee);

        // Save size of entities
        long initSizeOfContract = employmentContractDao.count();
        long initSizeOfEmployee = employeeDao.count();

        employmentContractDao.save(contract1);
        employmentContractDao.save(contract2);
        employeeDao.save(employee);

        // When
        // Delete employee from previous contract
        contract1.getEmployees().remove(employee);

        // Set up new relation
        employee.setEmploymentContract(contract2);
        contract2.getEmployees().add(employee);

        // Refresh data in database
        employeeDao.save(employee);

        // Delete only contract1
        employmentContractDao.delete(contract1.getId());

        long sizeOfContractAfterDeleteContract1 = employmentContractDao.count();
        long sizeOfEmployeeAfterDeleteContract1 = employeeDao.count();

        // Clean up (deleting contract2 and employee)
        contract2.getEmployees().remove(employee);
        employee.setEmploymentContract(null);

        employeeDao.save(employee); // refresh data in database

        employmentContractDao.delete(contract2.getId());
        employeeDao.delete(employee);

        long terminalSizeOfContract = employmentContractDao.count();
        long terminalSizeOfEmployee = employeeDao.count();

        // Then
        Assert.assertEquals(initSizeOfContract, terminalSizeOfContract);
        Assert.assertEquals(initSizeOfEmployee, terminalSizeOfEmployee);

        Assert.assertEquals(initSizeOfContract + 1, sizeOfContractAfterDeleteContract1);
        Assert.assertEquals(initSizeOfEmployee + 1, sizeOfEmployeeAfterDeleteContract1);
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void shouldThrowException()
    {
        // Given
        EmploymentContract contract1 = new EmploymentContract();
        contract1.setEmploymentType("Employment Type One");
        contract1.setHourPay(10.5);
        contract1.setShiftBegin(LocalTime.now());
        contract1.setShiftEnd(LocalTime.now());
        contract1.setQuantityOfFullWorkDaysForOneHoliday(10);

        Employee employee = new Employee();
        employee.setFirstName("Patryk");
        employee.setSecondName("Tak");
        employee.setPESEL("12345678901");
        employee.setPhoneNumber("489 090 787");
        employee.setHireDate(LocalDate.now());

        // Set fields responsible for foreign keys
        employee.setEmploymentContract(contract1);
        contract1.getEmployees().add(employee);

        // When
        employeeDao.save(employee);
        employmentContractDao.save(contract1); // this should be persisted first

        // Cause of this exception is incorrect order of persisting objets.
        // Employee is owner of Employee - EmploymentContract, so EmploymentContract should be persisted first
    }

}