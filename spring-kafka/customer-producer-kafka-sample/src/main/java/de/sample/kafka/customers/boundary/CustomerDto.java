package de.sample.kafka.customers.boundary;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class CustomerDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
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
