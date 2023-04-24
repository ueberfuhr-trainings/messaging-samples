package de.sample.kafka.customers.boundary;

import de.sample.kafka.customers.domain.Customer;
import de.sample.kafka.customers.domain.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@RequestMapping("/customers")
@Controller
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;

    @PostMapping
    public String create(
      @RequestParam
      String name,
      @RequestParam(required = false)
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
      LocalDate birthdate
    ) {

        final var customer = Customer.builder()
          .name(name)
          .birthdate(birthdate)
          .build();
        this.service.createCustomer(customer);
        return "redirect:index.html";

    }

}
