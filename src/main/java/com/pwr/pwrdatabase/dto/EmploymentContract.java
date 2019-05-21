package com.pwr.pwrdatabase.dto;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class EmploymentContract
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, unique = true)
    @NotNull private long id;

    @NotNull private String employmentType;
    @NotNull private double hourPay;
    @NotNull private LocalTime shiftBegin;
    @NotNull private LocalTime shiftEnd;
    @NotNull private int quantityOfFullWorkDaysForOneHoliday;
    @NotNull private boolean active;

    @OneToMany(
            targetEntity = Employee.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "employmentContract"
    )
    @NotNull private List<Employee> employees = new ArrayList<>();

}

