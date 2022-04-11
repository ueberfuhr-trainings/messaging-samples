package de.sample.messaging.spring;

import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomersJMSConsumer {

    private final CustomersService service;

    @JmsListener(destination = "${messaging.queue}")
    public void onMessage(Message<Customer> message) {
        System.out.println(message.getPayload());
        this.service.addCustomer(message.getPayload());
    }
}
