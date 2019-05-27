package com.pwr.pwrdatabase.mapper;

import com.pwr.pwrdatabase.domain.DailyEmployeeReport;
import com.pwr.pwrdatabase.dto.DailyEmployeeReportDto;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportMapper
{
    @Autowired private EmployeeMapper employeeMapper;

    public DailyEmployeeReport mapToReport(final DailyEmployeeReportDto REPORT_DTO)
    {
        return new DailyEmployeeReport(REPORT_DTO.getId(),
                                       REPORT_DTO.getReportDate(),
                                       REPORT_DTO.getWorkTime(),
                                       REPORT_DTO.getEarnedCash(),
                                       REPORT_DTO.isLateness(),
                                       REPORT_DTO.getLatenessTime(),
                                       employeeMapper.mapToEmployee(REPORT_DTO.getEmployeeDto()));
    }

    public DailyEmployeeReportDto mapToReportDto(final DailyEmployeeReport REPORT)
    {
        return new DailyEmployeeReportDto(REPORT.getId(),
                                          REPORT.getReportDate(),
                                          REPORT.getWorkTime(),
                                          REPORT.getEarnedCash(),
                                          REPORT.isLateness(),
                                          REPORT.getLatenessTime(),
                                          employeeMapper.mapToEmployeeDto(REPORT.getEmployee()));
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
