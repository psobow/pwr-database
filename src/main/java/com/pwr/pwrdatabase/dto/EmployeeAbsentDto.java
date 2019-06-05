package com.pwr.pwrdatabase.dto;

import java.time.LocalDate;
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
@EqualsAndHashCode (exclude = "id")
public class EmployeeAbsentDto
{
    @Id
    private long id;

    @NotNull private String typeOfAbsent;
    @NotNull private LocalDate absentStartDate;
    @NotNull private Integer absentDurationInDays;

    // Foreign Keys

    @NotNull private Long employeeDtoID;
}
