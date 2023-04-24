package de.sample.kafka.customers.test.slices.messaging;

import de.sample.kafka.customers.messaging.Messaging;
import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
// Test Configuration
@SpringBootTest
@ComponentScan(basePackageClasses = Messaging.class)
@ActiveProfiles({ "test", "messaging-test" })
@Tag("messaging-test")
public @interface MessagingTest {
}
