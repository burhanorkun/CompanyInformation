package com.orkun.repository;

import com.orkun.dto.DepartmentSearchCriteriaDTO;
import com.orkun.entity.Department;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Tag("unit")
public class DepartmentRepositoryTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    private Department department1;
    private Department department2;

    /**
     * This method will be executed before each and every test inside this class
     */
    @BeforeEach
    void setUp() {

        department1 = new Department();
        department1.setDepartmentCode("ABC");
        department1.setDepartmentName("Department 1");
        department1.setDepartmentDescription("Description 1");

        department2 = new Department();
        department2.setDepartmentCode("DEF");
        department2.setDepartmentName("Department 2");
        department2.setDepartmentDescription("Description 2");
    }

    @Test
    void givenDepartmentCode_whenFindByDepartmentCode_thenReturnDepartment() {

        // given - precondition or setup
        departmentRepository.save(department1);


        // when - action or behaviour that we are going to test
        Department departmentFromDb = departmentRepository.findByDepartmentCode("ABC");

        // then - verify the output
        assertThat(departmentFromDb).isNotNull();
        assertThat(departmentFromDb.getDepartmentCode()).isEqualTo("ABC");
        assertThat(departmentFromDb.getDepartmentName()).isEqualTo("Department 1");
        assertThat(departmentFromDb.getDepartmentDescription()).isEqualTo("Description 1");
    }

    @Test
    void givenDepartmentSearchCriteriaDTO_whenGetAllDepartmentsUsingPagination_thenReturnDepartmentPage() {

        // given - precondition or setup
        departmentRepository.save(department1);
        departmentRepository.save(department2);


        DepartmentSearchCriteriaDTO searchCriteria = new DepartmentSearchCriteriaDTO();
        searchCriteria.setDepartmentCode("ABC");
        searchCriteria.setDepartmentName("Depa");

        PageRequest pageRequest = PageRequest.of(0, 10);

        // when - action or behaviour that we are going to test
        Page<Department> departmentPage = departmentRepository.getAllDepartmentsUsingPagination(searchCriteria, pageRequest);

        // then - verify the output
        assertThat(departmentPage).isNotNull();
        assertThat(departmentPage.getContent()).hasSize(1);
        assertThat(departmentPage.getContent().get(0).getDepartmentCode()).isEqualTo("ABC");
        assertThat(departmentPage.getContent().get(0).getDepartmentName()).isEqualTo("Department 1");
        assertThat(departmentPage.getContent().get(0).getDepartmentDescription()).isEqualTo("Description 1");
    }
}
