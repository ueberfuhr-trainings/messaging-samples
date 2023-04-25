package de.sample.kafka.customers.domain;

import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.LinkedList;
import java.util.List;

@Validated
@Service
public class CustomerService {

    @Getter
    private final List<Customer> customers = new LinkedList<>();

    public void add(@Valid Customer customer) {
        this.customers.add(customer);
    }

}
