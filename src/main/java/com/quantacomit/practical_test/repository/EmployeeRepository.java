package com.quantacomit.practical_test.repository;

import com.quantacomit.practical_test.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
  Optional<Employee> findTopByOrderByIdDesc();
  Optional<Employee> findByNameAndDateOfBirthAndContactNo(String name, LocalDate dateOfBirth, String contactNo);
  Optional<Employee> findByEmployeeCode(String employeeCode);
}