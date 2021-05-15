## Enable Https

### Generating a Keystore

```
#123456
keytool -genkeypair -alias pkgtest -keyalg RSA -keysize 2048 -keystore ./pkgtest.jks -validity 3650
```

### Build a war target

**1.first build custom project to a jar**

```
<plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-jar-plugin</artifactId>
                <executions>
                    <execution>
                        <phase>prepare-package</phase>
                        <goals>
                            <goal>jar</goal>
                        </goals>
                        <configuration>
                            <classesDirectory>${project.build.directory}/classes</classesDirectory>
                            <finalName>pkg-test</finalName>
                            <outputDirectory>${project.build.directory}/${project.build.finalName}/WEB-INF/lib
                            </outputDirectory>
                            <includes>
                                <include>com/**</include>
                            </includes>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
```


**2.build all files to a war**
```
<plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-war-plugin</artifactId>
                <version>3.3.1</version>
                <configuration>
                    <archive>
                        <manifest>
                            <addDefaultImplementationEntries>false</addDefaultImplementationEntries>
                            <addDefaultSpecificationEntries>false</addDefaultSpecificationEntries>
                        </manifest>
                    </archive>
                    <packagingExcludes>
                        WEB-INF/classes/com/**,WEB-INF/classes/application-local.yml
                    </packagingExcludes>
                </configuration>
            </plugin>
```

**Notes:** The compiler jdk version must be same with runnning jdk version in liberty!

### Why springboot2 project doesn't need a pom.xml, because the function is provided by servlet-3.1 and must add configure in main class

```
@Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(PkgApplication.class);
  }
```

### Enable log4j2

**1.Put log4j2.xml to src/resources**

**2.Append the code to application.yml**
```
logging.config: classpath:log4j2-prod.xml
```

**3.Update pom.xml**

```
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
            <exclusions>
                <exclusion>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-logging</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-log4j2</artifactId>
            <exclusions>
                <exclusion>
                    <groupId>org.slf4j</groupId>
                    <artifactId>jcl-over-slf4j</artifactId>
                </exclusion>
                <exclusion>
                    <groupId>org.slf4j</groupId>
                    <artifactId>jul-to-slf4j</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
```

### security https

### share session with spring session

