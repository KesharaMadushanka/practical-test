package com.quantacomit.practical_test.service.impl;

import com.quantacomit.practical_test.DTO.EmployeeDTO;
import com.quantacomit.practical_test.DTO.response.ResponseMessage;
import com.quantacomit.practical_test.Util.AgeCalculator;
import com.quantacomit.practical_test.Util.Converters;
import com.quantacomit.practical_test.Util.IdGenerator;
import com.quantacomit.practical_test.Util.ValidationUtil;
import com.quantacomit.practical_test.exception.CustomException;
import com.quantacomit.practical_test.models.Employee;
import com.quantacomit.practical_test.repository.EmployeeRepository;
import com.quantacomit.practical_test.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final IdGenerator idGenerator;
    private final ResourceLoader resourceLoader;

    @Override
    @Transactional
    public ResponseEntity<?> addNewEmployee(EmployeeDTO employeeDTO) throws CustomException {
        Optional<Employee> byNameAndDateOfBirthAndContactNo = employeeRepository.findByNameAndDateOfBirthAndContactNo(
                employeeDTO.getName(), employeeDTO.getDateOfBirth(), employeeDTO.getContactNo()
        );

        if (byNameAndDateOfBirthAndContactNo.isPresent()) {
            throw new CustomException("Duplicate Entry");
        }

        employeeDTO.setEmployeeCode(idGenerator.employeeCodeGenerator());
        employeeDTO.setCurrentAgeInDays(AgeCalculator.calculateCurrentAgeInDays(employeeDTO.getDateOfBirth()));

        Employee employee = Converters.employeeDTOtoEmployee(employeeDTO);
        Employee savedEmployee = employeeRepository.save(employee);

        return new ResponseEntity<>(new ResponseMessage(HttpStatus.OK.value(), "success", savedEmployee), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAllEmployees() throws CustomException {
        List<Employee> allEmployees = employeeRepository.findAll();
        if (allEmployees.isEmpty()) {
            throw new CustomException("No employees found");
        }

        List<EmployeeDTO> employeeDTOList = allEmployees.stream()
                .map(Converters::employeeToEmployeeDTO)
                .toList();

        return new ResponseEntity<>(new ResponseMessage(HttpStatus.OK.value(), "success", employeeDTOList), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getEmployeeByEmployeeCode(String employeeCode) throws CustomException {
        ValidationUtil.isNullOrEmptyException(employeeCode, "Employee Code");

        Optional<Employee> byEmployeeCode = employeeRepository.findByEmployeeCode(employeeCode);

        EmployeeDTO employeeDTO = byEmployeeCode
                .map(Converters::employeeToEmployeeDTO)
                .orElseThrow(() -> new CustomException("Employee with code " + employeeCode + " not found!"));

        return new ResponseEntity<>(new ResponseMessage(HttpStatus.OK.value(), "success", employeeDTO), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateEmployee(EmployeeDTO employeeDTO) throws CustomException {
        ValidationUtil.isNullOrEmptyException(employeeDTO.getEmployeeCode(), "Employee Code");

        final String employeeCode = employeeDTO.getEmployeeCode();

        Employee employee = employeeRepository.findByEmployeeCode(employeeCode)
                .orElseThrow(() -> new CustomException("Employee with code " + employeeCode + " not found"));

        EmployeeDTO empDto = Converters.employeeToEmployeeDTO(employee);

        if (!ValidationUtil.isNullOrEmpty(employeeDTO.getContactNo())) {
            empDto.setContactNo(employeeDTO.getContactNo());
        }
        if (!ValidationUtil.isNullOrEmpty(employeeDTO.getName())) {
            empDto.setName(employeeDTO.getName());
        }
        if (employeeDTO.getDateOfBirth() != null) {
            empDto.setDateOfBirth(employeeDTO.getDateOfBirth());
            empDto.setCurrentAgeInDays(AgeCalculator.calculateCurrentAgeInDays(employeeDTO.getDateOfBirth()));
        }

        validateEmployee(empDto);
        employee = Converters.employeeDTOtoEmployee(empDto);

        Employee saved = employeeRepository.save(employee);
        return new ResponseEntity<>(new ResponseMessage(HttpStatus.OK.value(), "successfully Updated!", saved), HttpStatus.OK);

    }

    private void validateEmployee(EmployeeDTO employeeDTO) throws CustomException {
        if (employeeDTO.getContactNo().length() < 10 || employeeDTO.getContactNo().length() > 15) {
            throw new CustomException("Contact number should be between 10 and 15 characters");
        } else if (!employeeDTO.getContactNo().matches("^[0-9]+$")) {
            throw new CustomException("Contact number should only contain digits");
        }
        if (!employeeDTO.getDateOfBirth().isBefore(LocalDate.now())) {
            throw new CustomException("Date of birth must be a past date");
        }
    }

    @Override
    public ResponseEntity<?> deleteEmployee(String employeeCode) throws CustomException {
        ValidationUtil.isNullOrEmptyException(employeeCode, "Employee Code");
        Optional<Employee> byEmployeeCode = employeeRepository.findByEmployeeCode(employeeCode);
        if (byEmployeeCode.isEmpty()) {
            throw new CustomException("Employee with code " + employeeCode + " not found!");
        }

        employeeRepository.delete(byEmployeeCode.get());
        return new ResponseEntity<>(new ResponseMessage(HttpStatus.OK.value(), employeeCode + " successfully Deleted!"), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<?> uploadProfilePicture(String employeeCode, MultipartFile file) throws CustomException, IOException {
        ValidationUtil.isNullOrEmptyException(employeeCode, "Employee Code");

        employeeRepository.findByEmployeeCode(employeeCode).orElseThrow(() -> new CustomException("Employee with code " + employeeCode + " not found!"));

        String fileExtension = getFileExtension(file);

        if (!fileExtension.matches("\\.(jpg|jpeg|png|gif|bmp|webp)")) {
            throw new CustomException("Invalid image file extension");
        }

        String fileName = "profile_" + employeeCode + fileExtension;
        Path path = Paths.get("images/profile_pictures/" + employeeCode + "/" + fileName);
        Files.createDirectories(path.getParent());
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        return new ResponseEntity<>(new ResponseMessage(HttpStatus.OK.value(), "successfully uploaded!"), HttpStatus.OK);

    }

    private static String getFileExtension(MultipartFile file) throws CustomException {
        if (file.isEmpty()) {
            throw new CustomException("File is empty");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new CustomException("Only image files are allowed");
        }

        String originalFilename = file.getOriginalFilename();
        String fileExtension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        return fileExtension;
    }

    @Override
    public ResponseEntity<?> downloadProfilePicture(String employeeCode) throws CustomException, IOException {
        ValidationUtil.isNullOrEmptyException(employeeCode, "Employee Code");
        employeeRepository.findByEmployeeCode(employeeCode).orElseThrow(() -> new CustomException("Employee with code " + employeeCode + " not found!"));

        Path directoryPath = Paths.get("images/profile_pictures/" + employeeCode);
        if (!Files.exists(directoryPath) || !Files.isDirectory(directoryPath)) {
            throw new CustomException("Image Folder Not Found");
        }

        Path filePath;
        try (Stream<Path> paths = Files.list(directoryPath)) {
            filePath = paths.filter(path -> !Files.isDirectory(path))
                    .findFirst()
                    .orElse(null);
        }

        if (filePath == null) {
            throw new CustomException("Image Not Found");
        }

        Resource resource = new UrlResource(filePath.toUri());
        if (!resource.exists() || !resource.isReadable()) {
            throw new CustomException("Image Not Found");
        }

        String contentType = Files.probeContentType(filePath);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filePath.getFileName().toString() + "\"")
                .body(resource);
    }

    @Override
    public ResponseEntity<?> downloadEmployeeReport() throws CustomException, JRException {
        List<Employee> employees = employeeRepository.findAll();
        if (employees.isEmpty()) {
            throw new CustomException("No Employees found!");
        }

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(employees);
        Map<String, Object> parameters = new HashMap<>();

        Resource resource = resourceLoader.getResource("classpath:reports/employee_list.jrxml");

        try (InputStream inputStream = resource.getInputStream()) {

            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            byte[] pdfContent = JasperExportManager.exportReportToPdf(jasperPrint);

            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=employee_report.pdf");
            headers.setContentType(org.springframework.http.MediaType.APPLICATION_PDF);

            return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
        } catch (JRException e) {
            e.printStackTrace(); // Print the stack trace for debugging
            throw new CustomException("Error compiling report: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(); // Print the stack trace for debugging
            throw new CustomException("Error generating report: " + e.getMessage());
        }
    }
}
