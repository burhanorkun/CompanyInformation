package com.orkun.mapper;

import com.orkun.dto.DepartmentDTO;
import com.orkun.dto.DepartmentReportDTO;
import com.orkun.dto.DepartmentRequestDTO;
import com.orkun.entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {EmployeeMapper.class})
public interface DepartmentMapper {

    Department departmentDtoToDepartment(DepartmentDTO departmentDTO);
    DepartmentDTO departmentToDepartmentDto(Department department);

    List<Department> departmentDtoToDepartment(List<DepartmentDTO> departmentDTOList);
    List<DepartmentDTO> departmentToDepartmentDto(List<Department> departmentList);

    DepartmentDTO departmentRequestDTOToDepartmentDTO(DepartmentRequestDTO departmentRequestDTO);

    @Mapping(target = "totalEmployees", expression = "java(department.getEmployees() != null ? department.getEmployees().size() : 0)")
    DepartmentReportDTO departmentToDepartmentReportDto(Department department);

    @Mapping(target = "totalEmployees", expression = "java(departmentList.getEmployees() != null ? departmentList.getEmployees().size() : 0)")
    List<DepartmentReportDTO> departmentToDepartmentReportDto(List<Department> departmentList);
}
