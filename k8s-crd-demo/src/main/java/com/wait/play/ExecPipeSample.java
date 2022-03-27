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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
//Error to Execute!!!!!!!!!!!!!!
public class ExecPipeSample {
  private static Logger logger = LoggerFactory.getLogger(ExecPipeSample.class);
  private static final String POD_NAME = "pod-example";

  public static void main(String[] args) {
    ConfigBuilder configBuilder = new ConfigBuilder().withMasterUrl(Constants.MASTER_URL);
    Config config = configBuilder.build();
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    try (KubernetesClient client = new DefaultKubernetesClient(config);
    ExecWatch watch = client.pods().withName(POD_NAME)
        .redirectingInput()
        .redirectingOutput()
        .redirectingError()
        .redirectingErrorChannel()
        .usingListener(new SimpleListener())
        .exec();
    ) {
      InputStreamPumper.pump(watch.getOutput(), (b, o, l) -> System.out.print(new String(b, o, l)),
          executorService);
      watch.getInput().write("ls -al\n".getBytes());
      Thread.sleep(5 * 1000L);
    } catch (Exception e) {
      logger.error("Error!", e);
    } finally {
      executorService.shutdown();
    }
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
