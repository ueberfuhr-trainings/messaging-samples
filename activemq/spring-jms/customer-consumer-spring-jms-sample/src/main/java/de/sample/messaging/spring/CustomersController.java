package de.sample.messaging.spring;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("customers")
@RequiredArgsConstructor
public class CustomersController {

    private final CustomersService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Customer> getCustomers() {
        return this.service.getCustomers();
    }

    @GetMapping(value="{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Customer getCustomer(@PathVariable("id") long id) {
        return this.service.findById(id)
          .orElseThrow(NotFoundException::new);
    }

}
