package de.sample.messaging.javaee;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

@ApplicationScoped
public class JsonbProvider {

    @Produces
    @ApplicationScoped
    public Jsonb provideBuilder() {
        return JsonbBuilder.create();
    }

}
