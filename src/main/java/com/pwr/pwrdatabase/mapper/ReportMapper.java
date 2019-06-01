package com.pwr.pwrdatabase.mapper;

import com.pwr.pwrdatabase.domain.DailyEmployeeReport;
import com.pwr.pwrdatabase.domain.Employee;
import com.pwr.pwrdatabase.dto.DailyEmployeeReportDto;
import com.pwr.pwrdatabase.service.EmployeeService;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReportMapper
{
    private final EmployeeService EMPLOYEE_SERVICE;

    public DailyEmployeeReport mapToReport(final DailyEmployeeReportDto REPORT_DTO)
    {
        Employee employeeFromDatabase = this.EMPLOYEE_SERVICE.findOne(REPORT_DTO.getEmployeeDtoID());
        return new DailyEmployeeReport(REPORT_DTO.getId(),
                                       REPORT_DTO.getReportDate(),
                                       REPORT_DTO.getWorkTime(),
                                       REPORT_DTO.getEarnedCash(),
                                       REPORT_DTO.isLateness(),
                                       REPORT_DTO.getLatenessMinutes(),
                                       employeeFromDatabase);
    }

    public DailyEmployeeReportDto mapToReportDto(final DailyEmployeeReport REPORT)
    {
        Long employeeID = REPORT.getEmployee().getId();
        return new DailyEmployeeReportDto(REPORT.getId(),
                                          REPORT.getReportDate(),
                                          REPORT.getWorkTime(),
                                          REPORT.getEarnedCash(),
                                          REPORT.isLateness(),
                                          REPORT.getLatenessMinutes(),
                                          employeeID);
    }

    public Set<DailyEmployeeReport> mapToReports(final Set<DailyEmployeeReportDto> REPORTS_DTO)
    {
        return REPORTS_DTO.stream()
                          .map(this::mapToReport)
                          .collect(Collectors.toSet());
    }

    public Set<DailyEmployeeReportDto> mapToReportsDto(final Set<DailyEmployeeReport> REPORTS)
    {
        return REPORTS.stream()
                      .map(this::mapToReportDto)
                      .collect(Collectors.toSet());
    }
}
