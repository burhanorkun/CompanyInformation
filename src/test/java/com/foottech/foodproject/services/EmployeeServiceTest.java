package com.foottech.foodproject.services;

import com.foottech.foodproject.entities.Department;
import com.foottech.foodproject.entities.Employee;
import com.foottech.foodproject.repositories.DepartmentRepository;
import com.foottech.foodproject.repositories.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    public void testCreateEmployee() {

        Employee employee = new Employee();
        employee.setId(1234L);
        employee.setName("Burhan Orkun");
        employee.setEmail("burhanorkun@gmail.com");
        Department department = Department.builder()
                .id(4567L)
                .name("CRM")
                .description("Customer Relation Management")
                .build();
        employee.setDepartment(department);

        when(departmentRepository.findById(4567L)).thenReturn(Optional.ofNullable(department));
        when(employeeRepository.save(employee)).thenReturn(employee);

        Employee savedEmployee = employeeService.createEmployee(employee);

        assertNotNull(savedEmployee);
        assertNotNull(savedEmployee.getId());
        assertEquals("Burhan Orkun", savedEmployee.getName());
        assertEquals("burhanorkun@gmail.com", savedEmployee.getEmail());
        assertEquals(4567L, savedEmployee.getDepartment().getId().longValue());
    }

    @Test
    public void testGetAllEmployees() {
        List<Employee> list = new ArrayList<>();
        Employee empOne = new Employee(1L, "burhan", "burhan", null);
        Employee empTwo = new Employee(2L, "orhan", "orhan", null);
        Employee empThree = new Employee(3L, "erkan", "erkan", null);

        list.add(empOne);
        list.add(empTwo);
        list.add(empThree);

        when(employeeRepository.findAll()).thenReturn(list);


        List<Employee> employees = employeeService.getAllEmployees();

        assertNotNull(employees);
        assertFalse(employees.isEmpty());
        assertEquals(3, employees.size());
    }

    @Test
    public void testGetEmployeeById() {
        Employee empOne = Employee.builder()
                .id(123L)
                .name("Burhan Orkun")
                .email("burhanorkun@gmail.com").build();
        when(employeeRepository.findById(123L)).thenReturn(Optional.of(empOne));

        Employee employee = employeeService.getEmployeeById(123L);

        assertNotNull(employee);
        assertEquals("Burhan Orkun", employee.getName());
        assertEquals("burhanorkun@gmail.com", employee.getEmail());
        assertEquals(123L, employee.getId());
    }

    @Test
    public void testGetEmployeeByIdNotFound() {
        when(employeeRepository.findById(99999L)).thenThrow(new EntityNotFoundException("Department not found"));
        assertThrows(EntityNotFoundException.class, ()->employeeService.getEmployeeById(99999L));
    }

    @Test
    public void testUpdateEmployee() {
        Department department = Department.builder()
                .id(4567L)
                .name("CRM")
                .description("Customer Relation Management")
                .build();
        Employee employee = Employee.builder()
                .id(123L)
                .name("Burhan Orkun")
                .email("burhanorkun@gmail.com")
                .department(department).build();

        when(departmentRepository.findById(4567L)).thenReturn(Optional.ofNullable(department));
        when(employeeRepository.findById(123L)).thenReturn(Optional.ofNullable(employee));
        when(employeeRepository.save(employee)).thenReturn(employee);

        Employee updatedEmployee = employeeService.updateEmployee(123L, employee);

        assertNotNull(updatedEmployee);
        assertEquals("Burhan Orkun", updatedEmployee.getName());
    }

    @Test
    public void testDeleteEmployee() {
        // When
        employeeService.deleteEmployee(9999L);
        // Then
        verify(this.employeeRepository).deleteById(9999L);

        /*
        try {
            employeeService.getEmployeeById(1L);
            fail("EntityNotFoundException not thrown");
        } catch (EntityNotFoundException e) {
            // expected
        }  */
    }

}