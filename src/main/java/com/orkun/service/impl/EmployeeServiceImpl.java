package com.orkun.service.impl;

import com.orkun.dto.EmployeeAndDepartmentDTO;
import com.orkun.dto.EmployeeDTO;
import com.orkun.dto.EmployeeSearchCriteriaDTO;
import com.orkun.entity.Department;
import com.orkun.entity.Employee;
import com.orkun.exception.BusinessLogicException;
import com.orkun.exception.ResourceAlreadyExistException;
import com.orkun.exception.ResourceNotFoundException;
import com.orkun.mapper.EmployeeMapper;
import com.orkun.repository.DepartmentRepository;
import com.orkun.repository.EmployeeRepository;
import com.orkun.service.EmployeeService;
import com.orkun.utils.SortItem;
import com.orkun.utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeMapper employeeMapper;



    @Override
    public EmployeeDTO createEmployee(Long departmentId, EmployeeDTO employeeDTO) {

        Employee employeeRecordFromDB = employeeRepository.findByEmail(employeeDTO.getEmail());

        if (employeeRecordFromDB != null) {
            throw new ResourceAlreadyExistException("Employee", "email", employeeDTO.getEmail());
        }

        Employee employee = employeeMapper.employeeDtoToEmployee(employeeDTO);

        Department departmentRecordFromDB = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department", "id", departmentId));

        employee.setDepartment(departmentRecordFromDB);

        Employee savedEmployee = employeeRepository.save(employee);

        return employeeMapper.employeeToEmployeeDto(savedEmployee);
    }

    @Override
    public Page<EmployeeDTO> getAllEmployeesUsingPagination(EmployeeSearchCriteriaDTO employeeSearchCriteriaDTO) {

        Integer page = employeeSearchCriteriaDTO.getPage();
        Integer size = employeeSearchCriteriaDTO.getSize();
        List<SortItem> sortList = employeeSearchCriteriaDTO.getSortList();

        // this pageable will be used for the pagination.
        Pageable pageable = Utils.createPageableBasedOnPageAndSizeAndSorting(sortList, page, size);

        Page<Employee> recordsFromDb = employeeRepository.getAllEmployeesUsingPagination(employeeSearchCriteriaDTO, pageable);

        List<EmployeeDTO> result = employeeMapper.employeeToEmployeeDto(recordsFromDb.getContent());

        return new PageImpl<>(result, pageable, result.size());
    }


    @Override
    public List<EmployeeDTO> getEmployeesByDepartmentId(Long departmentId) {

        List<Employee> employeesFromDB = employeeRepository.findByDepartmentId(departmentId);

        return employeeMapper.employeeToEmployeeDto(employeesFromDB);
    }


    @Override
    public EmployeeDTO getEmployeeById(Long departmentId, String employeeId) {

        Department departmentRecordFromDB = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department", "id", departmentId));

        Employee employeeRecordFromDB = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeeId));

        if ( !employeeBelongsToDepartment(departmentRecordFromDB, employeeRecordFromDB)) {
            throw new BusinessLogicException("Employee does not belong to Department");
        }

        return employeeMapper.employeeToEmployeeDto(employeeRecordFromDB);
    }

    @Override
    public EmployeeDTO updateEmployeeById(Long departmentId, String employeeId, EmployeeDTO employeeDTO) {

        Department departmentRecordFromDB = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department", "id", departmentId));

        Employee employeeRecordFromDB = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeeId));

        if ( !employeeBelongsToDepartment(departmentRecordFromDB, employeeRecordFromDB)) {
            throw new BusinessLogicException("Employee does not belong to Department");
        }


        // just to be safe that the object does not have another id
        employeeDTO.setId(employeeId);

        Employee recordToBeSaved = employeeMapper.employeeDtoToEmployee(employeeDTO);

        // assign the post to the current comment
        recordToBeSaved.setDepartment(departmentRecordFromDB);

        Employee savedEmployeeRecord = employeeRepository.save(recordToBeSaved);

        return employeeMapper.employeeToEmployeeDto(savedEmployeeRecord);
    }

    @Override
    public void deleteEmployee(Long departmentId, String employeeId) {

        Department departmentRecordFromDB = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department", "id", departmentId));

        Employee employeeRecordFromDB = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeeId));

        if ( !employeeBelongsToDepartment(departmentRecordFromDB, employeeRecordFromDB)) {
            throw new BusinessLogicException("Employee does not belong to Department");
        }

        employeeRepository.delete(employeeRecordFromDB);

    }

    @Override
    public EmployeeAndDepartmentDTO getEmployeeAndDepartmentByEmployeeEmail(String email) {

        Employee employeeRecordFromDB = employeeRepository.getEmployeeAndDepartmentByEmployeeEmail(email);

        if (employeeRecordFromDB == null) {
            throw new ResourceNotFoundException("Employee", "email", email);
        }

        return employeeMapper.employeeToEmployeeAndDepartmentDto(employeeRecordFromDB);
    }


    private boolean employeeBelongsToDepartment(Department departmentRecordFromDB, Employee employeeRecordFromDB) {

        if (departmentRecordFromDB == null || employeeRecordFromDB.getDepartment() == null) {
            return false;
        }

        Long departmentId = employeeRecordFromDB.getDepartment().getId();
        return departmentId != null && departmentId.equals(departmentRecordFromDB.getId());
    }



}
