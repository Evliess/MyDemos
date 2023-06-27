#Tips

```shell
git config --global http.proxy socks5://127.0.0.1:7890
git config --global --unset http.proxy
```


##How to package

```

mvn clean compile assembly:single

```

##How to execute?

```

java -cp ./my-app-1.0-SNAPSHOT .
 
```
