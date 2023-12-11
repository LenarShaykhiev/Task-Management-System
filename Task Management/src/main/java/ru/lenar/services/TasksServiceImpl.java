package ru.lenar.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.lenar.dto.TaskDto;
import ru.lenar.mappers.StringToEnumMapper;
import ru.lenar.models.Task;
import ru.lenar.repositories.TasksRepository;
import ru.lenar.repositories.CustomersRepository;

import java.util.ArrayList;
import java.util.List;

import static ru.lenar.dto.TaskDto.from;

@Service
@RequiredArgsConstructor
public class TasksServiceImpl implements TasksService {

    private final TasksRepository tasksRepository;
    private final CustomersRepository customersRepository;

    private final StringToEnumMapper stringToEnumMapper = new StringToEnumMapper();

    @Override
    public List<TaskDto> getTasksByAuthor(Long userId, int page, int size) {
        PageRequest request = PageRequest.of(page, size, Sort.by("id"));
        Page<Task> result = tasksRepository.findAllByAuthor_IdAndIsDeletedIsNull(request, userId);
        return from(new ArrayList<>(result.getContent()));
    }

    @Override
    public List<TaskDto> getTasksByExecutor(Long userId, int page, int size) {
        PageRequest request = PageRequest.of(page, size, Sort.by("id"));
        Page<Task> result = tasksRepository.findAllByExecutor_IdAndIsDeletedIsNull(request, userId);
        return from(new ArrayList<>(result.getContent()));
    }

    @Override
    public TaskDto addTask(TaskDto task, Long authorId) {
        Task newTask = Task.builder()
                .title(task.getTitle())
                .description(task.getDescription())
                .author(customersRepository.getReferenceById(authorId))
                .executor(customersRepository.findById(task.getExecutorId()).orElseThrow(() -> new UsernameNotFoundException("Пользователь с таким id не найден")))
                .priority(task.getPriority() != null ? stringToEnumMapper.mapToPriority(task.getPriority()) : Task.Priority.MEDIUM)
                .status(Task.Status.AWAIT)
                .build();

        tasksRepository.save(newTask);

        return from(newTask);
    }

    @Override
    public TaskDto updateTask(Long taskId, TaskDto taskDto) {

        Task task = tasksRepository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Задача с таким id не найдена"));
        update(task, taskDto);
        tasksRepository.save(task);
        return from(task);

    }

    @Override
    public void deleteTask(Long taskId) {

        Task task = tasksRepository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Задача с таким id не найдена"));
        task.setIsDeleted(true);
        tasksRepository.save(task);

    }

    @Override
    public TaskDto getTaskById(Long taskId) {
        return from(tasksRepository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Задача с таким id не найдена")));
    }

    private void update(Task task, TaskDto taskDto) {

        Task.Status newStatus;
        Task.Priority newPriority;

        newStatus = stringToEnumMapper.mapToStatus(taskDto.getStatus());

        newPriority = stringToEnumMapper.mapToPriority(taskDto.getPriority());


        if (taskDto.getComment() != null) {
            task.setComment(taskDto.getComment());
        }
        if (taskDto.getStatus() != null) {
            task.setStatus(newStatus);
        }
        if (taskDto.getTitle() != null) {
            task.setTitle(taskDto.getTitle());
        }
        if (taskDto.getDescription() != null) {
            task.setDescription(taskDto.getDescription());
        }
        if (taskDto.getExecutorId() != null) {
            task.setExecutor(customersRepository.findById(taskDto.getExecutorId()).orElseThrow(() -> new EntityNotFoundException("Пользователь с таким id не найден")));
        }
        if (taskDto.getPriority() != null) {
            task.setPriority(newPriority);
        }
    }

}
