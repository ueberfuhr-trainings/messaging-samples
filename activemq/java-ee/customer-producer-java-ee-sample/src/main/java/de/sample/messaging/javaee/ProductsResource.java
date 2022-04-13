package de.sample.messaging.javaee;

import lombok.SneakyThrows;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.Session;
import javax.jms.Topic;
import javax.json.bind.Jsonb;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped // https://github.com/OpenLiberty/open-liberty/issues/6120
@Path("products")
@Tag(name = "products")
public class ProductsResource {

    @Resource(lookup = "jms/TopicConnectionFactory")
    ConnectionFactory jmsConnectionFactory;

    @Resource(lookup = "jms/ProductsTopic")
    Topic productsTopic;

    @Inject
    Jsonb jsonb;

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{shortcut}")
    @SneakyThrows
    public Response setProductInformation(@PathParam("shortcut") String shortcut, ProductDTO dto) {
        try (var con = jmsConnectionFactory.createConnection();
          var session = con.createSession(false, Session.AUTO_ACKNOWLEDGE)) {
            // because the topic is also used via STOMP, only text and byte messages are allowed
            var product = ProductsResource.from(shortcut, dto);
            var body = jsonb.toJson(product);
            var message = session.createTextMessage(body);
            session.createProducer(productsTopic).send(message);
        }
        return Response.noContent().build();
    }

    public static Product from(String shortcut, ProductDTO dto) {
        var result = new Product();
        result.setShortcut(shortcut);
        result.setName(dto.getName());
        result.setPrice(dto.getPrice());
        result.setStock(dto.getStock());
        return result;
    }

}
