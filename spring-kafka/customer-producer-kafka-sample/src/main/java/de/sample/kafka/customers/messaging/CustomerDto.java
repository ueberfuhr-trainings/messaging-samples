package de.sample.kafka.customers.messaging;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class CustomerDto {

    private UUID id;
    private String name;
    private LocalDate birthdate;

}
