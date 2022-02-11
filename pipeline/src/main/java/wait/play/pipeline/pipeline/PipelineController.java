package wait.play.pipeline.pipeline;

import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import wait.play.pipeline.quartz.SampleJob;

import java.util.UUID;

@RestController
public class PipelineController {

  private Logger logger = LoggerFactory.getLogger(PipelineController.class);

  @Autowired
  private LogService logService;

  @Autowired
  private SampleJob sampleJob;

  @GetMapping("/greeting")
  public String greeting() {
    String uuid = UUID.randomUUID().toString();
    logService.updateBuildLogContext(uuid);
    logger.info("Thread name: {}", Thread.currentThread().getName());
    logService.clearBuildLogContext(uuid);
    return "greeting!" + Thread.currentThread().getName();
  }
}
