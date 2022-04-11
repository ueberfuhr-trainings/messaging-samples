package de.sample.messaging.spring;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomersJMSProducer {

    private final JmsTemplate template;
    @Value("${messaging.queue}")
    private final String queue;

    public void send(Customer customer) {
        this.template.convertAndSend(this.queue, customer);
    }

}
