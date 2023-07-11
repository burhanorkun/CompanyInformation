package com.foottech.foodproject.validators;

import com.foottech.foodproject.entities.Department;
import com.foottech.foodproject.entities.Employee;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class DepartmentValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Employee.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Department department = (Department) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "NotEmpty");

        if(department.getName().length() < 3){
            errors.rejectValue("name", "Size.userForm.name");
        }
    }
}
