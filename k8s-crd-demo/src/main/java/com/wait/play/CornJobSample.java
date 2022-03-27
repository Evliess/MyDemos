package com.wait.play;

import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.batch.v1.CronJob;
import io.fabric8.kubernetes.api.model.batch.v1.CronJobBuilder;
import io.fabric8.kubernetes.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CornJobSample {
  private static Logger logger = LoggerFactory.getLogger(CornJobSample.class);

  public static void main(String[] args) {
    final ConfigBuilder configBuilder = new ConfigBuilder();
    Config config = configBuilder.withMasterUrl(Constants.MASTER_URL).build();
    try (KubernetesClient client = new DefaultKubernetesClient(config)) {
      CronJob cronJob1 = new CronJobBuilder()
          .withApiVersion("batch/v1")
          .withNewMetadata()
          .withName("hello")
          .withLabels(Collections.singletonMap("foo", "bar"))
          .endMetadata()
          .withNewSpec()
          .withSchedule("*/1 * * * *")
          .withNewJobTemplate()
          .withNewSpec()
          .withNewTemplate()
          .withNewSpec()
          .addNewContainer()
          .withName("hello")
          .withImage("busybox:latest")
          .withArgs("/bin/sh", "-c", "date; echo Hello from Kubernetes")
          .endContainer()
          .withRestartPolicy("OnFailure")
          .endSpec()
          .endTemplate()
          .endSpec()
          .endJobTemplate()
          .endSpec()
          .build();
      cronJob1 = client.batch().v1().cronjobs().inNamespace(Constants.NS_DEFAULT).create(cronJob1);
      logger.info("Successfully created cronjob with name ", cronJob1.getMetadata().getName());
      final CountDownLatch watchLatch = new CountDownLatch(1);
      try (Watch watch = client.pods().inNamespace(Constants.NS_DEFAULT).withLabel("job-name").watch(new Watcher<Pod>() {
        @Override
        public void eventReceived(Action action, Pod aPod) {
          logger.info("{}, {}", aPod.getMetadata().getName(), aPod.getStatus().getPhase());
          if (aPod.getStatus().getPhase().equals("Succeeded")) {
//            logger.info("Logs -> {}", client.pods().inNamespace(Constants.NS_DEFAULT).withName(aPod.getMetadata().getName()).getLog());
            watchLatch.countDown();
          }
        }

        @Override
        public void onClose(WatcherException e) {
          // Ignore
        }
      })) {
        watchLatch.await(3, TimeUnit.MINUTES);
      } catch (KubernetesClientException | InterruptedException e) {
        logger.error("Could not watch pod", e);
      }
    } catch (KubernetesClientException exception) {
      logger.error("An error occured while processing cronjobs:", exception.getMessage());
      exception.printStackTrace();
    }

  }

}
