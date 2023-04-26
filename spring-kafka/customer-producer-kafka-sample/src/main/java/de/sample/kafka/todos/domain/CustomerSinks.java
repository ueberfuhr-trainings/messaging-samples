package de.sample.kafka.todos.domain;

import lombok.experimental.UtilityClass;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * Persistence interfaces.
 */
@UtilityClass
public class CustomerSinks {

    public interface CustomerSink extends ReadableCustomerSink, UpdatableCustomerSink {
    }

    interface ReadableCustomerSink {


        Stream<Customer> findAll();

        Optional<Customer> findById(UUID id);

    }

    interface UpdatableCustomerSink {

        void insert(Customer customer);

    }

}
