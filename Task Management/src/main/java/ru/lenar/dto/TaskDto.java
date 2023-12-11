package ru.lenar.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.lenar.models.Task;
import ru.lenar.models.Customer;

import java.util.List;
import java.util.stream.Collectors;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDto {


    private Long id;
    @NotBlank
    private String title;
    private String description;

    private String comment;

    private Long authorId;
    private Long executorId;

    private String priority;
    private String status;

    public static TaskDto from(Task task) {
        return TaskDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .comment(task.getComment())
                .authorId(task.getAuthor().getId())
                .executorId(task.getExecutor().getId())
                .status(task.getStatus().toString())
                .priority(task.getPriority().toString())
                .build();

    }

    public static List<TaskDto> from(List<Task> tasks) {
        return tasks.stream().map(TaskDto::from).collect(Collectors.toList());
    }
}
