package de.sample.kafka.customers.messaging;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class CustomerDto {

    /* This is the messaging interface. It is also a kind of public API.
     * We use Json Serialization, so we can influence this using Jackson annotations.
     */

    private UUID id;
    private String name;
    private LocalDate birthdate;

}
