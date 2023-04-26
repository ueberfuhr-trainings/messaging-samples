package de.sample.kafka.todos.domain;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Persistence interfaces.
 */
public interface TodoSink {

    Stream<Todo> findAll();

    Optional<Todo> findById(Long id);

    void insert(Todo todo);

}
