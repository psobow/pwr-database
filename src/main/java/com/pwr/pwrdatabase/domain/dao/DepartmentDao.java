package com.pwr.pwrdatabase.domain.dao;

import com.pwr.pwrdatabase.domain.Department;
import java.util.Set;
import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface DepartmentDao extends CrudRepository<Department, Long>
{
    @Override
    Set<Department> findAll();

    @Override
    Set<Department> findAll(Iterable<Long> IDs);
}
