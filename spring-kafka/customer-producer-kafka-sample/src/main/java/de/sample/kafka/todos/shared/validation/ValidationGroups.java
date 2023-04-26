package de.sample.kafka.todos.shared.validation;

import jakarta.validation.groups.Default;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidationGroups {

    public interface ConstraintsOnCreation extends Default {
    }

    public interface ConstraintsOnUpdate extends Default {
    }

}
