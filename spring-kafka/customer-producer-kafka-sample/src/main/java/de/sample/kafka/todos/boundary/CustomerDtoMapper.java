package de.sample.kafka.todos.boundary;

import de.sample.kafka.todos.domain.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerDtoMapper {

    Customer map(CustomerDto customer);

    CustomerDto map(Customer customer);

}
