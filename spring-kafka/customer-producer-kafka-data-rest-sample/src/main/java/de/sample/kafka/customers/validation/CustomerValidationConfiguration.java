package de.sample.kafka.customers.validation;

import de.sample.kafka.customers.domain.Customer;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@Configuration
@RequiredArgsConstructor
public class CustomerValidationConfiguration {

    private final Validator beanValidator;

    @Bean
    public RepositoryRestConfigurer repositoryRestConfigurer() {
        return new RepositoryRestConfigurer() {
            @Override
            public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener v) {
                final var validator = new BeanValidationValidator<>(beanValidator, Customer.class);
                v.addValidator(
                  "beforeCreate",
                  validator
                );
                v.addValidator(
                  "beforeSave",
                  validator
                );
            }
        };
    }

}
