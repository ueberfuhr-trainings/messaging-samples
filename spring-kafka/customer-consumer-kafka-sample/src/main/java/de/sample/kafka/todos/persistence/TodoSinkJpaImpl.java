package de.sample.kafka.todos.persistence;

import de.sample.kafka.todos.domain.Todo;
import de.sample.kafka.todos.domain.TodoSink;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class TodoSinkJpaImpl implements TodoSink {

    private final TodosEntityRepository repo;
    private final TodoEntityMapper mapper;

    @Override
    public Stream<Todo> findAll() {
        return this.repo
          .findAll()
          .stream()
          .map(this.mapper::map);
    }

    @Override
    public Optional<Todo> findById(Long id) {
        return this.repo
          .findById(id)
          .map(this.mapper::map);
    }

    @Override
    public void insert(Todo todo) {
        final var entity = this.mapper.map(todo);
        this.repo.save(entity);
        // copy generated fields back to task
        this.mapper.copyFromEntity(entity, todo);
    }
}
