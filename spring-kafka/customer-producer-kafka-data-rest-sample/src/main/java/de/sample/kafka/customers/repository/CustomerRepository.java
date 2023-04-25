package de.sample.kafka.customers.repository;

import de.sample.kafka.customers.domain.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.UUID;

@RepositoryRestResource(
  path = "customers",
  collectionResourceRel = "customers"
)
@CrossOrigin
public interface CustomerRepository extends CrudRepository<Customer, UUID> {

    @Override
    @RestResource(exported = false)
    void deleteById(UUID uuid);

    @RestResource(path = "byName")
    List<Customer> findByName(@Param("name") String name);

}
