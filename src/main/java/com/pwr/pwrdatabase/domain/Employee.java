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
        "currentHolidays",
        "active",
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
    @NotNull private int currentHolidays;
    @NotNull private boolean active = true;

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
    //@Getter(AccessLevel.NONE)
    //@Setter(AccessLevel.NONE)
    @NotNull private Set<WorkStartFinishEvent> workStartFinishEvents = new HashSet<>();

    @OneToMany(
            targetEntity = DailyEmployeeReport.class,
            cascade = CascadeType.ALL,
            mappedBy = "employee"
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    //@Getter(AccessLevel.NONE)
    //@Setter(AccessLevel.NONE)
    @NotNull private Set<DailyEmployeeReport> dailyEmployeeReports = new HashSet<>();

    @OneToMany(
            targetEntity = EmployeeAbsent.class,
            cascade = CascadeType.ALL,
            mappedBy = "employee"
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    //@Getter(AccessLevel.NONE)
    //@Setter(AccessLevel.NONE)
    @NotNull private Set<EmployeeAbsent> employeeAbsents = new HashSet<>();

    // Employee is owner of this relation
    @ManyToMany(cascade = CascadeType.REFRESH)
    @JoinTable(
            name = "JOIN_EMPLOYEE_DEPARTMENT",
            joinColumns = {@JoinColumn (name = "EMPLOYEE_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn (name = "DEPARTMENT_ID", referencedColumnName = "ID")}
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    //@Getter(AccessLevel.NONE)
    //@Setter(AccessLevel.NONE)
    @NotNull private Set<Department> departments = new HashSet<>();

    public boolean addWorkStartFinishEvent(@NotNull final WorkStartFinishEvent event)
    {
        return workStartFinishEvents.add(event);
    }

    public boolean removeWorkStartFinishEvent(@NotNull final WorkStartFinishEvent event)
    {
        return workStartFinishEvents.remove(event);
    }

    public boolean addDailyEmployeeReport(@NotNull final DailyEmployeeReport report)
    {
        return dailyEmployeeReports.add(report);
    }

    public boolean removeDailyEmployeeReport(@NotNull final DailyEmployeeReport report)
    {
        return dailyEmployeeReports.remove(report);
    }

    public boolean addEmployeeAbsent(@NotNull final EmployeeAbsent absent)
    {
        return employeeAbsents.add(absent);
    }

    public boolean removeEmployeeAbsent(@NotNull final EmployeeAbsent absent)
    {
        return employeeAbsents.remove(absent);
    }

    public boolean addDepartment(@NotNull final Department department)
    {
        return departments.add(department);
    }

    public boolean removeDepartment(@NotNull final Department department)
    {
        return departments.remove(department);
    }
}
