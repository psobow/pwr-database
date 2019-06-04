package com.pwr.pwrdatabase.mapper;

import com.pwr.pwrdatabase.domain.DailyEmployeeReport;
import com.pwr.pwrdatabase.domain.Department;
import com.pwr.pwrdatabase.domain.Employee;
import com.pwr.pwrdatabase.domain.EmployeeAbsent;
import com.pwr.pwrdatabase.domain.EmploymentContract;
import com.pwr.pwrdatabase.domain.WorkStartFinishEvent;
import com.pwr.pwrdatabase.dto.EmployeeDto;
import com.pwr.pwrdatabase.service.AbsentService;
import com.pwr.pwrdatabase.service.ContractService;
import com.pwr.pwrdatabase.service.DepartmentService;
import com.pwr.pwrdatabase.service.ReportService;
import com.pwr.pwrdatabase.service.WorkEventService;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeMapper
{
    private final ContractService CONTRACT_SERVICE;
    private final WorkEventService WORK_EVENT_SERVICE;
    private final ReportService REPORT_SERVICE;
    private final AbsentService ABSENT_SERVICE;
    private final DepartmentService DEPARTMENT_SERVICE;

    public Employee mapToEmployee(final EmployeeDto EMPLOYEE_DTO)
    {
        EmploymentContract contractFromDatabase = this.CONTRACT_SERVICE.findOne(EMPLOYEE_DTO.getEmploymentContractDtoID());
        Set<WorkStartFinishEvent> eventsFromDatabase = this.WORK_EVENT_SERVICE.findAll(EMPLOYEE_DTO.getWorkStartFinishEventsID());
        Set<DailyEmployeeReport> reportsFromDatabase = this.REPORT_SERVICE.findAll(EMPLOYEE_DTO.getDailyEmployeeReportsID());
        Set<EmployeeAbsent> absentsFromDatabase = this.ABSENT_SERVICE.findAll(EMPLOYEE_DTO.getEmployeeAbsentsID());
        Set<Department> departmentsFromDatabase = this.DEPARTMENT_SERVICE.findAll(EMPLOYEE_DTO.getDepartmentsID());

        return new Employee(EMPLOYEE_DTO.getId(),
                            EMPLOYEE_DTO.getFirstName(),
                            EMPLOYEE_DTO.getSecondName(),
                            EMPLOYEE_DTO.getPESEL(),
                            EMPLOYEE_DTO.getPhoneNumber(),
                            EMPLOYEE_DTO.getHireDate(),
                            //EMPLOYEE_DTO.getCurrentHolidays(),
                            //EMPLOYEE_DTO.isActive(),
                            contractFromDatabase,
                            eventsFromDatabase,
                            reportsFromDatabase,
                            absentsFromDatabase,
                            departmentsFromDatabase);
    }

    public EmployeeDto mapToEmployeeDto(final Employee EMPLOYEE)
    {
        Long contractID = EMPLOYEE.getEmploymentContract().getId();
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
                                           //EMPLOYEE.getCurrentHolidays(),
                                           //EMPLOYEE.isActive(),
                                           contractID,
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
