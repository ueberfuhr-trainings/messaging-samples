package de.sample.kafka.customers.messaging;

import de.sample.kafka.customers.domain.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
@RequiredArgsConstructor
@Slf4j
public class CustomerProducer {

    private final KafkaTemplate<String, CustomerDto> template;
    private final CustomerDtoMapper mapper;
    private final CustomerTopicConfiguration topic;

    @HandleAfterCreate
    public void handleCustomerCreated(Customer customer) {
        final var dto = mapper.map(customer);
        this.template.send(topic.getName(), dto)
          .thenAccept(
            result -> log.info("""
              Customer Creation Event sent to Kafka: {}
              Result is: {}
              """, dto, result)
          );
    }

}

