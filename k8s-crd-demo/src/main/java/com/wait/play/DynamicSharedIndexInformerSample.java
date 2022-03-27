package com.wait.play;

import com.sun.tools.internal.jxc.ap.Const;
import io.fabric8.kubernetes.api.model.GenericKubernetesResource;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.dsl.base.CustomResourceDefinitionContext;
import io.fabric8.kubernetes.client.informers.ResourceEventHandler;
import io.fabric8.kubernetes.client.informers.SharedIndexInformer;
import io.fabric8.kubernetes.client.informers.SharedInformerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class DynamicSharedIndexInformerSample {
  private static Logger logger = LoggerFactory.getLogger(DynamicSharedIndexInformerSample.class);

  public static void main(String[] args) {
    ConfigBuilder configBuilder = new ConfigBuilder().withMasterUrl(Constants.MASTER_URL);
    Config config = configBuilder.build();
    try (KubernetesClient client = new DefaultKubernetesClient(config)
        ) {
      SharedInformerFactory informerFactory = client.informers();
      CustomResourceDefinitionContext context = new CustomResourceDefinitionContext.Builder()
          .withGroup("demo.fabric8.io")
          .withVersion("v1")
          .withPlural("dummies")
          .withKind("Dummy")
          .withScope("Namespaced")
          .build();

      SharedIndexInformer<GenericKubernetesResource> informer = informerFactory.sharedIndexInformerForCustomResource(context, 60 * 1000L);
      informer.addEventHandler(new ResourceEventHandler<GenericKubernetesResource>() {
        @Override
        public void onAdd(GenericKubernetesResource genericKubernetesResource) {
          logger.info("ADD {}/{}", genericKubernetesResource.getMetadata().getNamespace(), genericKubernetesResource.getMetadata().getName());
        }

        @Override
        public void onUpdate(GenericKubernetesResource genericKubernetesResource, GenericKubernetesResource t1) {
          logger.info("UPDATE {}/{}", genericKubernetesResource.getMetadata().getNamespace(), genericKubernetesResource.getMetadata().getName());
        }

        @Override
        public void onDelete(GenericKubernetesResource genericKubernetesResource, boolean b) {
          logger.info("DELETE {}/{}", genericKubernetesResource.getMetadata().getNamespace(), genericKubernetesResource.getMetadata().getName());
        }
      });

      informerFactory.addSharedInformerEventListener(e -> logger.error(e.getMessage()));
      informerFactory.startAllRegisteredInformers();

      TimeUnit.MINUTES.sleep(10);
    } catch (Exception e) {
      logger.error("Error!", e);
    }
  }

}
