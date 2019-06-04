package com.pwr.pwrdatabase.mapper;

import com.pwr.pwrdatabase.domain.Employee;
import com.pwr.pwrdatabase.domain.WorkStartFinishEvent;
import com.pwr.pwrdatabase.dto.WorkStartFinishEventDto;
import com.pwr.pwrdatabase.service.EmployeeService;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkEventMapper
{
    private final EmployeeService EMPLOYEE_SERVICE;

    public WorkStartFinishEvent mapToEvent(final WorkStartFinishEventDto EVENT_DTO)
    {
        Employee employeeFromDatabase = this.EMPLOYEE_SERVICE.findOne(EVENT_DTO.getEmployeeDtoID());
        return new WorkStartFinishEvent(EVENT_DTO.getId(),
                                        EVENT_DTO.getEventDate(),
                                        EVENT_DTO.getEventTime(),
                                        EVENT_DTO.getBeginning(),
                                        employeeFromDatabase);
    }

    public WorkStartFinishEventDto mapToEventDto(final WorkStartFinishEvent EVENT)
    {
        Long employeeID = EVENT.getEmployee().getId();
        return new WorkStartFinishEventDto(EVENT.getId(),
                                           EVENT.getEventDate(),
                                           EVENT.getEventTime(),
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
