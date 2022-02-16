## Run with docker link
```
# Didn't work without link
docker run --name spring-redis -p 8090:8090 -eSERVER_PORT=8090 -eREDIS_HOST=redis -eREDIS_PORT=6379 waitplay/spring-redis:1.0.0
# Work as expected
docker run --name spring-redis --link redis:redis -p 8090:8090 -eSERVER_PORT=8090 -eREDIS_HOST=redis -eREDIS_PORT=6379 waitplay/spring-redis:1.0.0


docker login shguijj@gmail.com
```
## Run with docker compose

```
docker-compose -f docker-compose.yml up -d
docker-compose -f docker-compose.yml down

# Verify
curl -d 'key=hello' -d 'value=world' -X POST  http://localhost:9080/greeting
curl http://localhost:9080/hello/hello
```

