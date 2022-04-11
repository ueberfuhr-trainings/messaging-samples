# Active MQ Samples

This samples use Active MQ to communicate. There are implementations for

- [Java EE with Liberty and JMS](java-ee)
- [Jakarta EE with Liberty and JMS](jakarta-ee)
- [Spring Boot and JMS](spring-jms)
- [Spring Boot and STOMP](spring-stomp)

## Install Active MQ

We can use Docker to install Active MQ.

```bash
docker-compose up -d
```

**Hint:** To run artemis, just type

```bash
docker run -p 8161:8161 -p 61616:61616 -e AMQ_USER=admin -e AMQ_PASSWORD=password --name artemis quay.io/artemiscloud/activemq-artemis-broker:latest
```

Active MQ is then reachable on the host system with local port `8161` (`http://localhost:8161`).
You can login using `admin` / `password`.
