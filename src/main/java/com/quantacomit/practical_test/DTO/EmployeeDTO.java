package com.quantacomit.practical_test.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
public class EmployeeDTO {
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    private String employeeCode;

    @NotBlank(message = "Contact number is mandatory")
    @Size(min = 10, max = 15, message = "Contact number should be between 10 and 15 characters")
    @Pattern(regexp = "^[0-9]+$", message = "Contact number should only contain digits")
    private String contactNo;

    @NotNull(message = "Date of birth is mandatory")
    @Past(message = "Date of birth must be a past date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    private int currentAgeInDays;
}
