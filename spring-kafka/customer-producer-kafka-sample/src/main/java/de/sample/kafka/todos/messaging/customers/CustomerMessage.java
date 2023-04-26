package de.sample.kafka.todos.messaging.customers;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class CustomerMessage {

    /* This is the messaging interface. It is also a kind of public API.
     * We use Json Serialization, so we can influence this using Jackson annotations.
     */

    private UUID id;
    private String name;
    private String phone;
    private LocalDate birthdate;

}
