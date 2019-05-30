package com.pwr.pwrdatabase.mapper;

import static org.junit.Assert.*;

import com.pwr.pwrdatabase.domain.Department;
import com.pwr.pwrdatabase.domain.Employee;
import com.pwr.pwrdatabase.domain.EmploymentContract;
import com.pwr.pwrdatabase.dto.EmployeeDto;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.NoSuchElementException;
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
public class EmployeeMapperTest
{
    @Autowired private EmployeeMapper employeeMapper;

    @Test
    public void mapperTest()
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
        EmployeeDto mappedToDTO = employeeMapper.mapToEmployeeDto(employee);
        Employee mappedBack = employeeMapper.mapToEmployee(mappedToDTO);

        Set<EmployeeDto> employeeDtoSet = employeeMapper.mapToEmployeesDto(
                new HashSet<Employee>()
                {
                    {
                        add(employee);
                    }
                });

        // Then
        Assert.assertEquals("EmployeeDto", employeeDtoSet.stream()
                                                             .findAny()
                                                             .orElseThrow( () -> new NoSuchElementException())
                                                             .getClass()
                                                             .getSimpleName());

        Assert.assertEquals("EmployeeDto", mappedToDTO.getClass().getSimpleName());
        Assert.assertEquals(employee.getId(), mappedToDTO.getId());

        Assert.assertEquals("Employee", mappedBack.getClass().getSimpleName());
        Assert.assertEquals(employee.getId(), mappedBack.getId());

        Assert.assertEquals((Long) employee.getEmploymentContract().getId(), mappedToDTO.getEmploymentContractDtoID());
    }
}