package com.pwr.pwrdatabase.domain;

import com.pwr.pwrdatabase.domain.dao.DailyEmployeeReportDao;
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
public class DailyEmployeeReportTest
{
    @Autowired private EmployeeDao employeeDao;
    @Autowired private EmploymentContractDao employmentContractDao;
    @Autowired private DailyEmployeeReportDao dailyEmployeeReportDao;

    @Test
    public void persistReportWithEmployeeAndContract()
    {
        // Given
        DailyEmployeeReport report = new DailyEmployeeReport();
        report.setReportDate(LocalDate.now());
        report.setWorkTime(LocalTime.of(7, 50));
        report.setEarnedCash(100);
        report.setLateness(true);
        report.setLatenessTime(LocalTime.of(0, 10));

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

        report.setEmployee(employee);
        employee.getDailyEmployeeReports().add(report);

        // Save size of entities
        long initSizeOfContract = employmentContractDao.count();
        long initSizeOfEmployee = employeeDao.count();
        long initSizeOfReport = dailyEmployeeReportDao.count();

        // When
        employmentContractDao.save(contract); // First has to be persisted contract
        employeeDao.save(employee); // Persist Employee and Report / Refresh Contract
        //-- Employee jest wlascicielem relacji Employee-EmploymentContract. Dlatego najpierw musibyc utrwalony EmploymentContract


        // Clean up
        // Break relation betwen contract - employee
        contract.getEmployees().remove(employee);
        employee.setEmploymentContract(null);

        employeeDao.save(employee); // Refresh employee, contract, report

        // Since now objects Employee and EmploymentContract should not have refference to each other in database
        Employee employeeFromDatabase = employeeDao.findOne(employee.getId());
        EmploymentContract contractFromDatabase = employmentContractDao.findOne(contract.getId());

        // Since relation has been broke deletion order is irrelevant.
        employeeDao.delete(employee.getId());  // delete employee and daily employee report
        employmentContractDao.delete(contract.getId()); // delete contract from database

        // Then
        long terminalSizeOfContract = employmentContractDao.count();
        long terminalSizeOfEmployee = employeeDao.count();
        long terminalSizeOfReport = dailyEmployeeReportDao.count();

        Assert.assertEquals(initSizeOfContract, terminalSizeOfContract);
        Assert.assertEquals(initSizeOfEmployee, terminalSizeOfEmployee);
        Assert.assertEquals(initSizeOfReport, terminalSizeOfReport);

        Assert.assertNull(employeeFromDatabase.getEmploymentContract());
        Assert.assertEquals(contractFromDatabase.getEmployees().size(), 0);

    }
}