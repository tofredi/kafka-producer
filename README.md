# KAFKA PRODUCER. API REST
This repository provides a simple Spring Boot app with an implementation of Kafka Producer for hands-on with Kafka.
This client will connect to your cluster and send messages through HTTP method POST.


#### Environment variables

You can specify the path of your config file  with properties and credentials to connect to your cluster with `KAFKA_CONFIG_PATH`.
Also you can specify `bootstrap.servers` config with `BOOTSTRAP_SERVERS` env.

#### Run producer with Docker

```sh
$ docker build -t my-kafka-producer .
$ docker run -e KAFKA_CONFIG_PATH=path/to/your/config.properties -e BOOTSTRAP_SERVERS=<bootstrap-servers> -p 8080:8080 my-kafka-producer
```

#### Todos
- Add docker compose file with a simple Kafka cluster for local tests
