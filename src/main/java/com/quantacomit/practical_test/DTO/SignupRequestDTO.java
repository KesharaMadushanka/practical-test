package com.quantacomit.practical_test.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class SignupRequestDTO {
    @NotBlank(message = "Username is mandatory")
    @Size(min = 3, max = 200)
    private String username;

    @NotBlank(message = "Email is mandatory")
    @Size(max = 50)
    @Email(message = "Invalid email format. Please provide a valid email address.")
    private String email;

    private Set<String> role;

    @NotBlank(message = "password is mandatory")
    @Size(min = 6, max = 40)
    private String password;

}
