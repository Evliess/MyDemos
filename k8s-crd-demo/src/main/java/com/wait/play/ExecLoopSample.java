package com.wait.play;

import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.dsl.ExecListener;
import io.fabric8.kubernetes.client.dsl.ExecWatch;
import io.fabric8.kubernetes.client.utils.InputStreamPumper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class ExecLoopSample {
  private static Logger logger = LoggerFactory.getLogger(ExecLoopSample.class);
  private static final String POD_NAME = "pod-example";

  public static void main(String[] args) {
    ConfigBuilder configBuilder = new ConfigBuilder().withMasterUrl(Constants.MASTER_URL);
    Config config = configBuilder.build();
    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(20);

    try (KubernetesClient client = new DefaultKubernetesClient(config)) {
      for (int i = 0; i < 10; logger.info("i=" + i), i++) {
        ExecWatch watch = null;
        CompletableFuture<?> pump = null;
        final CountDownLatch latch = new CountDownLatch(1);
        watch = client.pods().inNamespace(Constants.NS_DEFAULT).withName(POD_NAME).redirectingOutput().
            usingListener(new ExecListener() {
              @Override
              public void onOpen() {
                logger.info("Open");
              }

              @Override
              public void onFailure(Throwable t, Response failureResponse) {
                latch.countDown();
              }

              @Override
              public void onClose(int code, String reason) {
                latch.countDown();
              }
            }).exec("date");
        pump = InputStreamPumper.pump(watch.getOutput(), (b, o, l) -> logger.info(new String(b, o, l)),
            executorService);
        executorService.scheduleAtFixedRate(new FutureChecker("Pump " + (i + 1), pump), 0, 2, TimeUnit.SECONDS);

        latch.await(5, TimeUnit.SECONDS);
        watch.close();
        pump.cancel(true);

      }
    } catch (Exception e) {
      logger.error("Error!", e);
    }
    executorService.shutdown();
    logger.info("Done.");
  }

  private static class FutureChecker implements Runnable {
    private final String name;
    private final Future<?> future;

    private FutureChecker(String name, Future<?> future) {
      this.name = name;
      this.future = future;
    }

    @Override
    public void run() {
      if (!future.isDone()) {
        logger.info("Future:[" + name + "] is not done yet");
      }
    }
  }


}

