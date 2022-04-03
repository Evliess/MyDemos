package com.wait.play;

import com.wait.play.model.Dummy;
import com.wait.play.model.DummySpec;
import io.fabric8.kubernetes.api.model.HasMetadata;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.informers.ResourceEventHandler;
import io.fabric8.kubernetes.client.informers.SharedIndexInformer;
import io.fabric8.kubernetes.client.informers.SharedInformerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CustomResourceInformerSample {
  private static Logger logger = LoggerFactory.getLogger(CustomResourceInformerSample.class);

  public static void main(String[] args) {
    final ConfigBuilder configBuilder = new ConfigBuilder().withMasterUrl(Constants.MASTER_URL);
    Config config  = configBuilder.build();
    try (KubernetesClient client = new DefaultKubernetesClient(config)) {
      SharedInformerFactory sharedInformerFactory = client.informers();
      SharedIndexInformer<Dummy> podInformer = sharedInformerFactory.sharedIndexInformerFor(Dummy.class, 60 * 1000L);
      logger.info("Informer factory initialized.");
      podInformer.addEventHandler(
          new ResourceEventHandler<Dummy>() {
            @Override
            public void onAdd(Dummy pod) {
              logger.info("{} dummy added", pod.getMetadata().getName());
            }

            @Override
            public void onUpdate(Dummy oldPod, Dummy newPod) {
              logger.info("{} dummy updated", oldPod.getMetadata().getName());
            }

            @Override
            public void onDelete(Dummy pod, boolean deletedFinalStateUnknown) {
              logger.info("{} dummy deleted", pod.getMetadata().getName());
            }
          }
      );

      sharedInformerFactory.addSharedInformerEventListener(ex ->
          logger.error("Exception occurred, but caught: {}", ex.getMessage()));

      logger.info("Starting all registered informers");
      sharedInformerFactory.startAllRegisteredInformers();

      Executors.newSingleThreadExecutor().submit(() -> {
        Thread.currentThread().setName("HAS_SYNCED_THREAD");
        try {
          for (;;) {
            logger.info("podInformer.hasSynced() : {}", podInformer.hasSynced());
            Thread.sleep(10 * 1000L);
          }
        } catch (InterruptedException inEx) {
          Thread.currentThread().interrupt();
          logger.warn("HAS_SYNCED_THREAD interrupted: {}", inEx.getMessage());
        }
      });

      final Dummy toCreate = new Dummy();
      toCreate.getMetadata().setName("dummy");
      DummySpec spec = new DummySpec();
      spec.setFoo("Hello");
      spec.setBar("Dummy");
      toCreate.setSpec(spec);
      if (client.getConfiguration().getNamespace() != null) {
        toCreate.getMetadata().setNamespace(client.getConfiguration().getNamespace());
      } else if (client.getNamespace() != null) {
        toCreate.getMetadata().setNamespace(client.getNamespace());
      } else {
        toCreate.getMetadata().setNamespace(client.namespaces().list().getItems().stream().findFirst()
            .map(HasMetadata::getMetadata).map(ObjectMeta::getNamespace).orElse("default"));
      }

      client.resources(Dummy.class).createOrReplace(toCreate);
      // Wait for some time now
      TimeUnit.MINUTES.sleep(5);

    }catch (Exception e) {
      logger.error("Failed with exception", e);
    }
  }

}
