package com.orkun.service;

import com.orkun.dto.DepartmentDTO;
import com.orkun.dto.DepartmentSearchCriteriaDTO;
import org.springframework.data.domain.Page;

public interface DepartmentService {

    DepartmentDTO createDepartment(DepartmentDTO departmentDTO);

    Page<DepartmentDTO> getAllDepartmentsUsingPagination(DepartmentSearchCriteriaDTO departmentSearchCriteriaDTO);

    DepartmentDTO getDepartmentById(Long id);

    DepartmentDTO updateDepartment(DepartmentDTO departmentDTO, Long id);

    void deleteDepartment(Long id);
}
