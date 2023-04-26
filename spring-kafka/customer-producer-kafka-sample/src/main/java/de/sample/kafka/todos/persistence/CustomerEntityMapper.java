package de.sample.kafka.todos.persistence;

import de.sample.kafka.todos.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CustomerEntityMapper {

    Customer map(CustomerEntity customer);

    CustomerEntity map(Customer customer);

    void copyFromEntity(CustomerEntity entity, @MappingTarget Customer customer);

}
