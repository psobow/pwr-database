package com.pwr.pwrdatabase.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
public class WorkStartFinishEvent
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, unique = true)
    @NotNull private long id;

    @NotNull private LocalDateTime eventDateTime;
    @NotNull private boolean beginning;

    // Foreign Keys

    @ManyToOne()
    @JoinColumn(name = "ID_EMPLOYEE")
    @NotNull private Employee employee;
}
