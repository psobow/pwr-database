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
    @NotNull private long id;

    @NotNull private LocalDate reportDate;
    @NotNull private LocalTime workTime;
    @NotNull private double earnedCash;
    @NotNull private boolean lateness;
    private LocalTime latenessTime;

    // Foreign Keys

    @NotNull private Long employeeDtoID;
}
