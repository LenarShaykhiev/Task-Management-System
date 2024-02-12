package ru.lenar.aspects;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.lenar.dto.ExceptionDto;
import ru.lenar.exceptions.BadPriority;
import ru.lenar.exceptions.BadStatus;
import ru.lenar.exceptions.NoAccessException;

import java.io.IOException;

// Класс сквозной обрабоки исключений
@ControllerAdvice
public class RestExceptionHandler {

    //      Исключения поиска сущности в БД
    @ExceptionHandler({EntityNotFoundException.class, UsernameNotFoundException.class})
    public ResponseEntity<Object> handleEntityNotFound(Exception ex) {
        return ResponseEntity.status(404).body(new ExceptionDto(ex.getMessage()));
    }

    //    Исключения валидации password
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleNotValid(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(400).body(new ExceptionDto(ex.getMessage()));
    }

    //    Исключения валидации email и проверки корректности status и priority
    @ExceptionHandler({IllegalArgumentException.class, BadStatus.class, BadPriority.class})
    public ResponseEntity<Object> handleIllegalArgument(Exception ex) {
        return ResponseEntity.status(400).body(new ExceptionDto(ex.getMessage()));
    }

    //    Исключания аутентификации
    @ExceptionHandler({AuthenticationException.class, IOException.class})
    public ResponseEntity<Object> handleAuth(Exception ex) {
        return ResponseEntity.status(401).body(new ExceptionDto(ex.getMessage()));
    }

    //    Исключения доступа
    @ExceptionHandler(NoAccessException.class)
    public ResponseEntity<Object> handleNoAccess(Exception ex) {
        return ResponseEntity.status(403).body(new ExceptionDto(ex.getMessage()));
    }
}
