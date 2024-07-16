package com.quantacomit.practical_test.service;

import com.quantacomit.practical_test.DTO.EmployeeDTO;
import com.quantacomit.practical_test.exception.CustomException;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface EmployeeService {
    ResponseEntity<?> addNewEmployee(EmployeeDTO employeeDTO) throws CustomException;

    ResponseEntity<?> getAllEmployees() throws CustomException;

    ResponseEntity<?> getEmployeeByEmployeeCode(String employeeCode) throws CustomException;

    ResponseEntity<?> updateEmployee(EmployeeDTO employeeDTO) throws CustomException;

    ResponseEntity<?> deleteEmployee(String employeeCode) throws CustomException;

    ResponseEntity<?> uploadProfilePicture(String employeeCode, MultipartFile file) throws CustomException, IOException;

    ResponseEntity<?> downloadProfilePicture(String employeeCode) throws CustomException, IOException;

    ResponseEntity<?> downloadEmployeeReport() throws CustomException, JRException;
}
