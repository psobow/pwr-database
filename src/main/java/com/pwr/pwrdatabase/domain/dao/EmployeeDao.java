package com.pwr.pwrdatabase.domain.dao;

import com.pwr.pwrdatabase.domain.Employee;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface EmployeeDao extends CrudRepository<Employee, Long>
{
    @Override
    List<Employee> findAll();
}

