package ru.lenar.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value.contains("@") && value.contains(".") && !value.contains("@.") && value.indexOf("@") < value.lastIndexOf(".")){
            return true;
        } else throw new IllegalArgumentException("Некорректный email");
    }
}
