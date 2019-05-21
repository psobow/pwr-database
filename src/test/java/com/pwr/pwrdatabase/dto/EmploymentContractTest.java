package com.pwr.pwrdatabase.dto;

import static org.junit.Assert.*;

import com.pwr.pwrdatabase.dto.dao.EmployeeDao;
import com.pwr.pwrdatabase.dto.dao.EmploymentContractDao;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class EmploymentContractTest
{
    @Autowired private EmploymentContractDao employmentContractDao;

    @Test
    public void persistEmploymentContract()
    {
        // Given
        EmploymentContract contract = new EmploymentContract();
        contract.setEmploymentType("Employment Type");
        contract.setHourPay(10.5);
        contract.setShiftBegin(LocalTime.now());
        contract.setShiftEnd(LocalTime.now());
        contract.setQuantityOfFullWorkDaysForOneHoliday(10);

        // Save size of entities
        long sizeOfContractBefore = employmentContractDao.count();

        // When
        employmentContractDao.save(contract);

        // Clean up
        employmentContractDao.delete(contract.getId());

        // Then
        long sizeOfContractAfter = employmentContractDao.count();

        Assert.assertEquals(sizeOfContractBefore, sizeOfContractAfter);
    }
}