package com.pwr.pwrdatabase.service;

import com.pwr.pwrdatabase.domain.EmploymentContract;
import com.pwr.pwrdatabase.domain.dao.EmploymentContractDao;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContractService
{
    @Autowired EmploymentContractDao repository;

    public Set<EmploymentContract> findAll()
    {
        return repository.findAll();
    }

    public Set<EmploymentContract> findAll(final Set<Long> IDs)
    {
        return repository.findAll(IDs);
    }

    public EmploymentContract findOne(final Long ID)
    {
        return repository.findOne(ID);
    }
}
