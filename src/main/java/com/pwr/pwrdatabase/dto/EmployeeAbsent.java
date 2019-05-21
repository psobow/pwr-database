package com.pwr.pwrdatabase.dto;

import java.time.LocalDate;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class EmployeeAbsent
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, unique = true)
    @NotNull private long id;

    @NotNull private String typeOfAbsent;
    @NotNull private LocalDate absentStartDate;
    @NotNull private int absentDurationInDays;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ID_EMPLOYEE")
    @NotNull private Employee employee;
}
