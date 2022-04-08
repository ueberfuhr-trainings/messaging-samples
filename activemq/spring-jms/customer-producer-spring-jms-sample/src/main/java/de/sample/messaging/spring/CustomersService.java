package de.sample.messaging.spring;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

@Service
public class CustomersService {

    // just in-memory
    private final Map<Long, Customer> customers = new TreeMap<>();
    private long counter;

    public Collection<Customer> getCustomers() {
        return this.customers.values();
    }

    public Optional<Customer> findById(long id) {
        return Optional.ofNullable(this.customers.get(id));
    }

    public void addCustomer(Customer customer) {
        synchronized (this) {
            customer.setId(this.counter++);
        }
        this.customers.put(customer.getId(), customer);
    }


}
