package ru.lenar.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.lenar.models.Customer;

import javax.management.relation.Role;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CustomerDto {

    private long id;

    private String firstName;
    private String lastName;

    private String email;
    private String password;

    private Role role;

    public static CustomerDto from(Customer customer) {
        return CustomerDto.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .password(customer.getPassword())
                .build();
    }

    public static List<CustomerDto> from(List<Customer> customers) {
        return customers.stream().map(CustomerDto::from).collect(Collectors.toList());
    }
}
