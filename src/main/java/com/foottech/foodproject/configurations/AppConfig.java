package com.foottech.foodproject.configurations;

import com.foottech.foodproject.entities.Employee;
import com.foottech.foodproject.validators.DepartmentValidator;
import com.foottech.foodproject.validators.EmployeeValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;

@Configuration
public class AppConfig {

    @Bean
    public Validator employeeValidator(){
        return new EmployeeValidator();
    }

    @Bean
    public Validator departmentValidator(){
        return new DepartmentValidator();
    }
}
