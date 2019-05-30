package com.pwr.pwrdatabase.service;

import com.pwr.pwrdatabase.domain.EmploymentContract;
import com.pwr.pwrdatabase.domain.dao.EmploymentContractDao;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContractService
{
    @Autowired private EmploymentContractDao repository;

    public Long count()
    {
        return repository.count();
    }

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

    public EmploymentContract save(final EmploymentContract CONTRACT)
    {
        return repository.save(CONTRACT);
    }

    public void delete(final EmploymentContract CONTRACT)
    {
        if (CONTRACT.getEmployees().size() != 0)
        {
            throw new IllegalArgumentException("Invalid contract. Can not delete contract with related employees.");
        }
        repository.delete(CONTRACT.getId());
    }

    public void delete(final Long ID)
    {
        EmploymentContract contractFromDatabase = this.repository.findOne(ID);
        this.delete(contractFromDatabase);
    }
}
