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

Active MQ is then reachable on the host system with local port `8161` (`http://localhost:8161`).
You can login using `admin` / `password`.
