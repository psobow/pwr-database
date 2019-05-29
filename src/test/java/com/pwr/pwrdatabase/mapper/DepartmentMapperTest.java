package com.pwr.pwrdatabase.mapper;

import static org.junit.Assert.*;

import com.pwr.pwrdatabase.domain.Department;
import com.pwr.pwrdatabase.dto.DepartmentDto;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
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
public class DepartmentMapperTest
{
    @Autowired private DepartmentMapper departmentMapper;

    @Test
    public void mapperTest()
    {
        // Given
        Department department = new Department();
        department.setCity("Warszawa");
        department.setZipCode("50-200");
        department.setLocalNumber("49B");

        // When
        DepartmentDto mappedToDTO = departmentMapper.mapToDepartmentDto(department);
        Department mappedBack = departmentMapper.mapToDepartment(mappedToDTO);

        Set<DepartmentDto> departmentDtoSet = departmentMapper.mapToDepartmentsDto(
                new HashSet<Department>()
                {
                    {
                        add(department);
                    }
                });

        // Then
        Assert.assertEquals("DepartmentDto", departmentDtoSet.stream()
                                                             .findAny()
                                                             .orElseThrow( () -> new NoSuchElementException())
                                                             .getClass()
                                                             .getSimpleName());

        Assert.assertEquals("DepartmentDto", mappedToDTO.getClass().getSimpleName());
        Assert.assertEquals(department.getId(), mappedToDTO.getId());

        Assert.assertEquals("Department", mappedBack.getClass().getSimpleName());
        Assert.assertEquals(department.getId(), mappedBack.getId());
    }
}