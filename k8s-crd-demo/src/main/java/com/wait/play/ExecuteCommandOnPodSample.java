package com.wait.play;

import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.dsl.ExecListener;
import io.fabric8.kubernetes.client.dsl.ExecWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class ExecuteCommandOnPodSample {
  private static Logger logger = LoggerFactory.getLogger(ExecuteCommandOnPodSample.class);
  private static final String POD_NAME = "pod-example";
  public static void main(String[] args) {
    final ConfigBuilder configBuilder = new ConfigBuilder();
    configBuilder.withMasterUrl(Constants.MASTER_URL);

    Config config = configBuilder.build();
    try (final KubernetesClient client = new DefaultKubernetesClient(config)) {

      Pod pod = client.pods().inNamespace("default").withName(POD_NAME).get();
      logger.info("Running command: {} on pod {} in namespace {}\n",
          Arrays.toString("df -h".split(" ")), pod.getMetadata().getName(), "default");

      CompletableFuture<String> data = new CompletableFuture<>();
      try (ExecWatch execWatch = execCmd(client, pod, data, "df -h".split(" "))) {
        logger.info(data.get(10, TimeUnit.SECONDS));
      }
    } catch (Exception e) {
      logger.error("Error!", e);
    }

  }

  private static ExecWatch execCmd(KubernetesClient client, Pod pod, CompletableFuture<String> data, String... command) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    return client.pods()
        .inNamespace(pod.getMetadata().getNamespace())
        .withName(pod.getMetadata().getName())
        .writingOutput(baos)
        .writingError(baos)
        .usingListener(new SimpleListener(data, baos))
        .exec(command);
  }

  static class SimpleListener implements ExecListener {

    private CompletableFuture<String> data;
    private ByteArrayOutputStream baos;

    public SimpleListener(CompletableFuture<String> data, ByteArrayOutputStream baos) {
      this.data = data;
      this.baos = baos;
    }

    @Override
    public void onOpen() {
      logger.info("Reading data... ");
    }

    @Override
    public void onFailure(Throwable t, Response failureResponse) {
      logger.error(t.getMessage());
      data.completeExceptionally(t);
    }

    @Override
    public void onClose(int code, String reason) {
      logger.info("Exit with: " + code + " and with reason: " + reason);
      data.complete(baos.toString());
    }
  }

}


