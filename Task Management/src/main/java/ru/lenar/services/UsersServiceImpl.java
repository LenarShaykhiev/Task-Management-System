package ru.lenar.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.lenar.dto.CustomerDto;
import ru.lenar.dto.SignUpForm;
import ru.lenar.models.Customer;
import ru.lenar.repositories.TasksRepository;
import ru.lenar.repositories.CustomersRepository;

import static ru.lenar.dto.CustomerDto.from;

@RequiredArgsConstructor
@Service
public class UsersServiceImpl implements UsersService {

    private final TasksRepository tasksRepository;
    private final CustomersRepository customersRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean isExist(String email) {
        return customersRepository.existsByEmail(email);
    }

    @Override
    public void signUp(SignUpForm form) {
        Customer customer = Customer.builder()
                .firstName(form.getFirstName())
                .lastName(form.getLastName())
                .email(form.getEmail())
                .password(passwordEncoder.encode(form.getPassword()))
                .role(Customer.Role.ROLE_USER)
                .build();

        customersRepository.save(customer);
    }

    @Override
    public CustomerDto getByEmail(String email) {
        Customer customer = customersRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Пользователь с таким email не найден"));
        return from(customer);
    }
}
