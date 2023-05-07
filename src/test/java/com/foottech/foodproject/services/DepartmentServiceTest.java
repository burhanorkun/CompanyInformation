package com.foottech.foodproject.services;

import com.foottech.foodproject.entities.Department;
import com.foottech.foodproject.entities.Employee;
import com.foottech.foodproject.repositories.DepartmentRepository;
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
public class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    @Test
    void getAllDepartments() {
        List<Department> list = new ArrayList<>();
        Department deptOne = Department.builder().id(1L).name("CRM").description("CRM").build();
        Department deptTwo = Department.builder().id(2L).name("IT").description("IT").build();
        Department deptThree = Department.builder().id(3L).name("HR").description("HR").build();

        list.add(deptOne);
        list.add(deptTwo);
        list.add(deptThree);

        when(departmentRepository.findAll()).thenReturn(list);

        List<Department> departments = departmentService.getAllDepartments();

        assertNotNull(departments);
        assertFalse(departments.isEmpty());
        assertEquals(3, departments.size());
    }

    @Test
    void getDepartmentById() {
        Department deptOne = Department.builder().id(123L).name("CRM").description("CRM").build();
        when(departmentRepository.findById(123L)).thenReturn(Optional.of(deptOne));

        Department department = departmentService.getDepartmentById(123L);

        assertNotNull(department);
        assertEquals("CRM", department.getName());
        assertEquals("CRM", department.getDescription());
        assertEquals(123L, department.getId());
    }

    @Test
    void createDepartment() {
        Department deptOne = Department.builder().id(123L).name("CRM").description("CRM").build();
        when(departmentRepository.save(deptOne)).thenReturn(deptOne);

        Department savedDepartment = departmentService.createDepartment(deptOne);

        assertNotNull(savedDepartment);
        assertNotNull(savedDepartment.getId());
        assertEquals("CRM", savedDepartment.getName());
        assertEquals("CRM", savedDepartment.getDescription());
    }

    @Test
    public void testGetDepartmentByIdNotFound() {
        when(departmentRepository.findById(99999L)).thenThrow(new EntityNotFoundException("Department not found"));
        assertThrows(EntityNotFoundException.class, ()->departmentService.getDepartmentById(99999L));
    }

    @Test
    void updateDepartment() {
        Department deptOne = Department.builder().id(123L).name("CRM").description("CRM").build();
        when(departmentRepository.findById(123L)).thenReturn(Optional.ofNullable(deptOne));
        when(departmentRepository.save(deptOne)).thenReturn(deptOne);

        Department updatedDepartment = departmentService.updateDepartment(123L, deptOne);

        assertNotNull(updatedDepartment);
        assertEquals("CRM", updatedDepartment.getName());
    }

    @Test
    void deleteDepartment() {
        // When
        departmentService.deleteDepartment(9999L);
        // Then
        verify(this.departmentRepository).deleteById(9999L);
    }
}
