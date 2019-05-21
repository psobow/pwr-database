package com.pwr.pwrdatabase.dto.dao;

import com.pwr.pwrdatabase.dto.WorkStartFinishEvent;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface WorkStartFinishEventDao extends CrudRepository<WorkStartFinishEvent, Long>
{
    @Override
    List<WorkStartFinishEvent> findAll();
}