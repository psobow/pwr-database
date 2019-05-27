package com.pwr.pwrdatabase.mapper;

import com.pwr.pwrdatabase.domain.Employee;
import com.pwr.pwrdatabase.domain.EmploymentContract;
import com.pwr.pwrdatabase.dto.EmploymentContractDto;
import com.pwr.pwrdatabase.service.EmployeeService;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContractMapper
{
    @Autowired private EmployeeService employeeService;

    public EmploymentContract mapToContract(final EmploymentContractDto CONTRACT_DTO)
    {
        Set<Employee> employeesFromDatabase = employeeService.findAll(CONTRACT_DTO.getEmployeesID());
        return new EmploymentContract(CONTRACT_DTO.getId(),
                                      CONTRACT_DTO.getEmploymentType(),
                                      CONTRACT_DTO.getHourPay(),
                                      CONTRACT_DTO.getShiftBegin(),
                                      CONTRACT_DTO.getShiftEnd(),
                                      CONTRACT_DTO.getQuantityOfFullWorkDaysForOneHoliday(),
                                      employeesFromDatabase);
    }

    public EmploymentContractDto mapToContractDto(final EmploymentContract CONTRACT)
    {
        Set<Long> employeesID = new HashSet<>();
        CONTRACT.getEmployees().forEach(employee -> employeesID.add(employee.getId()));
        return new EmploymentContractDto(CONTRACT.getId(),
                                         CONTRACT.getEmploymentType(),
                                         CONTRACT.getHourPay(),
                                         CONTRACT.getShiftBegin(),
                                         CONTRACT.getShiftEnd(),
                                         CONTRACT.getQuantityOfFullWorkDaysForOneHoliday(),
                                         employeesID);
    }
}
