package de.sample.kafka.customers.messaging;

import de.sample.kafka.customers.domain.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMessageMapper {

    CustomerMessage map(Customer customer);

}
