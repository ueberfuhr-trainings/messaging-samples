package de.sample.kafka.todos.boundary;

import de.sample.kafka.todos.domain.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.stream.Stream;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequestMapping("/customers")
@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;
    private final CustomerDtoMapper mapper;

    @GetMapping(
      produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Stream<CustomerDto> findAll() {
        return this.service
          .findAll()
          .map(this.mapper::map);
    }

    @GetMapping(
      value = "/{uuid}",
      produces = MediaType.APPLICATION_JSON_VALUE
    )
    public CustomerDto findById(@PathVariable("uuid") UUID uuid) {
        return this.service
          .findById(uuid)
          .map(this.mapper::map)
          .orElseThrow(NotFoundException::new);
    }

    @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CustomerDto> create(@Valid @RequestBody CustomerDto dto) {
        final var customer = this.mapper.map(dto);
        this.service.insert(customer);
        final var body = this.mapper.map(customer);
        final var locationHeader = linkTo(
          methodOn(CustomerController.class).findById(customer.getId())
        ).toUri();
        return ResponseEntity.created(locationHeader).body(body);
    }

}
