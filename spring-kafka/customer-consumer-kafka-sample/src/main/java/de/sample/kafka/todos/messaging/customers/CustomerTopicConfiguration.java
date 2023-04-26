package de.sample.kafka.todos.messaging.customers;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("application.customers.topic")
@Data
public class CustomerTopicConfiguration {

    private String name = "customers";
    private int currency = 1;

}
