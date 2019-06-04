package com.pwr.pwrdatabase.domain.dao;

import com.pwr.pwrdatabase.domain.EmploymentContract;
import java.util.Set;
import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface EmploymentContractDao extends CrudRepository<EmploymentContract, Long>
{
    @Override
    Set<EmploymentContract> findAll();

    @Override
    Set<EmploymentContract> findAll(Iterable<Long> IDs);

}
