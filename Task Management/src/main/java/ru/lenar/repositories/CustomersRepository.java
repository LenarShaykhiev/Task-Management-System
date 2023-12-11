package ru.lenar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lenar.models.Customer;

import java.util.Optional;

/**
 * The interface Customers repository.
 */
public interface CustomersRepository extends JpaRepository<Customer, Long> {

    /**
     * Find customer by email.
     *
     * @param email the email
     * @return the optional of Customer
     */
    Optional<Customer> findByEmail(String email);

    /**
     * Exists by email boolean.
     *
     * @param email the email
     * @return the boolean
     */
    Boolean existsByEmail(String email);

    /**
     * Gets customer by email.
     *
     * @param email the email
     * @return the customer
     */
    Customer getCustomerByEmail(String email);
}
