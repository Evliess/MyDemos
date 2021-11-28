package wait.play.pipeline.pipeline;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PipelineController {

  private Logger logger = LoggerFactory.getLogger(PipelineController.class);

  @GetMapping("/greeting")
  public String greeting() {
    logger.info("Thread name: {}", Thread.currentThread().getName());
    return "greeting!" + Thread.currentThread().getName();
  }
}
