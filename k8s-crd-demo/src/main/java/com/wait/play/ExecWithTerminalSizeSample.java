package com.wait.play;

import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.dsl.ExecListener;
import io.fabric8.kubernetes.client.dsl.ExecWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExecWithTerminalSizeSample {
  private static Logger logger = LoggerFactory.getLogger(ExecWithTerminalSizeSample.class);
  private static final String MASTER_URL = "http://192.168.88.128:8001";
  private static final String CLIENT_CERT_FILE = "D:\\Ubuntu\\client.crt";
  private static final String CLIENT_KEY_FILE = "D:\\Ubuntu\\client.key";
  private static final String KUBECONFIG = "D:\\Ubuntu\\config";
  public static void main(String[] args) throws InterruptedException {
    String columns = "80";
    String lines = "24";

    final ConfigBuilder configBuilder = new ConfigBuilder();
    configBuilder.withMasterUrl(MASTER_URL);
    Config config = configBuilder.build();
    config.setClientCertFile(CLIENT_CERT_FILE);
    config.setClientKeyFile(CLIENT_KEY_FILE);


    try (
        KubernetesClient client = new DefaultKubernetesClient(config);
        ExecWatch ignore = newExecWatch(client, "default", "nginx-deployment-9456bbbf9-gb6zt", columns, lines);
    ) {
      Thread.sleep(10 * 1000L);
    }
  }

  private static ExecWatch newExecWatch(KubernetesClient client, String namespace, String podName, String columns, String lines) {
    return client.pods().inNamespace(namespace).withName(podName)
        .readingInput(System.in)
        .writingOutput(System.out)
        .writingError(System.err)
        .withTTY()
        .usingListener(new SimpleListener())
        .exec("env", "TERM=xterm", "COLUMNS=" + columns, "LINES=" + lines, "sh", "-c", "ls -la");
  }

  private static class SimpleListener implements ExecListener {

    @Override
    public void onOpen() {
      logger.info("The shell will remain open for 10 seconds.");
    }

    @Override
    public void onFailure(Throwable t, Response failureResponse) {
      logger.error("shell barfed");
    }

    @Override
    public void onClose(int code, String reason) {
      logger.info("The shell will now close.");
    }
  }
}


