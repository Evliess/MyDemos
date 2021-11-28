package wait.play.pipeline.pipeline;

import org.springframework.stereotype.Service;

@Service
public class LogService {
  public void updateBuildLogContext(String runId) {
//    StringBuffer subRouting = new StringBuffer(LogConstants.PIPELINE_BUILD_ROUTING);
//    subRouting.append(KeterLogConstants.UNDERLINE).append(buildId);
//
//    ThreadContext.put(KeterLogConstants.ROUTING_KEY, subRouting.toString());
//    Thread.currentThread().setName(subRouting.toString());
//
//    String logPath = getBuildLogFilePath(pipelineId, buildId);
//
//    ThreadContext.put(KeterLogConstants.LOG_FILE_NAME_KEY, logPath);
  }
}
