package de.sample.kafka.customers.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class Customer {

    @NotNull
    private UUID id;
    @NotNull
    @Size(min = 1)
    private String name;
    private LocalDate birthdate;

}
