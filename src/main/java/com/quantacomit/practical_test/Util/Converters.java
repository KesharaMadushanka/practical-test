package com.quantacomit.practical_test.Util;

import com.quantacomit.practical_test.DTO.EmployeeDTO;
import com.quantacomit.practical_test.models.Employee;

public class Converters {

    public static Employee employeeDTOtoEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setId(employeeDTO.getId());
        employee.setName(employeeDTO.getName());
        employee.setEmployeeCode(employeeDTO.getEmployeeCode());
        employee.setContactNo(employeeDTO.getContactNo());
        employee.setDateOfBirth(employeeDTO.getDateOfBirth());
        employee.setCurrentAgeInDays(employeeDTO.getCurrentAgeInDays());
        return employee;
    }

    public static EmployeeDTO employeeToEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDto = new EmployeeDTO();
        employeeDto.setId(employee.getId());
        employeeDto.setName(employee.getName());
        employeeDto.setEmployeeCode(employee.getEmployeeCode());
        employeeDto.setContactNo(employee.getContactNo());
        employeeDto.setDateOfBirth(employee.getDateOfBirth());
        employeeDto.setCurrentAgeInDays(employee.getCurrentAgeInDays());
        return employeeDto;
    }


}
