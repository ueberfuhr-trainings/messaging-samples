package de.sample.kafka.todos.domain;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
@RequiredArgsConstructor
public class TodosService {

    @Delegate
    private final TodoSink sink;

}
