package com.foottech.foodproject.entitiesRepositories;

import com.foottech.foodproject.entities.Department;
import com.foottech.foodproject.entities.Employee;
import com.foottech.foodproject.repositories.DepartmentRepository;
import com.foottech.foodproject.repositories.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DepartmentEntityAndRepoTest {

    private TestEntityManager entityManager;
    private DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentEntityAndRepoTest(TestEntityManager entityManager, DepartmentRepository departmentRepository) {
        this.entityManager = entityManager;
        this.departmentRepository = departmentRepository;
    }

    @Test
    void testId(){
        Department department = new Department();
        assertNull(department.getId());
        department.setName("CRM");
        department.setDescription("Customer Relation Management");

        entityManager.persistAndFlush(department);

        assertNotNull(department.getId());
    }

    @Test
    void testName() {
        Department department = getTestDepartment();

        entityManager.persistAndFlush(department);

        Department foundDepartment = departmentRepository.findById(department.getId()).orElse(null);
        assertNotNull(foundDepartment);
        assertEquals(department.getName(), foundDepartment.getName());
    }

    @Test
    void testDescription() {
        Department department = getTestDepartment();

        entityManager.persistAndFlush(department);

        Department foundDepartment = departmentRepository.findById(department.getId()).orElse(null);
        assertNotNull(foundDepartment);
        assertEquals(department.getDescription(), foundDepartment.getDescription());
    }

    @AfterEach
    void tearDown() {
        entityManager.clear();
        departmentRepository.deleteAll();
    }

    private Department getTestDepartment(){
        return Department.builder()
                .name("CRM")
                .description("Customer Relation Management")
                .build();
    }
}
