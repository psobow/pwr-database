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

    public Long count()
    {
        return repository.count();
    }

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

    public WorkStartFinishEvent save(final WorkStartFinishEvent EVENT)
    {
        if (EVENT == null)
        {
            throw new IllegalArgumentException("Event is null.");
        }
        if (EVENT.getEmployee() == null)
        {
            throw new IllegalArgumentException("Invalid event. Can not persist event without Employee.");
        }
        return repository.save(EVENT);
    }

    public void delete(final WorkStartFinishEvent EVENT)
    {
        if (EVENT == null)
        {
            throw new IllegalArgumentException("Event is null.");
        }
        if(repository.findOne(EVENT.getId()) == null)
        {
            throw new IllegalArgumentException("Event with ID: " + EVENT.getId() + " does not exist in database.");
        }

        // Break relation Employee - Event
        EVENT.getEmployee().getWorkStartFinishEvents().remove(EVENT);

        repository.delete(EVENT.getId());
    }

    public void delete(final Long ID)
    {
        WorkStartFinishEvent event = repository.findOne(ID);
        delete(event);
    }
}
