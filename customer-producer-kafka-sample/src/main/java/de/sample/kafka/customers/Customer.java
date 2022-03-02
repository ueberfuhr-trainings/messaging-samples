package de.sample.kafka.customers;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class Customer {

    private String name;
    private LocalDate birthdate;

}
