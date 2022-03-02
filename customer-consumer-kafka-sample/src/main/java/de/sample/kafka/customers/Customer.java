package de.sample.kafka.customers;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Customer {

    private String name;
    private LocalDate birthdate;

}
