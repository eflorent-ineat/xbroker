# about

Demo app maybe an IoT platform baby featuring:

- Springboot 2.2.1
- Mongo Repository
- Artemis AMQP
- spring-security, spring-security-oauth2, spring-security-test, 
- Angular 8.2.11
- RxJS 6.5.3, NgRx
- AngularMaterial,


# run

```
mvn clean install
mvn --projects backend compile
mvn --projects backend spring-boot:run
```

# run frontend in  dev mode

```
cd frontend
npm run start
```

# Docker

```
docker build . --tag upyfx/xbroker:latest
docker run -d -p 8088:8088 --name xbrooker upyfx/xbroker:latest
```
