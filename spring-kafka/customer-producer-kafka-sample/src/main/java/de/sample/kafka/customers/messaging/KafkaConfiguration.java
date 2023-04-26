package de.sample.kafka.customers.messaging;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfiguration {

    @Bean // optionally to create topic
    public NewTopic topic(CustomerTopicConfiguration topic) {
        return TopicBuilder.name(topic.getName())
          .partitions(topic.getPartitions())
          .replicas(topic.getReplica())
          .build();
    }

}
