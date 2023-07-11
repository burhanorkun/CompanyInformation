package com.orkun.service;

import com.orkun.dto.EmployeeAndDepartmentDTO;
import com.orkun.dto.EmployeeDTO;
import com.orkun.dto.EmployeeSearchCriteriaDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {

    EmployeeDTO createEmployee(Long departmentId, EmployeeDTO employeeDTO);

    Page<EmployeeDTO> getAllEmployeesUsingPagination(EmployeeSearchCriteriaDTO employeeSearchCriteriaDTO);

    List<EmployeeDTO> getEmployeesByDepartmentId(Long departmentId);

    EmployeeDTO getEmployeeById(Long departmentId, String employeeId);

    EmployeeDTO updateEmployeeById(Long departmentId, String employeeId, EmployeeDTO employeeDTO);

    void deleteEmployee(Long departmentId, String employeeId);

    EmployeeAndDepartmentDTO getEmployeeAndDepartmentByEmployeeEmail(String email);
}
