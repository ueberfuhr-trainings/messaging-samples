package de.sample.kafka.customers.messaging;

import de.sample.kafka.customers.domain.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerDtoMapper {

    CustomerDto map(Customer customer);

}
