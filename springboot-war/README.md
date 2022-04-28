# Package Springboot project to war

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

# build war

```mvn clean package -DskipTests```

## verify

```
curl http://localhost:8090/spring-redis/hello
```
