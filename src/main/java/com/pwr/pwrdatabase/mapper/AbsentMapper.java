package com.pwr.pwrdatabase.mapper;

import com.pwr.pwrdatabase.domain.Employee;
import com.pwr.pwrdatabase.domain.EmployeeAbsent;
import com.pwr.pwrdatabase.dto.EmployeeAbsentDto;
import com.pwr.pwrdatabase.service.EmployeeService;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AbsentMapper
{
    @Autowired private EmployeeService employeeService;

    public EmployeeAbsent mapToAbsent(final EmployeeAbsentDto ABSENT_DTO)
    {
        Employee employeeFromDatabase = employeeService.findOne(ABSENT_DTO.getEmployeeDtoID());
        return new EmployeeAbsent(ABSENT_DTO.getId(),
                                  ABSENT_DTO.getTypeOfAbsent(),
                                  ABSENT_DTO.getAbsentStartDate(),
                                  ABSENT_DTO.getAbsentDurationInDays(),
                                  employeeFromDatabase);
    }

    public EmployeeAbsentDto mapToAbsentDto(final EmployeeAbsent ABSENT)
    {
        Long employeeID = ABSENT.getEmployee().getId();
        return new EmployeeAbsentDto(ABSENT.getId(),
                                     ABSENT.getTypeOfAbsent(),
                                     ABSENT.getAbsentStartDate(),
                                     ABSENT.getAbsentDurationInDays(),
                                     employeeID);
    }

    public Set<EmployeeAbsent> mapToAbsents(final Set<EmployeeAbsentDto> ABSENTS_DTO)
    {
        return ABSENTS_DTO.stream()
                          .map(this::mapToAbsent)
                          .collect(Collectors.toSet());
    }

    public Set<EmployeeAbsentDto> mapToAbsentsDto(final Set<EmployeeAbsent> ABSENTS)
    {
        return ABSENTS.stream()
                      .map(this::mapToAbsentDto)
                      .collect(Collectors.toSet());
    }
}
