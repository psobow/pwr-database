package com.pwr.pwrdatabase.controller;

import com.pwr.pwrdatabase.dto.EmployeeAbsentDto;
import com.pwr.pwrdatabase.mapper.AbsentMapper;
import com.pwr.pwrdatabase.service.AbsentService;
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
@RequestMapping("/v1/absents")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AbsentController
{
    private final AbsentService SERVICE;
    private final AbsentMapper MAPPER;

    @GetMapping
    public Set<EmployeeAbsentDto> getAbsents()
    {
        return MAPPER.mapToAbsentsDto(SERVICE.findAll());
    }

    @GetMapping("/{id}")
    public EmployeeAbsentDto getAbsent(@PathVariable("id") final Long ID)
    {
        return MAPPER.mapToAbsentDto(SERVICE.findOne(ID));
    }

    @DeleteMapping("/{id}")
    public void deleteAbsent(@PathVariable("id") final Long ID)
    {
        SERVICE.delete(ID);
    }

    @PostMapping
    public void createAbsent(@Valid @RequestBody final EmployeeAbsentDto absentDto)
    {
        SERVICE.save(MAPPER.mapToAbsent(absentDto));
    }
}
