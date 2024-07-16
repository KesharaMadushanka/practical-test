package com.quantacomit.practical_test.service;

import com.quantacomit.practical_test.DTO.LoginRequestDTO;
import com.quantacomit.practical_test.DTO.SignupRequestDTO;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> authenticateUser(LoginRequestDTO loginRequest);

    ResponseEntity<?> addNewUser(SignupRequestDTO signUpRequest);
}
