package com.pwr.pwrdatabase.controller;

import com.pwr.pwrdatabase.dto.EmployeeDto;
import com.pwr.pwrdatabase.mapper.EmployeeMapper;
import com.pwr.pwrdatabase.service.EmployeeService;
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
@RequestMapping("/v1/employees")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class EmployeeController
{
    private final EmployeeService SERVICE;
    private final EmployeeMapper MAPPER;

    @GetMapping
    public Set<EmployeeDto> getEmployees()
    {
        return MAPPER.mapToEmployeesDto(SERVICE.findAll());
    }

    @GetMapping("/{id}")
    public EmployeeDto getEmployee(@PathVariable("id") final Long ID)
    {
        return MAPPER.mapToEmployeeDto(SERVICE.findOne(ID));
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable("id") final Long ID)
    {
        SERVICE.delete(ID);
    }

    // Dla tej metody ID pochodzi od clienta
    @PutMapping
    public EmployeeDto updateEmployee(@Valid @RequestBody final EmployeeDto EMPLOYEE_DTO)
    {
        return MAPPER.mapToEmployeeDto(SERVICE.saveAndRefreshAllOtherEntities(MAPPER.mapToEmployee(EMPLOYEE_DTO)));
    }

    //Dla tej metody ID pochodzi od serwera
    @PostMapping
    public void createEmployee(@Valid @RequestBody final EmployeeDto EMPLOYEE_DTO)
    {
        SERVICE.saveAndRefreshAllOtherEntities(MAPPER.mapToEmployee(EMPLOYEE_DTO));
    }
}
