package ru.lenar.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.lenar.models.Task;

import java.util.ArrayList;

/**
 * The interface Tasks repository.
 */
public interface TasksRepository extends JpaRepository<Task, Long> {

    /**
     * Find all tasks by author id and is deleted is null page.
     *
     * @param pageable   the pageable
     * @param customerId the customer id
     * @return the page of tasks
     */
    Page<Task> findAllByAuthor_IdAndIsDeletedIsNull(Pageable pageable, Long customerId);

    /**
     * Find all tasks by executor id and is deleted is null page.
     *
     * @param pageable   the pageable
     * @param customerId the customer id
     * @return the page of tasks
     */
    Page<Task> findAllByExecutor_IdAndIsDeletedIsNull(Pageable pageable, Long customerId);

}
