

## Redis
```
docker run --name redis -d -p 6379:6379 redis:latest
docker exec -it redis bash
redis-cli
ping
```
## Nginx

1. Edit Dockerfile
2. Update nginx.conf
```
#gzip  on;
upstream myapp {
    server host.docker.internal:8080 weight=1;
    server host.docker.internal:8090 weight=1;
}
```
3. Update default.conf
```
location / {
    root   /usr/share/nginx/html;
    index  index.html index.htm;
    proxy_pass http://myapp;
}
```
4. Start nginx
```
docker build . -t mynginx:latest
docker run --name nginx -d -p 9080:80 mynginx:latest
```

## Jmeter

Prerequisites: Configure JAVA_HOME

```
1. Double click <YOUR_JMETER_HOME>/bin/jmeter.bat
2. Right click on Test Plan --> Add --> Threads(User) --> Thread Group
3. Configure Number of Thread(users), Ramp-up period(seconds), Loop Count
4. Right click on Thread Group --> Add --> Sampler --> HTTP Request
5. In the Http Request Section, setup Path field
6. Righr click on Http Request, Add --> Listener --> Aggregate Report 

```


