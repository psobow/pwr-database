package com.pwr.pwrdatabase.controller;

import com.pwr.pwrdatabase.dto.DepartmentDto;
import com.pwr.pwrdatabase.mapper.DepartmentMapper;
import com.pwr.pwrdatabase.service.DepartmentService;
import java.util.Set;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/departments")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class DepartmentController
{
    private final DepartmentService SERVICE;
    private final DepartmentMapper MAPPER;

    @GetMapping
    public Set<DepartmentDto> getDepartments()
    {
        return MAPPER.mapToDepartmentsDto(SERVICE.findAll());
    }

    @GetMapping("/{id}")
    public DepartmentDto getDepartment(@PathVariable("id") final Long ID)
    {
        return MAPPER.mapToDepartmentDto(SERVICE.findOne(ID));
    }

    @DeleteMapping("/{id}")
    public void deleteDepartment(@PathVariable("id") final Long ID)
    {
        SERVICE.delete(ID);
    }

    @PutMapping
    public DepartmentDto updateDepartment(@Valid @RequestBody final DepartmentDto DEPARTMENT_DTO)
    {
        return MAPPER.mapToDepartmentDto(SERVICE.save(MAPPER.mapToDepartment(DEPARTMENT_DTO)));
    }

    @PostMapping
    public void createDepartment(@Valid @RequestBody final DepartmentDto DEPARTMENT_DTO)
    {
        SERVICE.save(MAPPER.mapToDepartment(DEPARTMENT_DTO));
    }
}
