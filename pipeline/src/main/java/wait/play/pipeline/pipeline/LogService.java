package wait.play.pipeline.pipeline;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.stereotype.Service;

@Service
public class LogService {
  public void updateBuildLogContext(String runId) {
    ThreadContext.put(LogConstants.ROUTING_KEY, runId);
    Thread.currentThread().setName(runId);
  }
  public void clearBuildLogContext(String runId) {
    ThreadContext.remove(LogConstants.ROUTING_KEY);
  }
}
