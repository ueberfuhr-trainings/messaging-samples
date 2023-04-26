package de.sample.kafka.todos.messaging.customers;

import de.sample.kafka.todos.domain.Customer;
import de.sample.kafka.todos.domain.CustomerCreatedEvent;
import de.sample.kafka.todos.test.slices.messaging.AutoConfigureKafkaTemplateMock;
import de.sample.kafka.todos.test.slices.messaging.MessagingTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

// fill the context only with beans declared within messaging package
@MessagingTest
// disable Kafka connection
// declare a mocked KafkaTemplate
@AutoConfigureKafkaTemplateMock
class CustomerMessageProducerWithKafkaTemplateMockTests {

    @Autowired
    KafkaTemplate<String, CustomerMessage> templateMock;
    @Autowired
    CustomerTopicConfiguration topic;

    @Nested
    @DisplayName("GIVEN we have an event publisher")
    class GivenAnEventPublisher {

        @Autowired
        ApplicationEventPublisher publisher;

        @Nested
        @DisplayName("WHEN we send an event that a customer is created")
        class WhenNewCustomerIsCreated {

            @BeforeEach
            void sendEvent() {
                final var uuid = UUID.randomUUID();
                final var customer = Customer.builder()
                  .id(uuid)
                  .name("name")
                  .build();
                publisher.publishEvent(new CustomerCreatedEvent(customer));
            }

            @Test
            @DisplayName("THEN a customer message is sent to the customer topic")
            void shouldSendCustomerMessageToCustomerTopic() {
                verify(templateMock).send(eq(topic.getName()), any());
            }

        }

    }

}
