package com.pwr.pwrdatabase.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
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
        "employmentContract",
        "workStartFinishEvents",
        "dailyEmployeeReports",
        "employeeAbsents",
        "departments"})
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
    //@NotNull private int currentHolidays;
    //@NotNull private boolean active = true;

    // Foreign Keys

    // Employee is owner of this relation
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "ID_EMPLOYMENT_CONTRACT")
    private EmploymentContract employmentContract;

    @OneToMany(
            targetEntity = WorkStartFinishEvent.class,
            cascade = CascadeType.ALL,
            mappedBy = "employee"
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    @NotNull private Set<WorkStartFinishEvent> workStartFinishEvents = new HashSet<>();

    @OneToMany(
            targetEntity = DailyEmployeeReport.class,
            cascade = CascadeType.ALL,
            mappedBy = "employee"
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    @NotNull private Set<DailyEmployeeReport> dailyEmployeeReports = new HashSet<>();

    @OneToMany(
            targetEntity = EmployeeAbsent.class,
            cascade = CascadeType.ALL,
            mappedBy = "employee"
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    @NotNull private Set<EmployeeAbsent> employeeAbsents = new HashSet<>();

    // Employee is owner of this relation
    @ManyToMany(cascade = CascadeType.REFRESH)
    @JoinTable(
            name = "JOIN_EMPLOYEE_DEPARTMENT",
            joinColumns = {@JoinColumn (name = "EMPLOYEE_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn (name = "DEPARTMENT_ID", referencedColumnName = "ID")}
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    @NotNull private Set<Department> departments = new HashSet<>();

}
