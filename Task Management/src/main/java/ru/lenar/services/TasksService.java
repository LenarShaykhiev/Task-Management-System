package ru.lenar.services;

import ru.lenar.dto.TaskDto;

import java.util.List;

/**
 * The interface Tasks service.
 */
public interface TasksService {

    /**
     * Gets tasks by author id.
     *
     * @param id   the id
     * @param page the page
     * @param size the size
     * @return the tasks by author
     */
    List<TaskDto> getTasksByAuthor(Long id, int page, int size);

    /**
     * Gets tasks by executor id.
     *
     * @param id   the id
     * @param page the page
     * @param size the size
     * @return the tasks by executor
     */
    List<TaskDto> getTasksByExecutor(Long id, int page, int size);

    /**
     * Add task.
     *
     * @param task     the task
     * @param authorId the author id
     * @return the task dto
     */
    TaskDto addTask(TaskDto task, Long authorId);

    /**
     * Update task.
     *
     * @param taskId the task id
     * @param task   the task
     * @return the task dto
     */
    TaskDto updateTask(Long taskId, TaskDto task);

    /**
     * Delete task.
     *
     * @param taskId the task id
     */
//    Удаление задачи
    void deleteTask(Long taskId);

    /**
     * Gets task by id.
     *
     * @param taskId the task id
     * @return the task by id
     */
//    Поиск задачи по ее id
    TaskDto getTaskById(Long taskId);

}
