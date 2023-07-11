package com.orkun.dto;

import lombok.*;

import java.util.Set;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDTO {

    private Long id;
    private String departmentCode;
    private String departmentName;
    private String departmentDescription;
    private Set<EmployeeDTO> employees;
}
