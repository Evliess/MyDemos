package wait.play.p6.DistLock;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController
public class BookController {

  private static final String BOOKS = "books";

  @Resource
  private StringRedisTemplate stringRedisTemplate;

  @Autowired
  private RedissonClient redissonClient;

  @GetMapping("/books/sell")
  public String sellOneBook() {
    String left;
    Long num = 0l;
    RLock rLock = redissonClient.getLock("myLock");
    rLock.lock(1, TimeUnit.SECONDS);
    ValueOperations<String, String> forValue = stringRedisTemplate.opsForValue();
    if (Integer.valueOf(forValue.get(BOOKS)) > 0) {
      num = forValue.decrement(BOOKS, 1);
    }
    left = num == 0 ? "0 left!" : num.toString();
    System.out.println("Book left number: " + left);
    rLock.unlock();
    return left;
  }

  @GetMapping("/books/onshore")
  public String onShore() {
    ValueOperations<String, String> forValue = stringRedisTemplate.opsForValue();
    forValue.set(BOOKS, "200");
    return "200";
  }
}
