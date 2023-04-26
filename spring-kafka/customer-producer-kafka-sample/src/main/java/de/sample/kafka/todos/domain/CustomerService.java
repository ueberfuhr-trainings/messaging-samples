package de.sample.kafka.todos.domain;

import de.sample.kafka.todos.shared.validation.ValidationGroups.ConstraintsOnCreation;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final ApplicationEventPublisher eventPublisher;
    @Delegate(types = CustomerSinks.ReadableCustomerSink.class)
    private final CustomerSinks.CustomerSink sink;

    public void insert(
      @Validated(ConstraintsOnCreation.class)
      Customer customer
    ) {
        // do some stuff here - mostly store into database
        this.sink.insert(customer);
        // notify event listeners
        eventPublisher.publishEvent(new CustomerCreatedEvent(customer));
    }

}
