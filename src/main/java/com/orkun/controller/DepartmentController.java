package com.orkun.controller;

import com.orkun.dto.APIResponse;
import com.orkun.dto.DepartmentDTO;
import com.orkun.dto.DepartmentRequestDTO;
import com.orkun.dto.DepartmentSearchCriteriaDTO;
import com.orkun.mapper.DepartmentMapper;
import com.orkun.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RequestMapping("/api/v2/departments")
@RestController
public class DepartmentController {

    private final DepartmentService departmentService;
    private final DepartmentMapper departmentMapper;
    private final String SUCCESS = "Success";

    @Operation(summary = "Add a new department")
    @PostMapping
    public ResponseEntity<APIResponse<DepartmentDTO>> createDepartment(
            @Valid @RequestBody DepartmentRequestDTO departmentRequestDTO) {

        DepartmentDTO departmentDTO = departmentMapper.departmentRequestDTOToDepartmentDTO(departmentRequestDTO);

        DepartmentDTO result = departmentService.createDepartment(departmentDTO);

        // Builder Design pattern
        APIResponse<DepartmentDTO> responseDTO = APIResponse
                .<DepartmentDTO>builder()
                .status(SUCCESS)
                .results(result)
                .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @Operation(summary = "Search all departments using pagination",
            description = "Returns a list of departments")
    @PostMapping("/search")
    public ResponseEntity<APIResponse<Page<DepartmentDTO>>> getAllDepartmentsUsingPagination(
            @Valid @RequestBody DepartmentSearchCriteriaDTO departmentSearchCriteriaDTO) {

        Page<DepartmentDTO> result = departmentService.getAllDepartmentsUsingPagination(departmentSearchCriteriaDTO);

        // Builder Design pattern
        APIResponse<Page<DepartmentDTO>> responseDTO = APIResponse
                .<Page<DepartmentDTO>>builder()
                .status(SUCCESS)
                .results(result)
                .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Operation(summary = "Find department by ID",
            description = "Returns a single department")
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<DepartmentDTO>> getDepartmentById(@PathVariable("id") Long id) {

        DepartmentDTO result = departmentService.getDepartmentById(id);

        // Builder Design pattern
        APIResponse<DepartmentDTO> responseDTO = APIResponse
                .<DepartmentDTO>builder()
                .status(SUCCESS)
                .results(result)
                .build();


        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


    @Operation(summary = "Update an existing department")
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<DepartmentDTO>> updateDepartment(
            @Valid @RequestBody DepartmentRequestDTO departmentRequestDTO,
            @PathVariable("id") Long id) {

        DepartmentDTO departmentDTO = departmentMapper.departmentRequestDTOToDepartmentDTO(departmentRequestDTO);

        DepartmentDTO result = departmentService.updateDepartment(departmentDTO, id);

        // Builder Design pattern
        APIResponse<DepartmentDTO> responseDTO = APIResponse
                .<DepartmentDTO>builder()
                .status(SUCCESS)
                .results(result)
                .build();


        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }


    @Operation(summary = "Delete a department by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<String>> deleteDepartment(@PathVariable("id") Long id) {

        departmentService.deleteDepartment(id);

        String result = "Department deleted successfully";

        // Builder Design pattern
        APIResponse<String> responseDTO = APIResponse
                .<String>builder()
                .status(SUCCESS)
                .results(result)
                .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}
