package de.sample.kafka.customers;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class CustomerService {

    @Getter
    private final List<Customer> customers = new LinkedList<>();

    public void add(Customer customer) {
        System.out.println("Add customer " + customer);
        this.customers.add(customer);
    }

}
