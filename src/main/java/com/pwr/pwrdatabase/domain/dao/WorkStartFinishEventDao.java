package com.pwr.pwrdatabase.domain.dao;

import com.pwr.pwrdatabase.domain.WorkStartFinishEvent;
import java.util.Set;
import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface WorkStartFinishEventDao extends CrudRepository<WorkStartFinishEvent, Long>
{
    @Override
    Set<WorkStartFinishEvent> findAll();

    @Override
    Set<WorkStartFinishEvent> findAll(Iterable<Long> IDs);
}