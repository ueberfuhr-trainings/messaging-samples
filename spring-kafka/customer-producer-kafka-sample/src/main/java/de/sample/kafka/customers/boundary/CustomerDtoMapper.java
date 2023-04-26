package de.sample.kafka.customers.boundary;

import de.sample.kafka.customers.domain.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerDtoMapper {

    Customer map(CustomerDto customer);

    CustomerDto map(Customer customer);

}
