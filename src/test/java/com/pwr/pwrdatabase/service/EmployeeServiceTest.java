package com.pwr.pwrdatabase.service;

import com.pwr.pwrdatabase.domain.Department;
import com.pwr.pwrdatabase.domain.Employee;
import com.pwr.pwrdatabase.domain.EmploymentContract;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class EmployeeServiceTest
{
    @Autowired EmployeeService employeeService;
    @Autowired ContractService contractService;
    @Autowired DepartmentService departmentService;

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void shouldThrowException_TryToPersistEmployeeWithoutContractAndDepartment()
    {
        Employee employee = new Employee();
        employee.setFirstName("Patryk");
        employee.setSecondName("Tak");
        employee.setPESEL("12345678901");
        employee.setPhoneNumber("489 090 787");
        employee.setHireDate(LocalDate.now());

        employeeService.saveAndRefreshAllOtherEntities(employee);
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void shouldThrowException_TryToPersistEmployeeWithoutDepartment()
    {
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

        employeeService.saveAndRefreshAllOtherEntities(employee);
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void shouldThrowException_TryToPersistEmployeeWithoutContract()
    {
        Department department = new Department();
        department.setCity("Warszawa");
        department.setZipCode("50-200");
        department.setLocalNumber("49B");

        Employee employee = new Employee();
        employee.setFirstName("Patryk");
        employee.setSecondName("Tak");
        employee.setPESEL("12345678901");
        employee.setPhoneNumber("489 090 787");
        employee.setHireDate(LocalDate.now());

        // Set fields responsible for foreign keys
        employee.getDepartments().add(department);
        department.getEmployees().add(employee);

        employeeService.saveAndRefreshAllOtherEntities(employee);
    }

    @Test
    public void shouldDeleteEmployeeNotDepartments()
    {
        // Given
        EmploymentContract contract = new EmploymentContract();
        contract.setEmploymentType("Employment Type");
        contract.setHourPay(10.5);
        contract.setShiftBegin(LocalTime.now());
        contract.setShiftEnd(LocalTime.now());
        contract.setQuantityOfFullWorkDaysForOneHoliday(10);

        Department department1 = new Department();
        department1.setCity("Warszawa");
        department1.setZipCode("50-200");
        department1.setLocalNumber("49B");

        Department department2 = new Department();
        department2.setCity("Warszawa");
        department2.setZipCode("10-100");
        department2.setLocalNumber("100A");

        Employee employee = new Employee();
        employee.setFirstName("Patryk");
        employee.setSecondName("Tak");
        employee.setPESEL("12345678901");
        employee.setPhoneNumber("489 090 787");
        employee.setHireDate(LocalDate.now());

        // Set fields responsible for foreign keys
        employee.getDepartments().add(department1);
        department1.getEmployees().add(employee);
        employee.getDepartments().add(department2);
        department2.getEmployees().add(employee);

        employee.setEmploymentContract(contract);
        contract.getEmployees().add(employee);

        contractService.save(contract);
        departmentService.save(department1);
        departmentService.save(department2);

        employeeService.saveAndRefreshAllOtherEntities(employee);

        // When
        employeeService.delete(employee);


        // Clean up
       // Department departmentFromDatabase = departmentService.findOne(department1.getId());

        EmploymentContract contractFromDatabase = contractService.findOne(contract.getId());

//        departmentService.delete(department1);
//        departmentService.delete(department2);
        contractService.save(contract);
        contractService.delete(contract);

        // Then
    }
}