package de.sample.messaging.javaee;

import lombok.SneakyThrows;

import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.function.Function;

@MessageDriven(name = "CustomerMDB")
public class CustomerListener implements MessageListener {

    @Inject
    CustomerManager manager;

    @Override
    @SneakyThrows
    public void onMessage(Message message) {
        if(message instanceof MapMessage) {
            var map = (MapMessage) message;
            Customer customer = new Customer();
            getFromMessage(map, "firstname").ifPresent(customer::setFirstname);
            getFromMessage(map, "lastname").ifPresent(customer::setLastname);
            getFromMessage(map, "birthdate", s -> LocalDate.parse(s, DateTimeFormatter.ISO_LOCAL_DATE))
              .ifPresent(customer::setBirthdate);
            this.manager.addCustomer(customer);
        }
    }


    private <T> Optional<T> getFromMessage(MapMessage message, String name, Function<String, T> convert) throws JMSException {
        return Optional.ofNullable(message.getString(name))
          .map(convert);
    }

    private Optional<String> getFromMessage(MapMessage message, String name) throws JMSException {
        return this.getFromMessage(message, name, Function.identity());
    }
}
