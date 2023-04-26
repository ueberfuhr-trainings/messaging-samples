package de.sample.kafka.todos.messaging.customers;

import de.sample.kafka.todos.domain.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMessageMapper {

    CustomerMessage map(Customer customer);

}
