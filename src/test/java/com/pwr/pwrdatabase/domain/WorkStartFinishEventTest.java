package com.pwr.pwrdatabase.domain;


import com.pwr.pwrdatabase.domain.dao.EmployeeDao;
import com.pwr.pwrdatabase.domain.dao.EmploymentContractDao;
import com.pwr.pwrdatabase.domain.dao.WorkStartFinishEventDao;
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
public class WorkStartFinishEventTest
{
    @Autowired WorkStartFinishEventDao workStartFinishEventDao;
    @Autowired EmploymentContractDao employmentContractDao;
    @Autowired EmployeeDao employeeDao;

    @Test
    public void persistEventWithEmployeeAndContract()
    {
        // Given
        WorkStartFinishEvent event = new WorkStartFinishEvent();
        event.setEventDate(LocalDate.now());
        event.setEventTime(LocalTime.now());
        event.setBeginning(true);

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

        // Set fields responsible for foreign keys
        employee.setEmploymentContract(contract);
        contract.getEmployees().add(employee);

        employee.getWorkStartFinishEvents().add(event);
        event.setEmployee(employee);

        // Save size of entities
        long initSizeOfContract = employmentContractDao.count();
        long initSizeOfEmployee = employeeDao.count();
        long initSizeOfEvent = workStartFinishEventDao.count();

        // When
        employmentContractDao.save(contract);
        employeeDao.save(employee); // save employe and event

        EmploymentContract contractFromDatabase = employmentContractDao.findOne(contract.getId());

        // Clean up
        // Break relation betwen contract - employee
        contract.getEmployees().remove(employee);
        employee.setEmploymentContract(null);

        employeeDao.save(employee); // refresh data

        employmentContractDao.delete(contract.getId());
        employeeDao.delete(employee); // delete employe and event

        // Then
        long terminalSizeOfContract = employmentContractDao.count();
        long terminalSizeOfEmployee = employeeDao.count();
        long terminalSizeOfEvent = workStartFinishEventDao.count();

        Assert.assertEquals(initSizeOfContract, terminalSizeOfContract);
        Assert.assertEquals(initSizeOfEmployee, terminalSizeOfEmployee);
        Assert.assertEquals(initSizeOfEvent, terminalSizeOfEvent);

        Assert.assertEquals(contractFromDatabase.getEmployees().size(), 1);
    }

}