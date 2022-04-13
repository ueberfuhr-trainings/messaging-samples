# Java EE Samples for Active MQ

To install the server locally and build the app, type

```bash
mvn package -Plocal
```

To start and stop the server, just use
- `mvn liberty:dev`
- `mvn liberty:stop`

You can then open Swagger UI using
- `http://localhost:9080/openapi/ui/` (Producer)
- `http://localhost:9081/openapi/ui/` (Consumer)
