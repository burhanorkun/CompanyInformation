package com.foottech.foodproject.services;

import com.foottech.foodproject.entities.Department;
import com.foottech.foodproject.entities.Employee;
import com.foottech.foodproject.repositories.DepartmentRepository;
import com.foottech.foodproject.repositories.EmployerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class EmployeeService {

    private final EmployerRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Employee not found"));
    }

    public Employee createEmployee(Employee employee) {
        Department department = departmentRepository.findById(employee.getDepartment().getId())
                .orElseThrow(()->new EntityNotFoundException("Department not found"));
        employee.setDepartment(department);
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, Employee employee) {
        Employee existingEmployee = getEmployeeById(id);
        existingEmployee.setName(employee.getName());
        existingEmployee.setEmail(employee.getEmail());
        Department existingDepartment = departmentRepository.findById(employee.getDepartment().getId())
                .orElseThrow(() -> new EntityNotFoundException("Department not found"));
        existingEmployee.setDepartment(existingDepartment);
        return employeeRepository.save(existingEmployee);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
