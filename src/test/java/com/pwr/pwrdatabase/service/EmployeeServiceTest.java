package com.pwr.pwrdatabase.service;

import com.pwr.pwrdatabase.domain.Department;
import com.pwr.pwrdatabase.domain.Employee;
import com.pwr.pwrdatabase.domain.EmploymentContract;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
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
        employee.setPhoneNumber("489090787");
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
        employee.setPhoneNumber("489090787");
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
        employee.setPhoneNumber("489090787");
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
        employee.setPhoneNumber("489090787");
        employee.setHireDate(LocalDate.now());

        // Set fields responsible for foreign keys
        employee.getDepartments().add(department1);
        department1.getEmployees().add(employee);
        employee.getDepartments().add(department2);
        department2.getEmployees().add(employee);

        employee.setEmploymentContract(contract);
        contract.getEmployees().add(employee);

        // Save size of entities
        long initSizeOfContract = contractService.count();
        long initSizeOfEmployee = employeeService.count();
        long initSizeOfDepartment = departmentService.count();

        contractService.save(contract);
        departmentService.save(department1);
        departmentService.save(department2);

        employeeService.saveAndRefreshAllOtherEntities(employee);

        // When
        employeeService.delete(employee);

        long sizeOfContract = contractService.count();
        long sizeOfEmployee = employeeService.count();
        long sizeOfDepartment = departmentService.count();

        // Clean up

        contractService.delete(contract);
        departmentService.delete(department1);
        departmentService.delete(department2);


        // Then
        long terminalSizeOfContract = contractService.count();
        long terminalSizeOfEmployee = employeeService.count();
        long terminalSizeOfDepartment = departmentService.count();

        Assert.assertEquals(initSizeOfContract, terminalSizeOfContract);
        Assert.assertEquals(initSizeOfEmployee, terminalSizeOfEmployee);
        Assert.assertEquals(initSizeOfDepartment, terminalSizeOfDepartment);

        Assert.assertEquals(initSizeOfEmployee, sizeOfEmployee);
        Assert.assertEquals(initSizeOfContract+1 , sizeOfContract);
        Assert.assertEquals(initSizeOfDepartment+2, sizeOfDepartment);
    }

    @Test
    public void testFindAllIDs()
    {
        // Given
        EmploymentContract contract = new EmploymentContract();
        contract.setEmploymentType("Employment Type");
        contract.setHourPay(10.5);
        contract.setShiftBegin(LocalTime.now());
        contract.setShiftEnd(LocalTime.now());
        contract.setQuantityOfFullWorkDaysForOneHoliday(10);

        Department department = new Department();
        department.setCity("Warszawa");
        department.setZipCode("50-200");
        department.setLocalNumber("49B");

        Employee employee = new Employee();
        employee.setFirstName("Patryk");
        employee.setSecondName("Tak");
        employee.setPESEL("12345678901");
        employee.setPhoneNumber("489090787");
        employee.setHireDate(LocalDate.now());

        Employee employee2 = new Employee();
        employee2.setFirstName("Patryk");
        employee2.setSecondName("NIE");
        employee2.setPESEL("12345678901");
        employee2.setPhoneNumber("489090787");
        employee2.setHireDate(LocalDate.now());

        // Set fields responsible for foreign keys
        employee.getDepartments().add(department);
        department.getEmployees().add(employee);

        employee2.getDepartments().add(department);
        department.getEmployees().add(employee2);

        employee.setEmploymentContract(contract);
        contract.getEmployees().add(employee);

        employee2.setEmploymentContract(contract);
        contract.getEmployees().add(employee2);

        // Save size of entities
        long initSizeOfContract = contractService.count();
        long initSizeOfEmployee = employeeService.count();
        long initSizeOfDepartment = departmentService.count();

        contractService.save(contract);
        departmentService.save(department);

        employeeService.saveAndRefreshAllOtherEntities(employee);
        employeeService.saveAndRefreshAllOtherEntities(employee2);

        // When
        Set<Employee> resultSet = employeeService.findAll(new HashSet<Long>()
        {
            {
                add(employee.getId());
                add(employee2.getId());
            }
        });

        Set<Employee> resultSet2 = employeeService.findAll();

        // Clean up
        employeeService.delete(employee);
        employeeService.delete(employee2);

        contractService.delete(contract);
        departmentService.delete(department);

        // Then
        long terminalSizeOfContract = contractService.count();
        long terminalSizeOfEmployee = employeeService.count();
        long terminalSizeOfDepartment = departmentService.count();

        Assert.assertEquals(initSizeOfContract, terminalSizeOfContract);
        Assert.assertEquals(initSizeOfEmployee, terminalSizeOfEmployee);
        Assert.assertEquals(initSizeOfDepartment, terminalSizeOfDepartment);

        Assert.assertEquals(2, resultSet.size());
        Assert.assertEquals(2, resultSet2.size());
    }
}