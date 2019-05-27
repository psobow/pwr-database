package com.pwr.pwrdatabase.dto;

import java.time.LocalTime;
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
public class EmploymentContractDto
{
    @Id
    @NotNull private long id;

    @NotNull private String employmentType;
    @NotNull private double hourPay;
    @NotNull private LocalTime shiftBegin;
    @NotNull private LocalTime shiftEnd;
    @NotNull private int quantityOfFullWorkDaysForOneHoliday;

    // Foreign Keys

    @NotNull private Set<Long> employeesID = new HashSet<>();

}

