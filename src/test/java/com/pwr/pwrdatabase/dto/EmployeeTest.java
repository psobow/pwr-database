package com.pwr.pwrdatabase.dto;

import static org.junit.Assert.*;

import com.pwr.pwrdatabase.dto.dao.EmployeeDao;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeTest
{
    @Autowired
    private EmployeeDao employeeDao;

    @Test
    public void persistEmployee()
    {
        // Given
        EmploymentContract contract = new EmploymentContract("Typ",
                                                             10.5,
                                                             LocalTime.now(),
                                                             LocalTime.now(),
                                                             10,
                                                             true);

        Employee employee = new Employee("Patryk",
                                         "Tak",
                                         "123",
                                         "321",
                                         LocalDate.now(),
                                         0);

        employee.setEmploymentContract(contract);
        contract.getEmployees().add(employee);

        // When
        employeeDao.save(employee);

        // Then

        // Clean up
    }


}