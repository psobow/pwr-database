package com.pwr.pwrdatabase.domain.dao;

import com.pwr.pwrdatabase.domain.DailyEmployeeReport;
import java.util.Set;
import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface DailyEmployeeReportDao extends CrudRepository<DailyEmployeeReport, Long>
{
    @Override
    Set<DailyEmployeeReport> findAll();

    @Override
    Set<DailyEmployeeReport> findAll(Iterable<Long> IDs);
}
