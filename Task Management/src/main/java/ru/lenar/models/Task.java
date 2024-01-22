package ru.lenar.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Task {

    public enum Status {
        AWAIT, PROCESS, COMPLETED
    }

    ;

    public enum Priority {
        LOW, MEDIUM, HIGH
    }

    ;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Customer author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "executor_id")
    private Customer executor;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Status status = Status.AWAIT;

    @Enumerated
    private Priority priority = Priority.MEDIUM;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

}
