package de.sample.messaging.javaee;

import lombok.SneakyThrows;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Session;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@ApplicationScoped // https://github.com/OpenLiberty/open-liberty/issues/6120
@Path("messages")
public class MessagesResource {

    @Resource(lookup = "jms/QueueConnectionFactory")
    ConnectionFactory jmsConnectionFactory;

    @Resource(lookup = "jms/HelloQueue")
    Queue helloQueue;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @SneakyThrows
    public Response sendMessage(MessageDto message, @Context UriInfo info) {
        pushMessageToQueue(message.getMessage());
        var location = info.getAbsolutePathBuilder() //
          //.path(Long.toString(message.getId())) //
          .build();
        return Response.created(location).build();
    }

    private void pushMessageToQueue(String msg) throws JMSException {
        try (var con = jmsConnectionFactory.createConnection();
          var session = con.createSession(false, Session.AUTO_ACKNOWLEDGE)) {
            var message = session.createTextMessage(msg);
            session.createProducer(helloQueue).send(message);
        }
    }
}
