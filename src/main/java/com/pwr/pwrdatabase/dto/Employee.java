package com.pwr.pwrdatabase.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Employee
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, unique = true)
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

    @OneToMany(
            targetEntity = WorkStartFinishEvent.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "employee"
    )
    @NotNull private List<WorkStartFinishEvent> WorkStartFinishEvents = new ArrayList<>();
}
