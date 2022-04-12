package de.sample.messaging.javaee;

import lombok.SneakyThrows;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.function.Function;

@ApplicationScoped // https://github.com/OpenLiberty/open-liberty/issues/6120
@Path("products")
@Tag(name = "products")
public class ProductsResource {

    @Resource(lookup = "jms/TopicConnectionFactory")
    ConnectionFactory jmsConnectionFactory;

    @Resource(lookup = "jms/ProductsTopic")
    Topic productsTopic;

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{shortcut}")
    @SneakyThrows
    public Response setProductInformation(@PathParam("shortcut") String shortcut, Product product) {
        try (var con = jmsConnectionFactory.createConnection();
          var session = con.createSession(false, Session.AUTO_ACKNOWLEDGE)) {
            var message = session.createMapMessage();
            addToMessage(message, "shortcut", shortcut);
            addToMessage(message, "name", product.getName());
            message.setDouble("price", product.getPrice());
            message.setInt("stock", product.getStock());
            session.createProducer(productsTopic).send(message);
        }
        return Response.noContent().build();
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
