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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "EMPLOYEE")
@NoArgsConstructor
@Getter
@Setter
public class Employee
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, unique = true)
    @NotNull private long id;

    @Column(length = 50)
    @NotNull private String firstName;

    @Column(length = 50)
    @NotNull private String secondName;

    @Column(length = 11)
    @NotNull private String PESEL;
    @NotNull private String phoneNumber;
    @NotNull private LocalDate hireDate;
    @NotNull private int currentHolidays;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ID_EMPLOYMENT_CONTRACT")
    @NotNull private EmploymentContract employmentContract;



    Employee(String firstName, String secondName, String PESEL, String phoneNumber, LocalDate hireDate,
             int currentHolidays)
    {
        this.firstName = firstName;
        this.secondName = secondName;
        this.PESEL = PESEL;
        this.phoneNumber = phoneNumber;
        this.hireDate = hireDate;
        this.currentHolidays = currentHolidays;
    }
}
