package com.quantacomit.practical_test.controllers;

import com.quantacomit.practical_test.DTO.LoginRequestDTO;
import com.quantacomit.practical_test.DTO.SignupRequestDTO;
import com.quantacomit.practical_test.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/practical/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequest) {
        return authService.authenticateUser(loginRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> addNewUser(@Valid @RequestBody SignupRequestDTO signUpRequest) {
        return authService.addNewUser(signUpRequest);
    }

}
