package com.pwr.pwrdatabase.domain.dao;

import com.pwr.pwrdatabase.domain.EmployeeAbsent;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Transactional
@Repository
public interface EmployeeAbsentDao extends CrudRepository<EmployeeAbsent, Long>
{
    @Override
    List<EmployeeAbsent> findAll();
}