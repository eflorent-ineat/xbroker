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