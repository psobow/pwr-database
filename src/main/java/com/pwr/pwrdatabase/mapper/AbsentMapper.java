package com.pwr.pwrdatabase.mapper;

import com.pwr.pwrdatabase.domain.EmployeeAbsent;
import com.pwr.pwrdatabase.dto.EmployeeAbsentDto;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AbsentMapper
{
    @Autowired private EmployeeMapper employeeMapper;

    public EmployeeAbsent mapToAbsent(final EmployeeAbsentDto ABSENT_DTO)
    {
        return new EmployeeAbsent(ABSENT_DTO.getId(),
                                  ABSENT_DTO.getTypeOfAbsent(),
                                  ABSENT_DTO.getAbsentStartDate(),
                                  ABSENT_DTO.getAbsentDurationInDays(),
                                  employeeMapper.mapToEmployee(ABSENT_DTO.getEmployeeDto()));
    }

    public EmployeeAbsentDto mapToAbsentDto(final EmployeeAbsent ABSENT)
    {
        return new EmployeeAbsentDto(ABSENT.getId(),
                                     ABSENT.getTypeOfAbsent(),
                                     ABSENT.getAbsentStartDate(),
                                     ABSENT.getAbsentDurationInDays(),
                                     employeeMapper.mapToEmployeeDto(ABSENT.getEmployee()));
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
