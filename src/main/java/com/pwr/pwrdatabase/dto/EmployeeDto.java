package com.pwr.pwrdatabase.dto;

import java.time.LocalDate;
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
@EqualsAndHashCode (exclude = {
        "id",
        "phoneNumber",
        "hireDate",
        //"currentHolidays",
        //"active",
        "employmentContractDtoID",
        "workStartFinishEventsID",
        "dailyEmployeeReportsID",
        "employeeAbsentsID",
        "departmentsID"})
public class EmployeeDto
{
    @Id
    @NotNull private Long id;

    @NotNull private String firstName;
    @NotNull private String secondName;
    @NotNull private String PESEL;

    @NotNull private String phoneNumber;
    private LocalDate hireDate;
    //@NotNull private int currentHolidays;
    //@NotNull private boolean active = true;

    // Foreign Keys

    private Long employmentContractDtoID;

    @NotNull private Set<Long> workStartFinishEventsID = new HashSet<>();

    @NotNull private Set<Long> dailyEmployeeReportsID = new HashSet<>();

    @NotNull private Set<Long> employeeAbsentsID = new HashSet<>();

    @NotNull private Set<Long> departmentsID = new HashSet<>();
}
