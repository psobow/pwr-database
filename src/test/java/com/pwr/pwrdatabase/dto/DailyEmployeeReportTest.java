package com.pwr.pwrdatabase.dto;

import static org.junit.Assert.*;

import com.pwr.pwrdatabase.dto.dao.DailyEmployeeReportDao;
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
public class DailyEmployeeReportTest
{
    @Autowired private EmployeeDao employeeDao;
    @Autowired private EmploymentContractDao employmentContractDao;
    @Autowired private DailyEmployeeReportDao dailyEmployeeReportDao;

    @Test
    public void persistReport()
    {
        // Given
        DailyEmployeeReport report = new DailyEmployeeReport();
        report.setReportDate(LocalDate.now());
        report.setWorkTime(LocalTime.of(7,50));
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
        long sizeOfContractBefore = employmentContractDao.count();
        long sizeOfEmployeeBefore = employeeDao.count();
        long sizeOfReportBefore = dailyEmployeeReportDao.count();

        // When
        employmentContractDao.save(contract);
        dailyEmployeeReportDao.save(report);

        // Clean up
        employmentContractDao.delete(contract.getId());

        // Then
        long sizeOfContractAfter = employmentContractDao.count();
        long sizeOfEmployeeAfter = employeeDao.count();
        long sizeOfReportAfter= dailyEmployeeReportDao.count();

        Assert.assertEquals(sizeOfContractBefore, sizeOfContractAfter);
        Assert.assertEquals(sizeOfEmployeeBefore, sizeOfEmployeeAfter);
        Assert.assertEquals(sizeOfReportBefore, sizeOfReportAfter);

    }
}