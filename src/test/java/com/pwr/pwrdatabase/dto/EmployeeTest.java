package com.pwr.pwrdatabase.dto;

import static org.junit.Assert.*;

import com.pwr.pwrdatabase.dto.dao.EmployeeDao;
import com.pwr.pwrdatabase.dto.dao.EmploymentContractDao;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.transaction.Transactional;
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
    public void persistEmployeeWithEmploymentContract()
    {
        // Given
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

        // Save size of entities
        long sizeOfContractBefore = employmentContractDao.count();
        long sizeOfEmployeeBefore = employeeDao.count();

        // When
        employmentContractDao.save(contract);
        // During saving contract object employee will be also persisted becouse of CascadeType inside EmploymentContract entity

        // Clean up
        employmentContractDao.delete(contract.getId());
        // During deletion both object will be deleted from database becouse of cascade type

        // Then
        long sizeOfContractAfter = employmentContractDao.count();
        long sizeOfEmployeeAfter = employeeDao.count();

        Assert.assertEquals(sizeOfContractBefore, sizeOfContractAfter);
        Assert.assertEquals(sizeOfEmployeeBefore, sizeOfEmployeeAfter);
    }

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
        long sizeOfContractBefore = employmentContractDao.count();
        long sizeOfEmployeeBefore = employeeDao.count();

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
        employmentContractDao.delete(contract.getId());

        // Then
        long sizeOfContractAfter = employmentContractDao.count();
        long sizeOfEmployeeAfter = employeeDao.count();

        Assert.assertEquals(sizeOfContractBefore, sizeOfContractAfter);
        Assert.assertEquals(sizeOfEmployeeBefore, sizeOfEmployeeAfter);
        Assert.assertEquals(contractFromDatabase.getEmployees().size(), 1);
    }
}