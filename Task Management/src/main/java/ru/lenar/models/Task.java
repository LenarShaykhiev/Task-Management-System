package ru.lenar.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Task{

    public enum Status {
        AWAIT, PROCESS, COMPLETED
    };

    public enum Priority {
        LOW, MEDIUM, HIGH
    };

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String comment;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Customer author;

    @ManyToOne
    @JoinColumn(name = "executor_id")
    private Customer executor;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Status status = Status.AWAIT;

    @Builder.Default
    @Enumerated
    private Priority priority = Priority.MEDIUM;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

}
