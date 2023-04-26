package de.sample.kafka.customers.test.slices.messaging;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockReset;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.KafkaTemplate;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.mockito.Mockito.RETURNS_MOCKS;
import static org.mockito.Mockito.mock;

/**
 * Auto-configures a {@link org.springframework.kafka.core.KafkaTemplate} mock in the test context.
 * You can get the mock injected by simply using
 * <pre>
 * \u0040Autowired
 * KafkaTemplate&lt;String, CustomerDto&gt; templateMock;
 * </pre>
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@EnableAutoConfiguration(exclude = KafkaAutoConfiguration.class)
@Import(AutoConfigureKafkaTemplateMock.KafkaTemplateMockConfiguration.class)
public @interface AutoConfigureKafkaTemplateMock {

    @TestConfiguration
    class KafkaTemplateMockConfiguration {

        @Bean
        KafkaTemplate<?,?> createKafkaTemplateMock() {
            return mock(
              KafkaTemplate.class,
              MockReset
                .withSettings(MockReset.AFTER)
                .defaultAnswer(RETURNS_MOCKS)
            );
        }
    }

}
