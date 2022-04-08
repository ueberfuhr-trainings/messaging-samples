package de.sample.messaging.spring;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("customers")
@RequiredArgsConstructor
public class CustomersController {

    private final CustomersService service;
    private final CustomersJMSProducer producer;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Customer> getCustomers() {
        return this.service.getCustomers();
    }

    @GetMapping(value="{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Customer getCustomer(@PathVariable("id") long id) {
        return this.service.findById(id)
          .orElseThrow(NotFoundException::new);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> sendCustomer(Customer customer) {
        this.service.addCustomer(customer);
        this.producer.send(customer);
        var locationHeader = linkTo(methodOn(CustomersController.class).getCustomer(customer.getId())).toUri(); // HATEOAS
        return ResponseEntity.created(locationHeader).build();
    }

}
