package com.pwr.pwrdatabase.mapper;

import com.pwr.pwrdatabase.domain.Employee;
import com.pwr.pwrdatabase.domain.EmploymentContract;
import com.pwr.pwrdatabase.domain.WorkStartFinishEvent;
import com.pwr.pwrdatabase.dto.WorkStartFinishEventDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
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
    @Autowired WorkEventMapper workEventMapper;

    @Test
    public void mapperTest()
    {
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

        WorkStartFinishEvent event = new WorkStartFinishEvent();
        event.setEventDateTime(LocalDateTime.now());
        event.setBeginning(true);

        // Set fields responsible for foreign keys
        employee.setEmploymentContract(contract);
        contract.getEmployees().add(employee);

        employee.getWorkStartFinishEvents().add(event);
        event.setEmployee(employee);

        Set<WorkStartFinishEvent> events = new HashSet<>();
        events.add(event);

        Set<WorkStartFinishEventDto> eventDto = workEventMapper.mapToEventsDto(events);

        /*
        List<WorkStartFinishEventDto> eventDto2 = workEventMapper.mapToEventsDto(
                    new ArrayList<WorkStartFinishEvent>() {{ add(new WorkStartFinishEvent()); }});
        */
    }
}