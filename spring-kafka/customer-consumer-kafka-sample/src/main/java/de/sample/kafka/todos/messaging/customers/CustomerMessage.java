package de.sample.kafka.todos.messaging.customers;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class CustomerMessage {

    /* This is the messaging interface. It is also a kind of public API.
     * We use Json Serialization, so we can influence this using Jackson annotations.
     */

    @NotNull
    private UUID id;
    @NotNull
    @Size(min = 1)
    private String name;
    @NotNull
    @Pattern(
      regexp = "^[+]*[(]?[0-9]{1,4}[)]{0,1}[-\\s./0-9]*$",
      message = "must contain a valid phone number"
    )
    private String phone;
    private LocalDate birthdate;

}
