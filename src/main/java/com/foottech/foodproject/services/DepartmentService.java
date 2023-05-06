package com.foottech.foodproject.services;

import com.foottech.foodproject.entities.Department;
import com.foottech.foodproject.repositories.DepartmentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Department not found"));
    }

    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public Department updateDepartment(Long id, Department department) {
        Department existingDepartment = getDepartmentById(id);
        existingDepartment.setName(department.getName());
        existingDepartment.setDescription(department.getDescription());
        // todo: write employees
        return departmentRepository.save(existingDepartment);
    }

    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }
}
