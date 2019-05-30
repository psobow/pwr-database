package com.pwr.pwrdatabase.mapper;

import com.pwr.pwrdatabase.domain.Department;
import com.pwr.pwrdatabase.domain.Employee;
import com.pwr.pwrdatabase.dto.DepartmentDto;
import com.pwr.pwrdatabase.service.EmployeeService;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DepartmentMapper
{
    private final EmployeeService EMPLOYEE_SERVICE;

    public Department mapToDepartment(final DepartmentDto DEPARTMENT_DTO)
    {
        Set<Employee> employeesFromDatabase = this.EMPLOYEE_SERVICE.findAll(DEPARTMENT_DTO.getEmployeesID());
        return new Department(DEPARTMENT_DTO.getId(),
                              DEPARTMENT_DTO.getCity(),
                              DEPARTMENT_DTO.getZipCode(),
                              DEPARTMENT_DTO.getLocalNumber(),
                              employeesFromDatabase);
    }

    public DepartmentDto mapToDepartmentDto(final Department DEPARTMENT)
    {
        Set<Long> employeesID = new HashSet<>();
        DEPARTMENT.getEmployees().forEach(employee -> employeesID.add(employee.getId()));
        return new DepartmentDto(DEPARTMENT.getId(),
                                 DEPARTMENT.getCity(),
                                 DEPARTMENT.getZipCode(),
                                 DEPARTMENT.getLocalNumber(),
                                 employeesID);
    }

    public Set<Department> mapToDepartments(final Set<DepartmentDto> DEPARTMENTS_DTO)
    {
        return DEPARTMENTS_DTO.stream()
                              .map(this::mapToDepartment)
                              .collect(Collectors.toSet());
    }

    public Set<DepartmentDto> mapToDepartmentsDto(final Set<Department> DEPARTMENTS)
    {
        return DEPARTMENTS.stream()
                          .map(this::mapToDepartmentDto)
                          .collect(Collectors.toSet());
    }
}
