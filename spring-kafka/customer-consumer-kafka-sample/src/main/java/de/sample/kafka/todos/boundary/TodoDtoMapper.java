package de.sample.kafka.todos.boundary;

import de.sample.kafka.todos.domain.Todo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TodoDtoMapper {

    TodoDto map(Todo todo);

}
