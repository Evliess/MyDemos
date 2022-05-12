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

## Enable https

```shell
#PKCS12
#pwd 123456
keytool -genkey -alias tomcat -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore keystore.p12 -validity 3650

#JKS -- deprecated
keytool -genkey -alias liberty -storetype JKS -keyalg RSA -keysize 2048 -keystore keystore.jks -validity 3650
```

## Package

[spring-boot-assembly](https://github.com/geekidea/spring-boot-assembly)

