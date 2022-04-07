package de.sample.messaging.javaee;

import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven(name = "CustomerMDB")
public class CustomerListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        System.out.println(message);
    }
}
