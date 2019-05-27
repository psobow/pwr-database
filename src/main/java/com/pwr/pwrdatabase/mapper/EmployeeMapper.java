package com.pwr.pwrdatabase.mapper;

import com.pwr.pwrdatabase.domain.DailyEmployeeReport;
import com.pwr.pwrdatabase.domain.Department;
import com.pwr.pwrdatabase.domain.Employee;
import com.pwr.pwrdatabase.domain.EmployeeAbsent;
import com.pwr.pwrdatabase.domain.WorkStartFinishEvent;
import com.pwr.pwrdatabase.dto.EmployeeDto;
import com.pwr.pwrdatabase.service.AbsentService;
import com.pwr.pwrdatabase.service.DepartmentService;
import com.pwr.pwrdatabase.service.ReportService;
import com.pwr.pwrdatabase.service.WorkEventService;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper
{
    @Autowired private ContractMapper contractMapper;

    @Autowired private WorkEventService workEventService;
    @Autowired private ReportService reportService;
    @Autowired private AbsentService absentService;
    @Autowired private DepartmentService departmentService;

    public Employee mapToEmployee(final EmployeeDto EMPLOYEE_DTO)
    {
        Set<WorkStartFinishEvent> eventsFromDatabase = workEventService.findAll(EMPLOYEE_DTO.getWorkStartFinishEventsID());
        Set<DailyEmployeeReport> reportsFromDatabase = reportService.findAll(EMPLOYEE_DTO.getDailyEmployeeReportsID());
        Set<EmployeeAbsent> absentsFromDatabase = absentService.findAll(EMPLOYEE_DTO.getEmployeeAbsentsID());
        Set<Department> departmentsFromDatabase = departmentService.findAll(EMPLOYEE_DTO.getDepartmentsID());

        return new Employee(EMPLOYEE_DTO.getId(),
                            EMPLOYEE_DTO.getFirstName(),
                            EMPLOYEE_DTO.getSecondName(),
                            EMPLOYEE_DTO.getPESEL(),
                            EMPLOYEE_DTO.getPhoneNumber(),
                            EMPLOYEE_DTO.getHireDate(),
                            EMPLOYEE_DTO.getCurrentHolidays(),
                            EMPLOYEE_DTO.isActive(),
                            contractMapper.mapToContract(EMPLOYEE_DTO.getEmploymentContractDto()),
                            eventsFromDatabase,
                            reportsFromDatabase,
                            absentsFromDatabase,
                            departmentsFromDatabase);
    }

    public EmployeeDto mapToEmployeeDto(final Employee EMPLOYEE)
    {
        Set<Long> eventsID = new HashSet<>();
        Set<Long> reportsID = new HashSet<>();
        Set<Long> absentsID = new HashSet<>();
        Set<Long> departmentsID = new HashSet<>();

        EMPLOYEE.getWorkStartFinishEvents().forEach(event -> eventsID.add(event.getId()));
        EMPLOYEE.getDailyEmployeeReports().forEach(report -> reportsID.add(report.getId()));
        EMPLOYEE.getEmployeeAbsents().forEach(absent -> absentsID.add(absent.getId()));
        EMPLOYEE.getDepartments().forEach(department -> departmentsID.add(department.getId()));
        return new EmployeeDto(EMPLOYEE.getId(),
                                           EMPLOYEE.getFirstName(),
                                           EMPLOYEE.getSecondName(),
                                           EMPLOYEE.getPESEL(),
                                           EMPLOYEE.getPhoneNumber(),
                                           EMPLOYEE.getHireDate(),
                                           EMPLOYEE.getCurrentHolidays(),
                                           EMPLOYEE.isActive(),
                                           contractMapper.mapToContractDto(EMPLOYEE.getEmploymentContract()),
                                           eventsID,
                                           reportsID,
                                           absentsID,
                                           departmentsID);
    }

    public Set<Employee> mapToEmployees(final Set<EmployeeDto> EMPLOYEES_DTO)
    {
        return EMPLOYEES_DTO.stream()
                            .map(this::mapToEmployee)
                            .collect(Collectors.toSet());
    }

    public Set<EmployeeDto> mapToEmployeesDto(final Set<Employee> EMPLOYEES)
    {
        return EMPLOYEES.stream()
                        .map(this::mapToEmployeeDto)
                        .collect(Collectors.toSet());
    }
}
