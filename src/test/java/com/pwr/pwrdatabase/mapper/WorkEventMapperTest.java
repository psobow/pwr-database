package com.pwr.pwrdatabase.mapper;

import com.pwr.pwrdatabase.domain.Employee;
import com.pwr.pwrdatabase.domain.EmploymentContract;
import com.pwr.pwrdatabase.domain.WorkStartFinishEvent;
import com.pwr.pwrdatabase.dto.WorkStartFinishEventDto;
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
public class WorkEventMapperTest
{
    @Autowired private WorkEventMapper workEventMapper;

    @Test
    public void mapperTest()
    {
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

        WorkStartFinishEvent event = new WorkStartFinishEvent();
        event.setEventDate(LocalDate.now());
        event.setEventTime(LocalTime.now());
        event.setBeginning(true);

        // Set fields responsible for foreign keys
        employee.setEmploymentContract(contract);
        contract.getEmployees().add(employee);

        employee.getWorkStartFinishEvents().add(event);
        event.setEmployee(employee);

        WorkStartFinishEventDto mappedEvent = workEventMapper.mapToEventDto(event);

        WorkStartFinishEvent mappedBackEvent = workEventMapper.mapToEvent(mappedEvent);


        //Sztuczka inicjalizacyjna z wykorzystaniem anonimowych klas oraz bloków inicjalizacyjnych

        Set<WorkStartFinishEventDto> eventDtoSet = workEventMapper.mapToEventsDto(
                    new HashSet<WorkStartFinishEvent>()
                    {
                        {
                            add(event);
                        }
                    });

        Assert.assertEquals("WorkStartFinishEventDto", eventDtoSet.stream()
                                                                  .findAny()
                                                                  .orElseThrow( () -> new NoSuchElementException())
                                                                  .getClass()
                                                                  .getSimpleName());

        Assert.assertEquals("WorkStartFinishEventDto", mappedEvent.getClass().getSimpleName());
        Assert.assertEquals((long) event.getId(), (long) mappedEvent.getId());

        Assert.assertEquals("WorkStartFinishEvent", mappedBackEvent.getClass().getSimpleName());
        Assert.assertEquals(event.getId(), mappedBackEvent.getId());

        Assert.assertEquals((Long) event.getEmployee().getId(), (Long) mappedEvent.getEmployeeDtoID());
    }
}