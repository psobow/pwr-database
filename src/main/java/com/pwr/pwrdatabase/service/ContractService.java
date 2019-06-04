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
    private final double MINIMAL_HOUR_PAY = 10.0;

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

        checkIfAndThrowException(CONTRACT == null, "Contract is null.");

        Set<EmploymentContract> contractSet = repository.findAll();
        for (EmploymentContract e : contractSet)
        {
            checkIfAndThrowException(e.equals(CONTRACT), "CONTRACT already exist in database.");
        }
        checkIfAndThrowException(CONTRACT.getHourPay() < MINIMAL_HOUR_PAY, "Invalid contract. Hour pay is to low.");

        return repository.save(CONTRACT);
    }

    public void delete(final EmploymentContract CONTRACT)
    {
        checkIfAndThrowException(CONTRACT == null, "Contract is null.");
        checkIfAndThrowException(repository.findOne(CONTRACT.getId()) == null, "Contract with ID: " + CONTRACT.getId() + " does not exist in database.");
        checkIfAndThrowException(CONTRACT.getEmployees().size() != 0, "Invalid contract. Can not delete contract with related employees.");
        repository.delete(CONTRACT.getId());
    }

    public void delete(final Long ID)
    {
        EmploymentContract contractFromDatabase = repository.findOne(ID);
        checkIfAndThrowException(contractFromDatabase == null, "Contract with ID: " + ID + " does not exist in database.");
        this.delete(contractFromDatabase);
    }

    private void checkIfAndThrowException(boolean b, String s)
    {
        if (b)
        {
            throw new IllegalArgumentException(s);
        }
    }
}
