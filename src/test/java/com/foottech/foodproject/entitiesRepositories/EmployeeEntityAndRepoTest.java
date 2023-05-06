package com.foottech.foodproject.entitiesRepositories;


import com.foottech.foodproject.entities.Employee;
import com.foottech.foodproject.repositories.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmployeeEntityAndRepoTest {

    private TestEntityManager entityManager;
    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeEntityAndRepoTest(TestEntityManager entityManager, EmployeeRepository employeeRepository) {
        this.entityManager = entityManager;
        this.employeeRepository = employeeRepository;
    }

    @Test
    void testId(){
        Employee employee = new Employee();
        assertNull(employee.getId());
        employee.setName("Burhan Orkun");
        employee.setEmail("burhanorkun@gmail.com");

        entityManager.persistAndFlush(employee);

        assertNotNull(employee.getId());
    }

    @Test
    void testName() {
        Employee employee = getTestEmployee();

        entityManager.persistAndFlush(employee);

        Employee foundEmployee = employeeRepository.findById(employee.getId()).orElse(null);
        assertNotNull(foundEmployee);
        assertEquals(employee.getName(), foundEmployee.getName());
    }

    @Test
    void testEmail() {
        Employee employee = getTestEmployee();

        entityManager.persistAndFlush(employee);

        Employee foundEmployee = employeeRepository.findById(employee.getId()).orElse(null);
        assertNotNull(foundEmployee);
        assertEquals(employee.getEmail(), foundEmployee.getEmail());
    }

    @AfterEach
    void tearDown() {
        entityManager.clear();
        employeeRepository.deleteAll();
    }

    private Employee getTestEmployee(){
        return Employee.builder()
                .name("Burhan Orkun")
                .email("burhanorkun@gmail.com")
                .build();
    }
}