package com.foottech.foodproject.controllers;

import com.foottech.foodproject.entities.Department;
import com.foottech.foodproject.entities.Employee;
import com.foottech.foodproject.services.DepartmentService;
import com.foottech.foodproject.services.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyInfoControllerTest {

    @Mock
    private EmployeeService employeeService;

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private CompanyInfoController companyInfoController;

    private Employee employee;
    private Department department;

    @BeforeEach
    public void setUp() {
        department = new Department();
        department.setId(1L);
        department.setName("IT");
        department.setDescription("Information Technology");

        employee = new Employee();
        employee.setId(1L);
        employee.setName("Burhan Orkun");
        employee.setEmail("burhanorkun@gmail.com");
        employee.setDepartment(department);
    }

    @Test
    public void testCreateEmployee() {
        when(employeeService.createEmployee(any(Employee.class))).thenReturn(employee);

        Employee response = companyInfoController.createEmployee(employee);

        assertNotNull(response);
        assertEquals(employee, response);

        verify(employeeService, times(1)).createEmployee(any(Employee.class));
    }

    @Test
    public void testGetEmployeeById() {
        when(employeeService.getEmployeeById(1L)).thenReturn(employee);

        Employee response = companyInfoController.getEmployeeById(1L);

        assertNotNull(response);
        assertEquals(employee, response);

        verify(employeeService, times(1)).getEmployeeById(1L);
    }

    @Test
    public void testGetEmployeeByIdNotFound() {
        when(employeeService.getEmployeeById(1L)).thenReturn(null);

        Employee response = companyInfoController.getEmployeeById(1L);

        assertNull(response);

        verify(employeeService, times(1)).getEmployeeById(1L);
    }

    @Test
    public void testGetAllEmployees() {
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee);

        when(employeeService.getAllEmployees()).thenReturn(employeeList);

        List<Employee> response = companyInfoController.getAllEmployees();

        assertNotNull(response);
        assertEquals(employeeList, response);

        verify(employeeService, times(1)).getAllEmployees();
    }

    @Test
    public void testUpdateEmployee() {
        when(employeeService.updateEmployee(any(), any(Employee.class))).thenReturn(employee);

        Employee response = companyInfoController.updateEmployee(employee.getId(), employee);

        assertNotNull(response);
        assertEquals(employee, response);

        verify(employeeService, times(1)).updateEmployee(any(), any(Employee.class));
    }

    @Test
    public void testDeleteEmployee() {
        companyInfoController.deleteEmployee(1L);

        verify(employeeService, times(1)).deleteEmployee(1L);
    }

    @Test
    public void testCreateDepartment() {
        when(departmentService.createDepartment(any(Department.class))).thenReturn(department);

        Department response = companyInfoController.createDepartment(department);

        assertNotNull(response);
        assertEquals(department, response);

        verify(departmentService, times(1)).createDepartment(any(Department.class));
    }
}