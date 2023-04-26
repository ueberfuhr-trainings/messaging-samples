package de.sample.kafka.todos.persistence;

import de.sample.kafka.todos.domain.Todo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TodoEntityMapper {

    Todo map(TodoEntity todo);

    TodoEntity map(Todo todo);

    void copyFromEntity(TodoEntity entity, @MappingTarget Todo todo);

}
