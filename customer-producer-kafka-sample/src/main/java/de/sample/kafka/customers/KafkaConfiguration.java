package de.sample.kafka.customers;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfiguration {

    @Bean // optionally to create topic
    public NewTopic topic() {
        return TopicBuilder.name("customers")
          .partitions(2)
          .replicas(1)
          .build();
    }

}
