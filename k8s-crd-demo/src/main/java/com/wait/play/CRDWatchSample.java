package com.wait.play;

import io.fabric8.kubernetes.api.builder.Visitor;
import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.api.model.ConfigMapBuilder;
import io.fabric8.kubernetes.api.model.ObjectMetaBuilder;
import io.fabric8.kubernetes.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.UUID;

public class CRDWatchSample {
  private static Logger logger = LoggerFactory.getLogger(CRDWatchSample.class);

  public static void main(String[] args) {
    final ConfigBuilder configBuilder = new ConfigBuilder();
    configBuilder.withMasterUrl(Constants.MASTER_URL);
    Config config = configBuilder.build();
    try (
        KubernetesClient client = new DefaultKubernetesClient(config);
        Watch ignored = newConfigMapWatch(client)
    ) {
      final String namespace = Optional.ofNullable(client.getNamespace()).orElse("default");
      final String name = "watch-config-map-test-" + UUID.randomUUID();
      final ConfigMap cm = client.configMaps().inNamespace(namespace).createOrReplace(new ConfigMapBuilder()
          .withNewMetadata().withName(name).endMetadata()
          .build()
      );
      client.configMaps().inNamespace(namespace).withName(name)
          .patch(new ConfigMapBuilder().withNewMetadata().withName(name).endMetadata().addToData("key", "value").build());
      //noinspection Convert2Lambda
      client.configMaps().inNamespace(namespace).withName(name).edit(new Visitor<ObjectMetaBuilder>() {
        public void visit(ObjectMetaBuilder omb) {
          omb.addToAnnotations("annotation", "value");
        }
      });
      client.configMaps().delete(cm);
    } catch (Exception e) {

    }
  }

  private static Watch newConfigMapWatch(KubernetesClient client) {
    return client.configMaps().watch(new Watcher<ConfigMap>() {
      @Override
      public void eventReceived(Action action, ConfigMap resource) {
        logger.info("Watch event received {}: {}", action.name(), resource.getMetadata().getName());
      }

      @Override
      public void onClose(WatcherException e) {
        logger.error("Watch error received: {}", e.getMessage(), e);
      }

      @Override
      public void onClose() {
        logger.info("Watch gracefully closed");
      }
    });
  }

}
