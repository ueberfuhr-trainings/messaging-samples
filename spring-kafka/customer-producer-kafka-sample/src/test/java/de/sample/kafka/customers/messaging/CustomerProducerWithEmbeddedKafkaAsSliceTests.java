package de.sample.kafka.customers.messaging;

import de.sample.kafka.customers.domain.Customer;
import de.sample.kafka.customers.test.slices.messaging.AutoConfigureEmbeddedKafka;
import de.sample.kafka.customers.test.slices.messaging.AutoConfigureEmbeddedKafka.KafkaTestContext;
import de.sample.kafka.customers.test.slices.messaging.MessagingTest;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

// fill the context only with beans declared within messaging package
@MessagingTest
// enable the embedded Kafka for the test class
// let the KafkaMessageListenerContainer be managed by Spring Context (re-usable)
// reset the records between test methods (managed by JUnit Extension)
@AutoConfigureEmbeddedKafka
class CustomerProducerWithEmbeddedKafkaAsSliceTests {

    @Autowired
    KafkaTestContext<String, CustomerDto> kafka;
    @Autowired
    CustomerProducer producer;

    @Test
    void shouldSendCustomerToTopic() throws InterruptedException {
        // Arrange
        final var uuid = UUID.randomUUID();
        final var customer = Customer.builder()
          .id(uuid)
          .name("name")
          .build();
        // Act
        producer.sendCustomerToTopic(customer);
        // Assert
        var record = kafka
          .records()
          .poll(500, TimeUnit.MILLISECONDS);
        assertThat(record)
          .isNotNull()
          .extracting(ConsumerRecord::value) // read CustomerDto
          .isInstanceOfSatisfying(CustomerDto.class,
            c -> assertThat(c)
              .extracting(CustomerDto::getId, CustomerDto::getName)
              .containsExactly(uuid, "name")
          );
    }

}
