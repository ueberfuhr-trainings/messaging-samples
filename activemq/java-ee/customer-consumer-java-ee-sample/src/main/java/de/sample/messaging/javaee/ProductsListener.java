package de.sample.messaging.javaee;

import lombok.SneakyThrows;

import javax.annotation.PostConstruct;
import javax.ejb.MessageDriven;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.Optional;
import java.util.logging.Logger;

@MessageDriven(name = "ProductMDB")
public class ProductsListener implements MessageListener {

    private static final Logger logger = Logger.getLogger("ProductMDB");

    @PostConstruct
    public void init() {
        logger.info("Products Consumer is available now!");
    }

    @Override
    @SneakyThrows
    public void onMessage(Message message) {
        if (message instanceof MapMessage) {
            var map = (MapMessage) message;
            var shortcut = map.getString("shortcut");
            var product = new Product();
            Optional.ofNullable(map.getString("name")).ifPresent(product::setName);
            product.setPrice(map.getDouble("price"));
            product.setStock(map.getInt("stock"));
            logger.info(() -> String.format("Zu Produkt %s wurde eine Aktualisierung übermittelt: %s%n", shortcut, product));
        }
    }

}
