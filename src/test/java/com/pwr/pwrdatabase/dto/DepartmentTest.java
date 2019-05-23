package com.pwr.pwrdatabase.dto;

import static org.junit.Assert.*;

import com.pwr.pwrdatabase.dto.dao.DepartmentDao;
import com.pwr.pwrdatabase.dto.dao.EmployeeDao;
import com.pwr.pwrdatabase.dto.dao.EmploymentContractDao;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
        long sizeOfDepartmentBefore = departmentDao.count();

        // When
        departmentDao.save(department);

        // Clean up
        departmentDao.delete(department.getId());

        // Then
        long sizeOfDepartmentAfter = departmentDao.count();

        Assert.assertEquals(sizeOfDepartmentBefore, sizeOfDepartmentAfter);
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
        long sizeOfContractBefore = employmentContractDao.count();
        long sizeOfEmployeeBefore = employeeDao.count();
        long sizeOfDepartmentBefore = departmentDao.count();

        // When
        employmentContractDao.save(contract);

        // Clean up
        employmentContractDao.delete(contract.getId());

        // Then
        long sizeOfContractAfter = employmentContractDao.count();
        long sizeOfEmployeeAfter = employeeDao.count();
        long sizeOfDepartmentAfter= departmentDao.count();

        Assert.assertEquals(sizeOfContractBefore, sizeOfContractAfter);
        Assert.assertEquals(sizeOfEmployeeBefore, sizeOfEmployeeAfter);
        Assert.assertEquals(sizeOfDepartmentBefore, sizeOfDepartmentAfter);
    }
}