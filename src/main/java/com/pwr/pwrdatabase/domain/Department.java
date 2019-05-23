package com.pwr.pwrdatabase.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Department
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, unique = true)
    @NotNull
    private long id;

    @NotNull private String city;
    @NotNull private String zipCode;
    @NotNull private String localNumber;

    // Foreign key
    @ManyToMany(targetEntity = Employee.class,
                mappedBy = "departments")
    @NotNull List<Employee> employees = new ArrayList<>();
}
