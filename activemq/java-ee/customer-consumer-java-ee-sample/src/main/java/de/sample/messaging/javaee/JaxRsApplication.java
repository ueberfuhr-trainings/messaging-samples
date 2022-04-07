package de.sample.messaging.javaee;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@OpenAPIDefinition(
  info = @Info(
    version = "1",
    title = "Customer Consumer API"
  )
)
@ApplicationPath("api/v1")
public class JaxRsApplication extends Application {
}
