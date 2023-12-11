package ru.lenar.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.lenar.dto.TaskDto;
import ru.lenar.dto.TasksResponse;
import ru.lenar.exceptions.NoAccessException;
import ru.lenar.services.TasksService;
import ru.lenar.services.UsersService;

import java.time.LocalDateTime;

import static ru.lenar.security.constant.SecurityConstant.*;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
@Api("Контроллер для управления задачами")
public class TasksController {

    private final TasksService tasksService;
    private final UsersService usersService;

    @GetMapping(value = "/byAuthor/{user-id}")
    @ApiOperation("Получение списка всех задач, созданных пользователем")
    public ResponseEntity<TasksResponse> getTasksByAuthor(@PathVariable("user-id") Long userId, @RequestParam("page") int page, @RequestParam("size") int size) {
        return ResponseEntity.ok()
                .headers(httpHeaders -> {
                    httpHeaders.add("dateTime", LocalDateTime.now().toString());
                })
                .body(TasksResponse.builder().data(tasksService.getTasksByAuthor(userId, page, size)).build());
    }

    @GetMapping(value = "/byExecutor/{user-id}")
    @ApiOperation("Получение списка всех задач, полученных пользователем")
    public ResponseEntity<TasksResponse> getTaskByExecutor(@PathVariable("user-id") Long userId, @RequestParam("page") int page, @RequestParam("size") int size) {
        return ResponseEntity.ok()
                .headers(httpHeaders -> {
                    httpHeaders.add("dateTime", LocalDateTime.now().toString());
                })
                .body(TasksResponse.builder().data(tasksService.getTasksByExecutor(userId, page, size)).build());
    }

    @PostMapping(value = "/create")
    @ApiOperation("Создание задачи")
    public TaskDto addTask(@RequestBody TaskDto task, @RequestHeader (JWT_TOKEN_HEADER) String token) {
        String email = getEmailFromToken(token);
        Long userId = usersService.getByEmail(email).getId();
        return tasksService.addTask(task, userId);
    }

    @PutMapping(value = "/{task-id}")
    @ApiOperation("Обновление существующей задачи")
    public TaskDto update(@PathVariable("task-id") Long taskId, @RequestBody TaskDto task,
                          @RequestHeader (JWT_TOKEN_HEADER) String token) {
        String email = getEmailFromToken(token);
        if (usersService.getByEmail(email).getId() != tasksService.getTaskById(taskId).getExecutorId()){
            throw new NoAccessException("Вы не исполнитель этой задачи");
        }
        return tasksService.updateTask(taskId, task);
    }

    @DeleteMapping(value = "/{task-id}")
    @ApiOperation("Удаление задачи")
    public void deleteTask(@PathVariable("task-id") Long taskId) {
        tasksService.deleteTask(taskId);
    }

    private String getEmailFromToken(String token) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(TOKEN_SECRET))
                .build().verify(token.replace(TOKEN_PREFIX, ""));

        return decodedJWT.getClaim("email").asString();
    }
}
