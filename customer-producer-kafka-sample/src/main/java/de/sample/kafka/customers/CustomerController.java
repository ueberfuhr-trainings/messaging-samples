package de.sample.kafka.customers;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@RequestMapping("/customers")
@Controller
@RequiredArgsConstructor
public class CustomerController {

    private final KafkaTemplate<String, Customer> template;

    @PostMapping
    public String sendMessage(
      @RequestParam
        String name,
      @RequestParam(required = false)
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate birthdate) {
        Customer customer = new Customer(name, birthdate);
        this.template.send("customers", customer).completable()
          .thenAccept(System.out::println);
        return "redirect:index.html";
    }

}
