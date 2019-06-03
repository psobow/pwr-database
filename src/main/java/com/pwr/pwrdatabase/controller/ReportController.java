package com.pwr.pwrdatabase.controller;

import com.pwr.pwrdatabase.dto.DailyEmployeeReportDto;
import com.pwr.pwrdatabase.mapper.ReportMapper;
import com.pwr.pwrdatabase.service.ReportService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/reports")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ReportController
{
    private final ReportService SERVICE;
    private final ReportMapper MAPPER;

    @GetMapping
    public Set<DailyEmployeeReportDto> getReports()
    {
        return MAPPER.mapToReportsDto(SERVICE.findAll());
    }

    @GetMapping("/{id}")
    public DailyEmployeeReportDto getReport(@PathVariable("id") final Long ID)
    {
        return MAPPER.mapToReportDto(SERVICE.findOne(ID));
    }

    @DeleteMapping("/{id}")
    public void deleteReport(@PathVariable("id") final Long ID)
    {
        SERVICE.delete(ID);
    }

}
