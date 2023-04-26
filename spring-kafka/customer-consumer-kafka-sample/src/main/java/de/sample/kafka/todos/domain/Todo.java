package de.sample.kafka.todos.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Todo {
    private Long id;
    @NotNull
    @Size(min = 1)
    private String title;
    private String description;
    private LocalDate duedate;

}
