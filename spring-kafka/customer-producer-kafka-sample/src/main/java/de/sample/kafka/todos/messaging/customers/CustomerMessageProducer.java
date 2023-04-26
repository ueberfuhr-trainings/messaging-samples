package de.sample.kafka.todos.messaging.customers;

import domain.de.sample.kafka.todos.Customer;
import de.sample.kafka.todos.domain.CustomerCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomerMessageProducer {

    private final KafkaTemplate<String, CustomerMessage> template;
    private final CustomerMessageMapper mapper;
    private final CustomerTopicConfiguration topic;

    @EventListener(CustomerCreatedEvent.class)
    public void handleCustomerCreatedEvent(CustomerCreatedEvent event) {
        this.sendCustomerToTopic(event.customer());
    }

    public void sendCustomerToTopic(Customer customer) {
        final var message = mapper.map(customer);
        this.template.send(topic.getName(), message)
          .thenAccept(
            result -> log.info("""
              Customer Creation Event sent to Kafka: {}
              Result is: {}
              """, message, result)
          );
    }

}
