package ru.lenar.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.lenar.validation.ValidEmail;
import ru.lenar.validation.ValidPassword;

@AllArgsConstructor
@Builder
@Data
public class SignUpForm {

    @Size(min = 3, max = 15)
    @NotBlank
    private String firstName;

    @Size(min = 3, max = 15)
    @NotBlank
    private String lastName;

    @Size(min = 8)
    @ValidEmail
    private String email;

    @Size(min = 6)
    @ValidPassword
    private String password;
}
