package de.sample.kafka.customers.domain;

import de.sample.kafka.customers.shared.validation.ValidationGroups;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class Customer {

    @Null(groups = ValidationGroups.Creating.class)
    @NotNull(groups = ValidationGroups.Existing.class)
    private UUID id;
    @NotNull
    @Size(min = 1)
    private String name;
    private LocalDate birthdate;

}
