package com.pwr.pwrdatabase.dto.dao;

import com.pwr.pwrdatabase.dto.Department;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface DepartmentDao extends CrudRepository<Department, Long>
{
    @Override
    List<Department> findAll();
}
