package de.sample.kafka.customers.boundary;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class CustomerDto {

    @NotNull
    private UUID id;
    @NotNull
    @Size(min = 5)
    private String name;
    private LocalDate birthdate;

}
