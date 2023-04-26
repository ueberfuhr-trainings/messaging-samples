package de.sample.kafka.todos.domain;

import de.sample.kafka.todos.shared.validation.ValidationGroups.ConstraintsOnCreation;
import de.sample.kafka.todos.shared.validation.ValidationGroups.ConstraintsOnUpdate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class Customer {

    @Null(groups = ConstraintsOnCreation.class)
    @NotNull(groups = ConstraintsOnUpdate.class)
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
