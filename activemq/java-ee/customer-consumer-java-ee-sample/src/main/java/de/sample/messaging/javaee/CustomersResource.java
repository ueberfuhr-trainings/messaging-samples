package de.sample.messaging.javaee;

import lombok.SneakyThrows;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;

@ApplicationScoped // https://github.com/OpenLiberty/open-liberty/issues/6120
@Path("customers")
@Tag(name = "customers")
public class CustomersResource {

    @Resource(lookup = "jms/QueueConnectionFactory")
    ConnectionFactory jmsConnectionFactory;

    @Resource(lookup = "jms/CustomerQueue")
    Queue customerQueue;

    // just in-memory
    private final Map<Long, CustomerDto> customers = new TreeMap<>();
    private long counter; // not thread-safe

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomers() {
        return Response.ok(this.customers.values()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getCustomer(@PathParam("id") long id) {
        var result = this.customers.get(id);
        if(result != null) {
            return Response.ok(result).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @SneakyThrows
    public Response sendCustomer(CustomerDto customer, @Context UriInfo info) {
        pushToQueue(customer);
        customer.setId(this.counter++);
        this.customers.put(customer.getId(), customer);
        var location = info.getAbsolutePathBuilder()
          .path(Long.toString(customer.getId()))
          .build();
        return Response.created(location).build();
    }

    private void pushToQueue(CustomerDto customer) throws JMSException {
        try (var con = jmsConnectionFactory.createConnection();
          var session = con.createSession(false, Session.AUTO_ACKNOWLEDGE)) {
            // different types of messages
            var message = session.createMapMessage();
            message.setStringProperty("produced-by", "java-ee");
            addToMessage(message, "firstname", customer.getFirstname());
            addToMessage(message, "lastname", customer.getLastname());
            addToMessage(message, "birthdate", customer.getBirthdate(), DateTimeFormatter.ISO_LOCAL_DATE::format);
            session.createProducer(customerQueue).send(message);
        }
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
