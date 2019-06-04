package com.pwr.pwrdatabase.controller;

import com.pwr.pwrdatabase.dto.WorkStartFinishEventDto;
import com.pwr.pwrdatabase.mapper.WorkEventMapper;
import com.pwr.pwrdatabase.service.WorkEventService;
import java.util.Set;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/events")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class WorkEventController
{
    private final WorkEventService SERVICE;
    private final WorkEventMapper MAPPER;

    @GetMapping
    public Set<WorkStartFinishEventDto> getEvents()
    {
        return MAPPER.mapToEventsDto(SERVICE.findAll());
    }

    @GetMapping("/{id}")
    public WorkStartFinishEventDto getEvent(@PathVariable("id") final Long ID)
    {
        return MAPPER.mapToEventDto(SERVICE.findOne(ID));
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable("id") final Long ID)
    {
        SERVICE.delete(ID);
    }

    //  (consumes = "application/x-www-form-urlencoded")
    @PostMapping
    public void createEvent(@Valid @RequestBody final WorkStartFinishEventDto EVENT_DTO)
    {
        SERVICE.save(MAPPER.mapToEvent(EVENT_DTO));
    }
}
