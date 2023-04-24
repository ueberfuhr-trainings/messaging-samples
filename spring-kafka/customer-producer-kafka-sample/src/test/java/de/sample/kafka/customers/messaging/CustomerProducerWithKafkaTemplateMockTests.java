package de.sample.kafka.customers.messaging;

import de.sample.kafka.customers.domain.Customer;
import de.sample.kafka.customers.test.slices.messaging.AutoConfigureKafkaTemplateMock;
import de.sample.kafka.customers.test.slices.messaging.MessagingTest;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

// fill the context only with beans declared within messaging package
@MessagingTest
// disable Kafka connection
// declare a mocked KafkaTemplate
@AutoConfigureKafkaTemplateMock
class CustomerProducerWithKafkaTemplateMockTests {

    @Autowired
    KafkaTemplate<String, CustomerDto> templateMock;
    @Autowired
    CustomerProducer producer;
    @Autowired
    CustomerTopicConfiguration topic;

    @Test
    void shouldSendCustomerToTopic() {
        // Arrange
        final var uuid = UUID.randomUUID();
        final var customer = Customer.builder()
          .id(uuid)
          .name("name")
          .build();
        // Act
        producer.sendCustomerToTopic(customer);
        // Assert
        ArgumentCaptor<CustomerDto> captor = ArgumentCaptor.forClass(CustomerDto.class);
        verify(templateMock).send(eq(topic.getName()), captor.capture());
        final var sentDto = captor.getValue();
        assertThat(sentDto)
          .extracting(CustomerDto::getId, CustomerDto::getName)
          .containsExactly(uuid, "name");
    }

}
