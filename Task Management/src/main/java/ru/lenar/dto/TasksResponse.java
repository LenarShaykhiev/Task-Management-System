package ru.lenar.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TasksResponse {
    private List<TaskDto> data;
}
