package com.pwr.pwrdatabase.mapper;

import com.pwr.pwrdatabase.domain.DailyEmployeeReport;
import com.pwr.pwrdatabase.domain.Employee;
import com.pwr.pwrdatabase.domain.EmploymentContract;
import com.pwr.pwrdatabase.dto.DailyEmployeeReportDto;
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
public class ReportMapperTest
{
    @Autowired private ReportMapper reportMapper;

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

        DailyEmployeeReport report = new DailyEmployeeReport();
        report.setReportDate(LocalDate.now());
        report.setWorkTime(LocalTime.of(7, 50));
        report.setEarnedCash(100);
        report.setLateness(true);
        report.setLatenessMinutes(10);

        // Set fields responsible for foreign keys
        employee.setEmploymentContract(contract);
        contract.getEmployees().add(employee);

        report.setEmployee(employee);
        employee.getDailyEmployeeReports().add(report);

        // When

        DailyEmployeeReportDto mappedObject = reportMapper.mapToReportDto(report);

        DailyEmployeeReport mappedBackObject = reportMapper.mapToReport(mappedObject);


        //Sztuczka inicjalizacyjna z wykorzystaniem anonimowych klas oraz bloków inicjalizacyjnych

        Set<DailyEmployeeReportDto> objectDtoSet = reportMapper.mapToReportsDto(
                new HashSet<DailyEmployeeReport>()
                {
                    {
                        add(report);
                    }
                });

        // Then
        Assert.assertEquals("DailyEmployeeReportDto", objectDtoSet.stream()
                                                             .findAny()
                                                             .orElseThrow( () -> new NoSuchElementException())
                                                             .getClass()
                                                             .getSimpleName());

        Assert.assertEquals("DailyEmployeeReportDto", mappedObject.getClass().getSimpleName());
        Assert.assertEquals(report.getId(), mappedObject.getId());

        Assert.assertEquals("DailyEmployeeReport", mappedBackObject.getClass().getSimpleName());
        Assert.assertEquals(report.getId(), mappedBackObject.getId());
    }
}