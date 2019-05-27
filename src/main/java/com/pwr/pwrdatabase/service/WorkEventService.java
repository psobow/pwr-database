package com.pwr.pwrdatabase.service;

import com.pwr.pwrdatabase.domain.WorkStartFinishEvent;
import com.pwr.pwrdatabase.domain.dao.WorkStartFinishEventDao;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkEventService
{
    @Autowired private WorkStartFinishEventDao repository;

    public Set<WorkStartFinishEvent> findAll()
    {
        return repository.findAll();
    }

    public Set<WorkStartFinishEvent> findAll(final Set<Long> IDs)
    {
        return repository.findAll(IDs);
    }

    public WorkStartFinishEvent findOne(final Long ID)
    {
        return repository.findOne(ID);
    }
}
