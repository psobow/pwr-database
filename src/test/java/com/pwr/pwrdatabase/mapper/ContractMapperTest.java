package com.pwr.pwrdatabase.mapper;

import com.pwr.pwrdatabase.domain.EmploymentContract;
import com.pwr.pwrdatabase.dto.EmploymentContractDto;
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
public class ContractMapperTest
{
    @Autowired private ContractMapper contractMapper;

    @Test
    public void mapperTest()
    {
        // Given
        EmploymentContract contract = new EmploymentContract();
        contract.setEmploymentType("Employment Type");
        contract.setHourPay(10.5);
        contract.setShiftBegin(LocalTime.now());
        contract.setShiftEnd(LocalTime.now());
        contract.setQuantityOfFullWorkDaysForOneHoliday(10);

        // When
        EmploymentContractDto mappedToDTO = contractMapper.mapToContractDto(contract);
        EmploymentContract mappedBack = contractMapper.mapToContract(mappedToDTO);

        // Then
        Assert.assertEquals("EmploymentContractDto", mappedToDTO.getClass().getSimpleName());
        Assert.assertEquals(contract.getId(), mappedToDTO.getId());

        Assert.assertEquals("EmploymentContract", mappedBack.getClass().getSimpleName());
        Assert.assertEquals(contract.getId(), mappedBack.getId());
    }
}