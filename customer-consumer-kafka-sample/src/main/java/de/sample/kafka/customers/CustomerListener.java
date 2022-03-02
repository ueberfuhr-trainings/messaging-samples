package de.sample.kafka.customers;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerListener {

    private final CustomerService service;

    @KafkaListener(topics = "customers")
    public void listen(Customer customer) {
        this.service.add(customer);
    }

}
