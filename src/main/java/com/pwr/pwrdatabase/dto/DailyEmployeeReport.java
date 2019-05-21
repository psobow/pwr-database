package com.pwr.pwrdatabase.dto;

import java.time.LocalDate;
import java.time.LocalTime;
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
public class DailyEmployeeReport
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, unique = true)
    @NotNull private long id;

    @NotNull private LocalDate reportDate;
    @NotNull private LocalTime workTime;
    @NotNull private double earnedCash;
    @NotNull private boolean lateness;
    private LocalTime latenessTime;

    // Foreign Keys

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ID_EMPLOYEE")
    @NotNull private Employee employee;
}
