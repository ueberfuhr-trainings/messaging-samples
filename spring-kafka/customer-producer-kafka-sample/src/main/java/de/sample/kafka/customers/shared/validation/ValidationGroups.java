package de.sample.kafka.customers.shared.validation;

import jakarta.validation.groups.Default;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidationGroups {

    public interface ConstraintsOnCreation extends Default {
    }

    public interface ConstraintsOnUpdate extends Default {
    }

}
