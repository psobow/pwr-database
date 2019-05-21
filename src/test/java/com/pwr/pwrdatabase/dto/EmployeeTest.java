package com.pwr.pwrdatabase.dto;

import static org.junit.Assert.*;

import com.pwr.pwrdatabase.dto.dao.EmployeeDao;
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
public class EmployeeTest
{
    @Autowired
    private EmployeeDao employeeDao;

    @Test
    public void persistEmployee()
    {
        // Given
        EmploymentContract contract = new EmploymentContract();
        contract.setEmploymentType("Employment Type");
        contract.setHourPay(10.5);
        contract.setShiftBegin(LocalTime.now());
        contract.setShiftEnd(LocalTime.now());
        contract.setQuantityOfFullWorkDaysForOneHoliday(10);
        contract.setActive(true);

        Employee employee = new Employee();
        employee.setFirstName("Patryk");
        employee.setSecondName("Tak");
        employee.setPESEL("12345678901");
        employee.setPhoneNumber("489 090 787");
        employee.setHireDate(LocalDate.now());

        // Set fields responsible for foreign keys
        employee.setEmploymentContract(contract);
        contract.getEmployees().add(employee);

        // When
        employeeDao.save(employee);
        long employeeId = employee.getId();

        // Clean up
        employeeDao.delete(employeeId);

        // Then
        log.info("New employee ID: " + employeeId);

    }


}