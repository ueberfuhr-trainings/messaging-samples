package de.sample.kafka.customers.messaging;

import de.sample.kafka.customers.domain.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Validated
@Component
@RequiredArgsConstructor
@Slf4j
public class CustomerListener {

    private final CustomerService service;
    private final CustomerMessagingDtoMapper mapper;

    @KafkaListener(
      topics = "${application.customers.topic.name}",
      concurrency = "${application.customers.topic.currency}"
    )
    public void listen(@Valid @Payload CustomerMessagingDto customerMessagingDto) {

        /*
         * We let the framework do the validation here.
         * We could also do the validation in the service. In this case,
         * we would be able to handle validation errors for incoming messages
         * here.
         */

        /*
         * It would also be possible to get more details like that:
         *
         * @Payload User user,
         * @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
         * @Header(KafkaHeaders.RECEIVED_PARTITION_ID) Integer partition,
         * @Header(KafkaHeaders.OFFSET) Long offset
         */
        log.info("Received customer: {}", customerMessagingDto);
        final var customer = this.mapper.map(customerMessagingDto);
        this.service.add(customer);
    }

}
