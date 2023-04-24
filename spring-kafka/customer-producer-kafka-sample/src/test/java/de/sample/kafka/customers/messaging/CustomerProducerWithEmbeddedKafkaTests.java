package de.sample.kafka.customers.messaging;

import de.sample.kafka.customers.domain.Customer;
import de.sample.kafka.customers.test.slices.messaging.MessagingTest;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

// fill the context only with beans declared within messaging package
@MessagingTest
// enable the embedded Kafka for the test class
@EmbeddedKafka
// overriding the Kafka broker address and port and using random port created by the embedded Kafka instead
@SpringBootTest(properties =
  "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}"
)
// only one test instance for all test cases (performance)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerProducerWithEmbeddedKafkaTests {

    /*
     * This sample canbe found in tutorials like
     * https://www.geekyhacker.com/2020/10/03/test-spring-kafka-consumer-and-producer-with-embeddedkafka/.
     *
     * The disadvantage is that the lifecycle of the Kafka container
     * is managed by JUnit, so each test class must initialize its own container.
     * See CustomerProducerWithEmbeddedKafkaAsSliceTests to get a sample slice
     * where the container is managed within the Spring Context, so it is
     * re-usable for multiple test classes.
     */

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    EmbeddedKafkaBroker kafka;
    @Autowired
    CustomerProducer producer;
    @Autowired
    CustomerTopicConfiguration topic;

    final BlockingQueue<ConsumerRecord<String, Object>> records = new LinkedBlockingQueue<>();

    KafkaMessageListenerContainer<String, String> container;

    @BeforeAll
    void setUp() {
        final var consumerFactory = new DefaultKafkaConsumerFactory<String, String>(getConsumerProperties());
        final var containerProperties = new ContainerProperties(topic.getName());
        container = new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);
        container.setupMessageListener((MessageListener<String, Object>) records::add);
        container.start();
        ContainerTestUtils.waitForAssignment(container, kafka.getPartitionsPerTopic());
    }

    @AfterEach
    void clear() {
        records.clear();
    }

    @AfterAll
    void tearDown() {
        container.stop();
    }

    private Map<String, Object> getConsumerProperties() {
        return Map.of(
          ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBrokersAsString(),
          ConsumerConfig.GROUP_ID_CONFIG, "consumer",
          ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true",
          ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "10",
          ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "60000",
          ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
          ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class,
          JsonDeserializer.TRUSTED_PACKAGES, "*",
          ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    }

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
        var record = records.poll(500, TimeUnit.MILLISECONDS);
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
