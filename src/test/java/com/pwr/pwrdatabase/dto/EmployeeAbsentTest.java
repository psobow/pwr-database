package com.pwr.pwrdatabase.dto;

import static org.junit.Assert.*;

import com.pwr.pwrdatabase.dto.dao.EmployeeAbsentDao;
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
public class EmployeeAbsentTest
{
    @Autowired private EmployeeDao employeeDao;
    @Autowired private EmploymentContractDao employmentContractDao;
    @Autowired private EmployeeAbsentDao employeeAbsentDao;

    @Test
    public void persistEmployeeAbsent()
    {
        // Given
        EmployeeAbsent employeeAbsent = new EmployeeAbsent();
        employeeAbsent.setTypeOfAbsent("Urlop zdrowotny");
        employeeAbsent.setAbsentStartDate(LocalDate.now());
        employeeAbsent.setAbsentDurationInDays(5);

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

        employeeAbsent.setEmployee(employee);
        employee.getEmployeeAbsents().add(employeeAbsent);

        // Save size of entities
        long sizeOfContractBefore = employmentContractDao.count();
        long sizeOfEmployeeBefore = employeeDao.count();
        long sizeOfAbsentBefore = employeeAbsentDao.count();

        // When
        employeeAbsentDao.save(employeeAbsent);

        // Clean up
        employeeDao.delete(employee.getId()); // deleting both employee and employeeAbsent
        employmentContractDao.delete(contract.getId());

        // Then
        long sizeOfContractAfter = employmentContractDao.count();
        long sizeOfEmployeeAfter = employeeDao.count();
        long sizeOfAbsentAfter = employeeAbsentDao.count();

        Assert.assertEquals(sizeOfContractBefore, sizeOfContractAfter);
        Assert.assertEquals(sizeOfEmployeeBefore, sizeOfEmployeeAfter);
        Assert.assertEquals(sizeOfAbsentBefore, sizeOfAbsentAfter);
    }
}