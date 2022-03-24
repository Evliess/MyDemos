package com.wait.play;

import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.dsl.ExecListener;
import io.fabric8.kubernetes.client.dsl.ExecWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.tokens.ScalarToken;

public class ExecWithTerminalSizeSample {
  private static Logger logger = LoggerFactory.getLogger(ExecWithTerminalSizeSample.class);

  public static void main(String[] args) throws InterruptedException {
    String columns = "80";
    String lines = "24";

    final ConfigBuilder configBuilder = new ConfigBuilder();
    configBuilder.withMasterUrl(Constants.MASTER_URL);
    Config config = configBuilder.build();
    try (
        KubernetesClient client = new DefaultKubernetesClient(config);
        ExecWatch ignore = newExecWatch(client, "default", "pod-example", columns, lines);
    ) {
      Thread.sleep(10 * 1000L);
    }
  }

  private static ExecWatch newExecWatch(KubernetesClient client, String namespace, String podName, String columns, String lines) {
    return client.pods().inNamespace(namespace).withName(podName)
        .readingInput(System.in)
        .writingOutput(System.out)
        .writingError(System.err)
//        .withTTY()
        .usingListener(new SimpleListener())
        .exec("env", "TERM=xterm", "COLUMNS=" + columns, "LINES=" + lines, "sh", "-c", "df -h");
  }

  private static class SimpleListener implements ExecListener {

    @Override
    public void onOpen() {
      logger.info("The shell will remain open for 10 seconds.");
    }

    @Override
    public void onFailure(Throwable t, Response failureResponse) {
      logger.error("Some error encountered");
    }

    @Override
    public void onClose(int code, String reason) {
      logger.info("The shell will now close.");
    }
  }
}


