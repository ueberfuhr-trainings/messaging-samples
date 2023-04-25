package de.sample.kafka.customers.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@RequiredArgsConstructor
class BeanValidationValidator<T> implements Validator {
    private final jakarta.validation.Validator validator;
    private final Class<T> clazz;

    @Override
    public boolean supports(Class<?> clazz) {
        return this.clazz.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        validator
          .validate(target)
          .forEach(
            v -> errors.rejectValue(
              v.getPropertyPath().toString(),
              v.getPropertyPath().toString()
                + "."
                + v.getConstraintDescriptor()
                .getAnnotation()
                .getClass()
                .getSimpleName()
                .toLowerCase(),
              v.getMessage()
            )
          );
    }
}
