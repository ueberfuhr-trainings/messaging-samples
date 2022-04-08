package de.sample.messaging.javaee;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("customers")
@Tag(name = "customers")
public class CustomersResource {

    @Inject
    CustomersManager manager;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomers() {
        return Response.ok(this.manager.getCustomers()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getCustomer(@PathParam("id") long id) {
        return this.manager.findById(id)
          .map(Response::ok)
          .orElse(Response.status(Response.Status.NOT_FOUND))
          .build();
    }

}
