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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "EMPLOYMENT_CONTRACT")
@NoArgsConstructor
@Getter
@Setter
public class EmploymentContract
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", unique = true)
    @NotNull private long id;

    @NotNull private String employmentType;
    @NotNull private double hourPay;
    @NotNull private LocalTime shiftBegin;
    @NotNull private LocalTime shiftEnd;
    @NotNull private int quantityOfFullWorkDaysForOneHoliday;
    @NotNull private boolean isActive;

    @OneToMany(
            targetEntity = Employee.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "employmentContract"
    )
    @NotNull private List<Employee> employees = new ArrayList<>();



    EmploymentContract(String employmentType, double hourPay, LocalTime shiftBegin, LocalTime shiftEnd,
                       int quantityOfFullWorkDaysForOneHoliday, boolean isActive)
    {
        this.employmentType = employmentType;
        this.hourPay = hourPay;
        this.shiftBegin = shiftBegin;
        this.shiftEnd = shiftEnd;
        this.quantityOfFullWorkDaysForOneHoliday = quantityOfFullWorkDaysForOneHoliday;
        this.isActive = isActive;
    }
}

