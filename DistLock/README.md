

```
docker run --name redis -d -p 6379:6379 redis:latest
docker exec -it redis bash
redis-cli
ping
```