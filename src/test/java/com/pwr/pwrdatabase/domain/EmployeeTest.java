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
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class EmployeeTest
{
    @Autowired private EmployeeDao employeeDao;
    @Autowired private EmploymentContractDao employmentContractDao;

    @Test
    public void persistEmploymentContractAndEmployeeSeparately()
    {
        // Given
        EmploymentContract contract = new EmploymentContract();
        contract.setEmploymentType("Employment Type");
        contract.setHourPay(10.5);
        contract.setShiftBegin(LocalTime.now());
        contract.setShiftEnd(LocalTime.now());
        contract.setQuantityOfFullWorkDaysForOneHoliday(10);

        // Save size of entities
        long initSizeOfContract = employmentContractDao.count();
        long initSizeOfEmployee = employeeDao.count();

        // Persisting contract in database before adding relation Contract - Employee
        employmentContractDao.save(contract);

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

        // Saving contract object for check if the relationship has been properly persisted inside database
        EmploymentContract contractFromDatabase = employmentContractDao.findOne(contract.getId());

        // Clean up
        contract.getEmployees().remove(employee);
        employee.setEmploymentContract(null);

        employeeDao.save(employee); // refresh employee and contract in database

        employmentContractDao.delete(contract.getId());
        employeeDao.delete(employee);

        // Then
        long terminalSizeOfContract = employmentContractDao.count();
        long terminalSizeOfEmployee = employeeDao.count();

        Assert.assertEquals(initSizeOfContract, terminalSizeOfContract);
        Assert.assertEquals(initSizeOfEmployee, terminalSizeOfEmployee);
        Assert.assertEquals(contractFromDatabase.getEmployees().size(), 1);
    }
}