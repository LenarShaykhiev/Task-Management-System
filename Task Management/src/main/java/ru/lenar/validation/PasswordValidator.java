package ru.lenar.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value.contains("!") || value.contains("@") || value.contains("#")){
            return true;
        } else throw new IllegalArgumentException("Пароль должен содержать !, @ или #");

    }
}
