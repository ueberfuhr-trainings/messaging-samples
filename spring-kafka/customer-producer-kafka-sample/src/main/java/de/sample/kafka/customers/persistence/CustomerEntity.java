package de.sample.kafka.customers.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
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
