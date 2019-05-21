package com.pwr.pwrdatabase.dto;

import static org.junit.Assert.*;

import com.pwr.pwrdatabase.dto.dao.EmployeeDao;
import com.pwr.pwrdatabase.dto.dao.EmploymentContractDao;
import com.pwr.pwrdatabase.dto.dao.WorkStartFinishEventDao;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
public class WorkStartFinishEventTest
{
    @Autowired
    WorkStartFinishEventDao workStartFinishEventDao;

    @Autowired
    EmploymentContractDao employmentContractDao;

    @Autowired
    EmployeeDao employeeDao;

    @Test
    public void persistEvent()
    {
        // Given
        WorkStartFinishEvent event = new WorkStartFinishEvent();
        event.setEventDateTime(LocalDateTime.now());
        event.setBeginning(true);

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

        employee.getWorkStartFinishEvents().add(event);
        event.setEmployee(employee);

        // Save size of entities
        long sizeOfContractBefore = employmentContractDao.count();
        long sizeOfEmployeeBefore = employeeDao.count();
        long sizeOfEventBefore = workStartFinishEventDao.count();

        // When
        workStartFinishEventDao.save(event);
        long idEvent = event.getId();

        // Clean up
        employeeDao.delete(employee.getId());
        employmentContractDao.delete(contract.getId());

        long sizeOfContractAfter = employmentContractDao.count();
        long sizeOfEmployeeAfter = employeeDao.count();
        long sizeOfEventAfter = workStartFinishEventDao.count();

        // Then
        log.info("New event ID: " + idEvent);

        Assert.assertEquals(sizeOfContractBefore, sizeOfContractAfter);
        Assert.assertEquals(sizeOfEmployeeBefore, sizeOfEmployeeAfter);
        Assert.assertEquals(sizeOfEventBefore, sizeOfEventAfter);
    }

}