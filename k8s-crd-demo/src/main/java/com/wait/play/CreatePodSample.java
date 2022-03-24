package com.wait.play;

import io.fabric8.kubernetes.api.model.HasMetadata;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodList;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.dsl.NonNamespaceOperation;
import io.fabric8.kubernetes.client.dsl.PodResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.util.List;

public class CreatePodSample {
  private static Logger logger = LoggerFactory.getLogger(CreatePodSample.class);


  private static final String fileName = "src/test/resources/pod/busybox.yml";
  public static void main(String[] args) {
    final ConfigBuilder configBuilder = new ConfigBuilder();
    configBuilder.withMasterUrl(Constants.MASTER_URL);
    Config config = configBuilder.build();

    try (KubernetesClient client = new DefaultKubernetesClient(config)) {
      List<HasMetadata> resources = client.load(new FileInputStream(fileName)).get();
      if (resources.isEmpty()) {
        logger.error("No resources loaded from file: {}", fileName);
        return;
      }
      HasMetadata resource = resources.get(0);
      if (resource instanceof Pod){
        Pod pod = (Pod) resource;
        logger.info("Creating pod in namespace {}", "default");
        NonNamespaceOperation<Pod, PodList, PodResource<Pod>> pods = client.pods().inNamespace("default");
        Pod result = pods.create(pod);
        logger.info("Created pod {}", result.getMetadata().getName());

        Thread.sleep(30 * 1000);

        pods.delete(pod);
        logger.info("Delete pod {} after 30 sec", result.getMetadata().getName());

      } else {
        logger.error("Loaded resource is not a Pod! {}", resource);
      }
    }catch (Exception e) {
      logger.error("Error!", e);
    }

  }
}
