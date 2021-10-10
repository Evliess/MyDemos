package wait.play.p6.DistLock;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

  @GetMapping("/books")
  public String sellBook() {
    return "001";
  }
}
