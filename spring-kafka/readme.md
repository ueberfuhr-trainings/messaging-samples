# Spring Boot Kafka Sample

This sample contains 2 Spring Boot projects:

- a webapp to produce a customer
  - the topic is configurable in [application.yml](./customer-producer-kafka-sample/src/main/resources/application.yml)
- a consumer that stores customers (in-memory) and provides a REST service to read them out

## Install Kafka

We can use Docker to install a Kafka service. Kafka needs ZooKeeper, so both are installed as a service by

```bash
docker-compose up -d
```

Kafka is then reachable on the host system with local port `29092` (`http://localhost:29092`). You can connect to it using [Offset Explorer (formerly "KafkaTool")](https://www.kafkatool.com/). **Please note** that you need to specify the Zookeeper port `22181`
for the successful connection.

## Run Customer Producer

The producer is a Spring MVC based webapp that provides an input form to enter name and (otionally) birthdate of a customer. On submit, the customer is created and sent as a JSON message to a topic named `customers`.

To run the app, enter

```bash
cd customer-producer-kafka-sample
mvn spring-boot:run
```

Then, open `http://localhost:8080` in your browser.

## Run Customer Consumer

The consumer listens on customer messages in the `customers` topic and stores the received objects into an internal (in-memory only) list.

To run the app and read out the customers, input

```bash
cd customer-consumer-kafka-sample
mvn spring-boot:run
curl http://localhost:8081/customers
```
