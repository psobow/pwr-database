package com.pwr.pwrdatabase.dto;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode (exclude = {"id", "employeesID"})
public class DepartmentDto
{
    @Id
    @NotNull private Long id;

    @NotNull private String city;
    @NotNull private String zipCode;
    @NotNull private String localNumber;

    // Foreign Keys

    @NotNull Set<Long> employeesID = new HashSet<>();
}
