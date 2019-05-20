package com.pwr.pwrdatabase.dto.dao;


import com.pwr.pwrdatabase.dto.Employee;
import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface EmployeeDao extends CrudRepository<Employee, Long>
{

}

