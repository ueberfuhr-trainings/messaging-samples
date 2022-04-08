package de.sample.messaging.spring;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class CustomersJMSProducer {

    private final JmsTemplate template;
    @Value("${messaging.queue}")
    private final String queue;

    public void send(Customer customer) {
        this.template.send(this.queue, session -> {
            var message = session.createMapMessage();
            message.setStringProperty("produced-by", "java-ee");
            addToMessage(message, "firstname", customer.getFirstname());
            addToMessage(message, "lastname", customer.getLastname());
            addToMessage(message, "birthdate", customer.getBirthdate(), DateTimeFormatter.ISO_LOCAL_DATE::format);
            return message;
        });
    }

    private <T> void addToMessage(MapMessage message, String name, T data, Function<T, String> convert) throws JMSException {
        if (data != null) {
            message.setString(name, convert.apply(data));
        }
    }

    private void addToMessage(MapMessage message, String name, String data) throws JMSException {
        this.addToMessage(message, name, data, Function.identity());
    }


}
