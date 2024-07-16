package com.quantacomit.practical_test.controllers;

import com.quantacomit.practical_test.DTO.EmployeeDTO;
import com.quantacomit.practical_test.exception.CustomException;
import com.quantacomit.practical_test.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/practical/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/addNewEmployee")
    public ResponseEntity<?> addNewEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) throws CustomException {
        try {
            return employeeService.addNewEmployee(employeeDTO);
        } catch (CustomException c) {
            throw new CustomException(c.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/getAllEmployees")
    public ResponseEntity<?> getAllEmployees() throws CustomException {
        try {
            return employeeService.getAllEmployees();
        } catch (CustomException c) {
            throw new CustomException(c.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/getEmployeeByEmployeeCode/{employeeCode}")
    public ResponseEntity<?> getEmployeeByEmployeeCode(@PathVariable String employeeCode) throws CustomException {
        try {
            return employeeService.getEmployeeByEmployeeCode(employeeCode);
        } catch (CustomException c) {
            throw new CustomException(c.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PostMapping("/updateEmployee")
    public ResponseEntity<?> updateEmployee(@RequestBody EmployeeDTO employeeDTO) throws CustomException {
        try {
            return employeeService.updateEmployee(employeeDTO);
        } catch (CustomException c) {
            throw new CustomException(c.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PostMapping("/deleteEmployee/{employeeCode}")
    @Secured("ADMIN")
    public ResponseEntity<?> deleteEmployee(@PathVariable String employeeCode) throws CustomException {
        try {
            return employeeService.deleteEmployee(employeeCode);
        } catch (CustomException c) {
            throw new CustomException(c.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PostMapping("/{employeeCode}/uploadProfilePicture")
    public ResponseEntity<?> uploadProfilePicture(@PathVariable String employeeCode, @RequestParam("file") MultipartFile file) throws CustomException {
        try {
            return employeeService.uploadProfilePicture(employeeCode, file);
        } catch (CustomException c) {
            throw new CustomException(c.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/downloadProfilePicture/{employeeCode}")
    public ResponseEntity<?> downloadProfilePicture(@PathVariable String employeeCode) throws CustomException {
        try {
            return employeeService.downloadProfilePicture(employeeCode);
        } catch (CustomException c) {
            throw new CustomException(c.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/employeeReport")
    public ResponseEntity<?> downloadEmployeeReport() throws CustomException {
        try {
            return employeeService.downloadEmployeeReport();
        } catch (CustomException c) {
            throw new CustomException(c.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}


