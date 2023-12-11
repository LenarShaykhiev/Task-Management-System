package ru.lenar.mappers;

import org.springframework.context.annotation.Bean;
import ru.lenar.exceptions.BadPriority;
import ru.lenar.exceptions.BadStatus;
import ru.lenar.models.Task;


public class StringToEnumMapper {

    @Bean
    StringToEnumMapper stringToEnumMapper() {
        return new StringToEnumMapper();
    }

    public Task.Priority mapToPriority(String strPriority) throws IllegalArgumentException {
        Task.Priority priority;
        switch (strPriority.toUpperCase()) {
            case "LOW" -> priority = Task.Priority.LOW;
            case "MEDIUM" -> priority = Task.Priority.MEDIUM;
            case "HIGH" -> priority = Task.Priority.HIGH;
            default -> throw new BadPriority("Некорректный приоритет задачи!");
        }
        return priority;
    }

    public Task.Status mapToStatus(String strStatus) {
        Task.Status status;
        if (strStatus != null) {
            switch (strStatus.toUpperCase()) {
                case "AWAIT" -> status = Task.Status.AWAIT;
                case "PROCESS" -> status = Task.Status.PROCESS;
                case "COMPLETED" -> status = Task.Status.COMPLETED;
                default -> throw new BadStatus("Некорректный статус задачи!");
            }
        } else return null;

        return status;
    }
}
