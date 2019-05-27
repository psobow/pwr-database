package com.pwr.pwrdatabase.mapper;

import com.pwr.pwrdatabase.domain.WorkStartFinishEvent;
import com.pwr.pwrdatabase.dto.WorkStartFinishEventDto;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorkEventMapper
{
    @Autowired EmployeeMapper employeeMapper;

    public WorkStartFinishEvent mapToEvent(final WorkStartFinishEventDto EVENT_DTO)
    {
        return new WorkStartFinishEvent(EVENT_DTO.getId(),
                                        EVENT_DTO.getEventDateTime(),
                                        EVENT_DTO.isBeginning(),
                                        employeeMapper.mapToEmployee(EVENT_DTO.getEmployeeDto()));
    }

    public WorkStartFinishEventDto mapToEventDto(final WorkStartFinishEvent EVENT)
    {
        return new WorkStartFinishEventDto(EVENT.getId(),
                                           EVENT.getEventDateTime(),
                                           EVENT.isBeginning(),
                                           employeeMapper.mapToEmployeeDto(EVENT.getEmployee()));
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
