package de.sample.kafka.customers.persistence;

import de.sample.kafka.customers.domain.Customer;
import de.sample.kafka.customers.domain.CustomerSinks;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class CustomerSinkJpaImpl implements CustomerSinks.CustomerSink {

    private final CustomerEntityRepository repo;
    private final CustomerEntityMapper mapper;

    @Override
    public Stream<Customer> findAll() {
        return this.repo
          .findAll()
          .stream()
          .map(this.mapper::map);
    }

    @Override
    public Optional<Customer> findById(UUID id) {
        return this.repo
          .findById(id)
          .map(this.mapper::map);
    }

    @Override
    public void insert(Customer customer) {
        final var entity = this.mapper.map(customer);
        this.repo.save(entity);
        // copy generated fields back to customer
        this.mapper.copyFromEntity(entity, customer);
    }
}
