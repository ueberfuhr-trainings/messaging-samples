package de.sample.kafka.customers.domain;

import de.sample.kafka.customers.shared.validation.ValidationGroups;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final ApplicationEventPublisher eventPublisher;

    public void createCustomer(
      @Validated(ValidationGroups.Creating.class)
      Customer customer
    ) {
        // do some stuff here - mostly store into database
        customer.setId(UUID.randomUUID());
        // notify event listeners
        eventPublisher.publishEvent(new CustomerCreatedEvent(customer));
    }

}
