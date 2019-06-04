package com.pwr.pwrdatabase.dto;

import java.time.LocalDate;
import java.time.LocalTime;
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
public class DailyEmployeeReportDto
{
    @Id
    @NotNull private Long id;

    @NotNull private LocalDate reportDate;
    @NotNull private LocalTime workTime;
    @NotNull private Double earnedCash;
    @NotNull private Boolean lateness;
    private Integer latenessMinutes;

    // Foreign Keys

    @NotNull private Long employeeDtoID;
}
