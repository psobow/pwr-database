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
    private final int MINIMAL_QUANTITY_OF_FULL_WORK_DAYS_FOR_ONE_HOLIDAY = 10;

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
        if (CONTRACT == null)
        {
            throw new IllegalArgumentException("Contract is null.");
        }
        if(CONTRACT.getHourPay() < MINIMAL_HOUR_PAY)
        {
            throw new IllegalArgumentException("Invalid contract. Hour pay is to low.");
        }
        if(CONTRACT.getQuantityOfFullWorkDaysForOneHoliday() < MINIMAL_QUANTITY_OF_FULL_WORK_DAYS_FOR_ONE_HOLIDAY )
        {
            throw new IllegalArgumentException("Invalid contract. Quantity of full work days for one holiday is to low.");
        }
        return repository.save(CONTRACT);
    }

    public void delete(final EmploymentContract CONTRACT)
    {
        if (CONTRACT == null)
        {
            throw new IllegalArgumentException("Contract is null.");
        }

        if (CONTRACT.getEmployees().size() != 0)
        {
            throw new IllegalArgumentException("Invalid contract. Can not delete contract with related employees.");
        }
        repository.delete(CONTRACT.getId());
    }

    public void delete(final Long ID)
    {
        EmploymentContract contractFromDatabase = repository.findOne(ID);
        this.delete(contractFromDatabase);
    }
}
