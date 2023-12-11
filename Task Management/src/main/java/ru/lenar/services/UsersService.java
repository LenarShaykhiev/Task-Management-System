package ru.lenar.services;

import ru.lenar.dto.CustomerDto;
import ru.lenar.dto.SignUpForm;

/**
 * The interface Users service.
 */
public interface UsersService {

    /**
     * Is exist.
     *
     * @param email the email
     * @return the boolean
     */
    boolean isExist(String email);

    /**
     * Sign up.
     *
     * @param form the form
     */
    void signUp(SignUpForm form);

    /**
     * Gets by email.
     *
     * @param email the email
     * @return the by email
     */
    CustomerDto getByEmail(String email);
}
