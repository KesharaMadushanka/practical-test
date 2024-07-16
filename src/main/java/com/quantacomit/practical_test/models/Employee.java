package com.quantacomit.practical_test.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    @Column(name = "employee_code")
    private String employeeCode;
    @Column(name = "contact_no")
    private String contactNo;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    @Column(name = "current_age_in_days")
    private int currentAgeInDays;

}
