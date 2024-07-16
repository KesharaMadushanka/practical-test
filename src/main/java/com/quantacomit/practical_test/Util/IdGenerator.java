package com.quantacomit.practical_test.Util;

import com.quantacomit.practical_test.models.Employee;
import com.quantacomit.practical_test.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class IdGenerator {

    private final EmployeeRepository employeeRepository;

    public String employeeCodeGenerator(){
        Optional<Employee> employee = employeeRepository.findTopByOrderByIdDesc();
        return employee.map(emp -> {
            String employeeCode = emp.getEmployeeCode();
            String[] parts = employeeCode.split("(?<=\\D)(?=\\d)");
            String numbers = parts[1];
            int numericPart = Integer.parseInt(numbers) + 1;
            return "EMP" + String.format("%04d", numericPart);
        }).orElse("EMP"+"0001");
    }
}
