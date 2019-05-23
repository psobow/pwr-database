package com.pwr.pwrdatabase.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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
    @NotNull private boolean active = true;

    // Foreign Keys

    @ManyToOne()
    @JoinColumn(name = "ID_EMPLOYMENT_CONTRACT")
    @NotNull private EmploymentContract employmentContract;

    @OneToMany(
            targetEntity = WorkStartFinishEvent.class,
            cascade = CascadeType.ALL,
            mappedBy = "employee"
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    @NotNull private List<WorkStartFinishEvent> workStartFinishEvents = new ArrayList<>();

    @OneToMany(
            targetEntity = DailyEmployeeReport.class,
            cascade = CascadeType.ALL,
            mappedBy = "employee"
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    @NotNull private List<DailyEmployeeReport> dailyEmployeeReports = new ArrayList<>();

    @OneToMany(
            targetEntity = EmployeeAbsent.class,
            cascade = CascadeType.ALL,
            mappedBy = "employee"
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    @NotNull private List<EmployeeAbsent> employeeAbsents = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "JOINT_EMPLOYEE_DEPARTMENT",
            joinColumns = {@JoinColumn (name = "EMPLOYEE_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn (name = "DEPARTMENT_ID", referencedColumnName = "ID")}
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    @NotNull private List<Department> departments = new ArrayList<>();
}
