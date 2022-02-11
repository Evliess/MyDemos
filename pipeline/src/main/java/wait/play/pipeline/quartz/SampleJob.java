package wait.play.pipeline.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import wait.play.pipeline.pipeline.PipelineController;

@Component
public class SampleJob implements Job {
  private Logger logger = LoggerFactory.getLogger(SampleJob.class);
  @Override
  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    logger.info("Sample job");
  }
}
