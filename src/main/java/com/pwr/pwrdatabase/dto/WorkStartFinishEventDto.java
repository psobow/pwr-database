package com.pwr.pwrdatabase.dto;

import java.time.LocalDateTime;
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
public class WorkStartFinishEventDto
{
    @Id
    @NotNull private long id;

    @NotNull private LocalDateTime eventDateTime;
    @NotNull private boolean beginning;

    // Foreign Keys
// tutaj zamiast referencji do obiektu wystarczy jego ID
    @NotNull private EmployeeDto employeeDto;
}
