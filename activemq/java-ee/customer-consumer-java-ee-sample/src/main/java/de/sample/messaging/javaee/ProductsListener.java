package de.sample.messaging.javaee;

import lombok.SneakyThrows;

import javax.annotation.PostConstruct;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.BytesMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.json.bind.Jsonb;
import java.nio.charset.Charset;
import java.util.logging.Logger;

@MessageDriven(name = "ProductMDB")
public class ProductsListener implements MessageListener {

    private static final Logger logger = Logger.getLogger("ProductMDB");

    @Inject
    Jsonb jsonb;

    @PostConstruct
    public void init() {
        logger.info("Products Consumer is available now!");
    }

    @Override
    @SneakyThrows
    public void onMessage(Message message) {
        logger.info("Message arrived: " + message);
        if (message instanceof TextMessage) {
            var textMessage = (TextMessage) message;
            this.handleMessage(textMessage.getText());
        } else if (message instanceof BytesMessage) {
            var bytesMessage = (BytesMessage) message;
            byte[] bytes = new byte[(int) bytesMessage.getBodyLength()];
            bytesMessage.readBytes(bytes);
            String body = new String(bytes, Charset.defaultCharset());
            this.handleMessage(body);
        }
    }

    private void handleMessage(String body) {
        var product = jsonb.fromJson(body, Product.class);
        logger.info(() -> String.format("Zu Produkt %s wurde eine Aktualisierung übermittelt: %s%n", product.getShortcut(), product));
    }

}
