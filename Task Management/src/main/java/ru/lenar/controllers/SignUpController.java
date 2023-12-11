package ru.lenar.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.lenar.dto.SignUpForm;
import ru.lenar.services.UsersService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/signUp")
@PreAuthorize("permitAll()")
@Api("Контроллер для регистрации пользователя")
public class SignUpController {

    private final UsersService usersService;

    @PostMapping()
    @ApiOperation(value = "Регистрация пользователя", authorizations = { @Authorization(value = "jwtToken")})
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpForm form){
        if(usersService.isExist(form.getEmail())) {
            return new ResponseEntity<>("Email already taken!", HttpStatus.BAD_REQUEST);
        }
        usersService.signUp(form);

        return new ResponseEntity<>("User registered successfully!", HttpStatus.OK);
    }
}
