package de.sample.kafka.customers.messaging;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("application.customers.topic")
@Data
public class CustomerTopicConfiguration {

    private String name = "customers";
    private int partitions = 1;
    private int replica = 1;

}
