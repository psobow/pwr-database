package com.pwr.pwrdatabase.service;

import com.pwr.pwrdatabase.domain.DailyEmployeeReport;
import com.pwr.pwrdatabase.domain.dao.DailyEmployeeReportDao;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService
{
    @Autowired private DailyEmployeeReportDao repository;

    public Long count()
    {
        return repository.count();
    }

    public Set<DailyEmployeeReport> findAll()
    {
        return repository.findAll();
    }

    public Set<DailyEmployeeReport> findAll(final Set<Long> IDs)
    {
        return repository.findAll(IDs);
    }

    public DailyEmployeeReport findOne(final Long ID)
    {
        return repository.findOne(ID);
    }

    public DailyEmployeeReport save(final DailyEmployeeReport report)
    {
        if (report.getEmployee() == null)
        {
            throw new IllegalArgumentException("Invalid report. Can not persist Report without Employee.");
        }
        return repository.save(report);
    }
}
