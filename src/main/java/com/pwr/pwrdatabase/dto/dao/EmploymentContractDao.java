package com.pwr.pwrdatabase.dto.dao;

import com.pwr.pwrdatabase.dto.EmploymentContract;
import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface EmploymentContractDao extends CrudRepository<EmploymentContract, Long>
{

}
