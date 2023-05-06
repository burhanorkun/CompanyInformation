package com.foottech.foodproject.services;

import com.foottech.foodproject.entities.Department;

import java.util.List;

public interface CompanyInfoService {
    List<Department> getAllDepartments();
    Department getDepartmentById(Long id);
    Department createDepartment(Department department);
    Department updateDepartment(Long id, Department department);
    void deleteDepartment(Long id);

}
