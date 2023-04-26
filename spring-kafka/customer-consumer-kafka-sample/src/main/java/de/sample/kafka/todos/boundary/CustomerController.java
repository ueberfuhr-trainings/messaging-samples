package de.sample.kafka.todos.boundary;

import de.sample.kafka.todos.domain.TodosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/todos")
@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final TodosService service;
    private final TodoDtoMapper mapper;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TodoDto> getTodos() {
        return service.findAll()
          .map(mapper::map)
          .collect(Collectors.toList());
    }

}
