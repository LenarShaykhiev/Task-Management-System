package ru.lenar.security.details;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.lenar.repositories.CustomersRepository;

@RequiredArgsConstructor
@Service
public class CustomerUserDetailsService implements UserDetailsService {

    private final CustomersRepository customersRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return new CustomerUserDetails(
                customersRepository.findByEmail(email)
                        .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден!")));
    }
}
