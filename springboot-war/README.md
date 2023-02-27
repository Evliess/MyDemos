## Package Springboot project to war

```xml
<packaging>war</packaging>
<build>
    <finalName>spring-redis</finalName>
    <resources>
        <resource>
            <directory>${basedir}/config</directory>
            <targetPath>config</targetPath>
        </resource>
    </resources>
		
		......
		
</build>
```

### Build war

```mvn clean package -DskipTests```

### Verify

```
curl http://localhost:8443/hello
```

## Enable mtls
- https://medium.com/ing-tech-romania/a-simple-mtls-guide-for-spring-boot-microservices-c6bfc9878369

```shell
keytool -genkeypair -alias server -keyalg RSA -keysize 4096 -validity 365 -dname "CN=Server,OU=Server,O=Examples,L=,S=CA,C=U" -keypass changeit -keystore server.p12 -storeType PKCS12 -storepass changeit
keytool -genkeypair -alias client -keyalg RSA -keysize 4096 -validity 365 -dname "CN=Client,OU=Server,O=Examples,L=,S=CA,C=U" -keypass changeit -keystore client.p12 -storeType PKCS12 -storepass changeit
// export public keys
keytool -exportcert -alias client -file client.cer -keystore client.p12 -storepass changeit
keytool -exportcert -alias server -file server.cer -keystore server.p12 -storepass changeit
//import public keys to trust stores.
keytool -importcert -keystore client-truststore.p12 -alias server-public -file server.cer -storepass changeit -noprompt
keytool -importcert -keystore server-truststore.p12 -alias client-public -file client.cer -storepass changeit -noprompt
```

### Verify

```shell
curl -v -k https://localhost:8443/hello
```

### Add client verify
```yaml
server.ssl.client-auth: need
server.ssl.trust-store: classpath:server-truststore.p12
server.ssl.trust-store-password: changeit
```

```shell
# Not work
curl -v -k https://localhost:8443/hello

# Worked
curl -k -v --cert-type P12 --cert client.p12:changeit https://localhost:8443/hello
```

