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
@EqualsAndHashCode(exclude = {"id", "eventTime"})
public class WorkStartFinishEventDto
{
    @Id
    private long id;

    @NotNull private LocalDate eventDate;
    @NotNull private LocalTime eventTime;
    @NotNull private Boolean beginning;

    // Foreign Keys

    @NotNull private Long employeeDtoID;
}
