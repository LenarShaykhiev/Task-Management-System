package ru.lenar.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.lenar.validation.ValidPassword;

@Data
@AllArgsConstructor
@Builder
public class SignInForm {

    @Email
    @NotBlank
    private String email;

    @ValidPassword
    @NotBlank
    private String password;

}
