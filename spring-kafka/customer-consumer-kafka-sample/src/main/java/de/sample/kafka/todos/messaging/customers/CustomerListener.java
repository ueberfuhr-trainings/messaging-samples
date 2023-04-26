package de.sample.kafka.todos.messaging.customers;

import de.sample.kafka.todos.domain.Todo;
import de.sample.kafka.todos.domain.TodosService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;

@Validated
@Component
@RequiredArgsConstructor
@Slf4j
public class CustomerListener {

    private final TodosService todosService;

    @KafkaListener(
      topics = "${application.customers.topic.name}",
      concurrency = "${application.customers.topic.currency}"
    )
    public void listen(@Valid @Payload CustomerMessage customerMessage) {

        /*
         * We let the framework do the validation here.
         * We could also do the validation in the service. In this case,
         * we would be able to handle validation errors for incoming messages
         * here.
         */

        /*
         * It would also be possible to get more details like that:
         *
         * @Payload User user,
         * @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
         * @Header(KafkaHeaders.RECEIVED_PARTITION_ID) Integer partition,
         * @Header(KafkaHeaders.OFFSET) Long offset
         */
        log.info(
          """
            Received customer: {}
            Will create a todo to call the customer within the next 2 weeks""",
          customerMessage
        );
        // create task to call the customer
        final var todo = Todo.builder()
          .title("Call the new customer (" + customerMessage.getName() + ")")
          .description("phone: " + customerMessage.getPhone())
          .duedate(LocalDate.now().plusWeeks(2))
          .build();
        this.todosService.insert(todo);
    }

}
