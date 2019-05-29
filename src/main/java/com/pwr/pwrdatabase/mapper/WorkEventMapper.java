package com.pwr.pwrdatabase.mapper;

import com.pwr.pwrdatabase.domain.Employee;
import com.pwr.pwrdatabase.domain.WorkStartFinishEvent;
import com.pwr.pwrdatabase.dto.WorkStartFinishEventDto;
import com.pwr.pwrdatabase.service.EmployeeService;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorkEventMapper
{
    @Autowired EmployeeService employeeService;

    public WorkStartFinishEvent mapToEvent(final WorkStartFinishEventDto EVENT_DTO)
    {
        Employee employeeFromDatabase = employeeService.findOne(EVENT_DTO.getEmployeeDtoID());
        return new WorkStartFinishEvent(EVENT_DTO.getId(),
                                        EVENT_DTO.getEventDateTime(),
                                        EVENT_DTO.isBeginning(),
                                        employeeFromDatabase);
    }

    public WorkStartFinishEventDto mapToEventDto(final WorkStartFinishEvent EVENT)
    {
        Long employeeID = EVENT.getEmployee().getId();
        return new WorkStartFinishEventDto(EVENT.getId(),
                                           EVENT.getEventDateTime(),
                                           EVENT.isBeginning(),
                                           employeeID);
    }

    public Set<WorkStartFinishEvent> mapToEvents(Set<WorkStartFinishEventDto> EVENTS_DTO)
    {
        return EVENTS_DTO.stream()
                         .map(this::mapToEvent)
                         .collect(Collectors.toSet());
    }

    public Set<WorkStartFinishEventDto> mapToEventsDto(final Set<WorkStartFinishEvent> EVENTS)
    {
        return EVENTS.stream()
                     .map(this::mapToEventDto)
                     .collect(Collectors.toSet());
    }
}
