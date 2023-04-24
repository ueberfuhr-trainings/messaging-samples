package de.sample.kafka.customers.messaging;

import de.sample.kafka.customers.domain.Customer;
import de.sample.kafka.customers.domain.CustomerCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomerProducer {

    private final KafkaTemplate<String, CustomerDto> template;
    private final CustomerDtoMapper mapper;
    private final CustomerTopicConfiguration topic;

    @EventListener(CustomerCreatedEvent.class)
    public void handleCustomerCreatedEvent(CustomerCreatedEvent event) {
        this.sendCustomerToTopic(event.customer());
    }

    public void sendCustomerToTopic(Customer customer) {
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
