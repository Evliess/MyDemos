package wait.play;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class MyController {

  private Logger logger = LoggerFactory.getLogger(MyController.class);

  @Autowired
  private RedisTemplate redisTemplate;

  @RequestMapping(value = "/greeting", method = RequestMethod.POST)
  public String greeting(@RequestParam String key, @RequestParam String value) {
    if (null == value || value.isEmpty()) {
      value = System.currentTimeMillis() + "";
    }
    if (key == null || key.isEmpty()) {
      key = "hello";
    }
    redisTemplate.opsForValue().set(key, value);
    logger.info("{ {}:{} }", key, value);
    return "greeting! {" + key + " : " + value + "}";
  }

  @RequestMapping(value = "/hello/{name}", method = RequestMethod.GET)
  public String hello(@PathVariable String name) {
    String value = redisTemplate.opsForValue().get(name).toString();
    logger.info("{}:{}", name, value);
    return "hello! {" + name + " : " + value + "}";
  }
}
