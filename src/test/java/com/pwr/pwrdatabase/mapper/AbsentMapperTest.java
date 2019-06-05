package com.pwr.pwrdatabase.mapper;

import com.pwr.pwrdatabase.domain.Employee;
import com.pwr.pwrdatabase.domain.EmployeeAbsent;
import com.pwr.pwrdatabase.domain.EmploymentContract;
import com.pwr.pwrdatabase.dto.EmployeeAbsentDto;
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
public class AbsentMapperTest
{
    @Autowired private AbsentMapper absentMapper;
    @Test
    public void mapperTest()
    {
        // Given
        EmploymentContract contract = new EmploymentContract();
        contract.setEmploymentType("Employment Type");
        contract.setHourPay(10.5);
        contract.setShiftBegin(LocalTime.now());
        contract.setShiftEnd(LocalTime.now());
        //contract.setQuantityOfFullWorkDaysForOneHoliday(10);

        Employee employee = new Employee();
        employee.setFirstName("Patryk");
        employee.setSecondName("Tak");
        employee.setPESEL("12345678901");
        employee.setPhoneNumber("489 090 787");
        employee.setHireDate(LocalDate.now());

        EmployeeAbsent employeeAbsent = new EmployeeAbsent();
        employeeAbsent.setTypeOfAbsent("Urlop zdrowotny");
        employeeAbsent.setAbsentStartDate(LocalDate.now());
        employeeAbsent.setAbsentDurationInDays(5);

        // Set fields responsible for foreign keys
        employee.setEmploymentContract(contract);
        contract.getEmployees().add(employee);

        employeeAbsent.setEmployee(employee);
        employee.getEmployeeAbsents().add(employeeAbsent);

        // When
        EmployeeAbsentDto mappedToDTO = absentMapper.mapToAbsentDto(employeeAbsent);

        EmployeeAbsent mappedBack = absentMapper.mapToAbsent(mappedToDTO);


        //Sztuczka inicjalizacyjna z wykorzystaniem anonimowych klas oraz bloków inicjalizacyjnych

        Set<EmployeeAbsentDto> absentDtoSet = absentMapper.mapToAbsentsDto(
                new HashSet<EmployeeAbsent>()
                {
                    {
                        add(employeeAbsent);
                    }
                });

        // Then
        Assert.assertEquals("EmployeeAbsentDto", absentDtoSet.stream()
                                                                  .findAny()
                                                                  .orElseThrow( () -> new NoSuchElementException())
                                                                  .getClass()
                                                                  .getSimpleName());

        Assert.assertEquals("EmployeeAbsentDto", mappedToDTO.getClass().getSimpleName());
        Assert.assertEquals((long) employeeAbsent.getId(), (long) mappedToDTO.getId());

        Assert.assertEquals("EmployeeAbsent", mappedBack.getClass().getSimpleName());
        Assert.assertEquals(employeeAbsent.getId(), mappedBack.getId());
    }
}