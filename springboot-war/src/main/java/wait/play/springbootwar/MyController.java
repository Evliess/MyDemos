package wait.play.springbootwar;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
public class MyController {

  @GetMapping(value = "hello")
  public String hello() {
    File file = new File(this.getClass().getClassLoader().getResource("config/test.conf").getFile());
    return file.exists() + ":" + System.currentTimeMillis();
  }
}
