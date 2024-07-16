package com.quantacomit.practical_test.service.scheduled;

import com.quantacomit.practical_test.Util.AgeCalculator;
import com.quantacomit.practical_test.models.Employee;
import com.quantacomit.practical_test.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AutoUpdateAge {

    private final EmployeeRepository employeeRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    public void updateCurrentAgeInDays() {
        List<Employee> employees = employeeRepository.findAll();

        List<Employee> updatedEmployees = employees.stream()
                .filter(employee -> employee.getDateOfBirth() != null)
                .peek(employee -> employee.setCurrentAgeInDays(AgeCalculator.calculateCurrentAgeInDays(employee.getDateOfBirth())))
                .collect(Collectors.toList());

        employeeRepository.saveAll(updatedEmployees);
    }
}
