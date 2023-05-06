package com.foottech.foodproject.services;

import com.foottech.foodproject.entities.Department;
import com.foottech.foodproject.entities.Employee;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @Test
    public void testCreateEmployee() {
        Employee employee = new Employee();
        employee.setId(11L);
        employee.setName("Burhan Orkun");
        employee.setEmail("burhanorkun@gmail.com");
        Department department = Department.builder()
                .id(11L)
                .name("CRM")
                .description("Customer Relation Management")
                .build();
        employee.setDepartment(department);

        Employee savedEmployee = employeeService.createEmployee(employee);

        assertNotNull(savedEmployee);
        assertNotNull(savedEmployee.getId());
        assertEquals("Burhan Orkun", savedEmployee.getName());
        assertEquals("burhanorkun@gmail.com", savedEmployee.getEmail());
        assertEquals(11L, savedEmployee.getDepartment().getId().longValue());
    }

    @Test
    public void testGetAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();

        assertNotNull(employees);
        assertFalse(employees.isEmpty());
    }

    @Test
    public void testGetEmployeeById() {
        Employee employee = employeeService.getEmployeeById(1L);

        assertNotNull(employee);
        assertEquals("Burhan Orkun", employee.getName());
        assertEquals("burhanorkun@gmail.com", employee.getEmail());
        assertEquals(1L, employee.getDepartment().getId().longValue());
    }

    @Test
    public void testGetEmployeeByIdNotFound() {
        assertThrows(EntityNotFoundException.class, ()->employeeService.getEmployeeById(100L));
    }

    @Test
    public void testUpdateEmployee() {
        Employee employee = employeeService.getEmployeeById(1L);
        employee.setName("Burhan Orkun");

        Employee updatedEmployee = employeeService.updateEmployee(1L, employee);

        assertNotNull(updatedEmployee);
        assertEquals("Burhan Orkun", updatedEmployee.getName());
    }

    @Test
    public void testDeleteEmployee() {
        employeeService.deleteEmployee(1L);

        try {
            employeeService.getEmployeeById(1L);
            fail("EntityNotFoundException not thrown");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

}