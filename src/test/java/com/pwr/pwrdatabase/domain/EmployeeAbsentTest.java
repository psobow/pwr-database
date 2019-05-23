package com.pwr.pwrdatabase.domain;

import com.pwr.pwrdatabase.domain.dao.EmployeeAbsentDao;
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
public class EmployeeAbsentTest
{
    @Autowired private EmployeeDao employeeDao;
    @Autowired private EmploymentContractDao employmentContractDao;
    @Autowired private EmployeeAbsentDao employeeAbsentDao;

    @Test
    public void persistEmployeeAbsentWithEmployeeAndContract()
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
        long initSizeOfContract = employmentContractDao.count();
        long initSizeOfEmployee = employeeDao.count();
        long initSizeOfAbsent = employeeAbsentDao.count();

        // When
        employmentContractDao.save(contract);
        employeeDao.save(employee);

        // Clean up
        // Break relation betwen contract - employee
        contract.getEmployees().remove(employee);
        employee.setEmploymentContract(null);

        employeeDao.save(employee); // Refresh employee, contract and absent in database

        employmentContractDao.delete(contract.getId());
        employeeDao.delete(employee);

        // Then
        long terminalSizeOfContract = employmentContractDao.count();
        long terminalSizeOfEmployee = employeeDao.count();
        long terminalSizeOfAbsent = employeeAbsentDao.count();

        Assert.assertEquals(initSizeOfContract, terminalSizeOfContract);
        Assert.assertEquals(initSizeOfEmployee, terminalSizeOfEmployee);
        Assert.assertEquals(initSizeOfAbsent, terminalSizeOfAbsent);
    }
}