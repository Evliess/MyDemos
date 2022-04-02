package com.wait.play;


import io.fabric8.kubernetes.api.model.ListOptionsBuilder;
import io.fabric8.kubernetes.api.model.PodList;
import io.fabric8.kubernetes.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class ClientSample {
  private static Logger logger = LoggerFactory.getLogger(ClientSample.class);

  public static void main(String[] args) {
    final ConfigBuilder configBuilder = new ConfigBuilder();
    configBuilder.withMasterUrl(Constants.MASTER_URL);
    Config config = configBuilder.build();


    try (KubernetesClient client = new DefaultKubernetesClient(config)) {
      final String namespace = Optional.ofNullable(client.getNamespace()).orElse("default");

      PodList podList = client.pods().inNamespace(namespace).list(new ListOptionsBuilder().withLimit(5L).build());
      podList.getItems().forEach(obj -> logger.info(obj.getMetadata().getName()));

      podList = client.pods().inNamespace(namespace)
          .list(new ListOptionsBuilder().withLimit(5L).withContinue(podList.getMetadata().getContinue()).build());
      podList.getItems().forEach(obj -> logger.info(obj.getMetadata().getName()));

      Integer services = client.services().inNamespace(namespace)
          .list(new ListOptionsBuilder().withLimit(1L).build()).getItems().size();

      client.services().inNamespace(namespace).list(new ListOptionsBuilder().withLimit(1L).withContinue(null).build());
      logger.info(services.toString());
    } catch (KubernetesClientException e) {
      logger.error(e.getMessage(), e);
    }
  }


}

