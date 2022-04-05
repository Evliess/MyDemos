package com.wait.play.app01;

import com.wait.play.Constants;
import com.wait.play.model.Dummy;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodBuilder;
import io.fabric8.kubernetes.api.model.PodList;
import io.fabric8.kubernetes.api.model.apiextensions.v1.CustomResourceColumnDefinition;
import io.fabric8.kubernetes.api.model.apiextensions.v1.CustomResourceDefinition;
import io.fabric8.kubernetes.api.model.apiextensions.v1.CustomResourceDefinitionList;
import io.fabric8.kubernetes.api.model.apiextensions.v1.JSONSchemaPropsBuilder;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.dsl.NonNamespaceOperation;
import io.fabric8.kubernetes.client.dsl.PodResource;
import io.fabric8.kubernetes.client.dsl.base.CustomResourceDefinitionContext;
import io.fabric8.kubernetes.client.informers.ResourceEventHandler;
import io.fabric8.kubernetes.client.informers.SharedIndexInformer;
import io.fabric8.kubernetes.client.informers.SharedInformerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.concurrent.Executors;

public class CRDOperator {
  private static Logger logger = LoggerFactory.getLogger(CRDOperator.class);

  public static void main(String[] args) {
    final ConfigBuilder configBuilder = new ConfigBuilder();
    configBuilder.withMasterUrl(Constants.MASTER_URL);
    try {
      KubernetesClient client = new DefaultKubernetesClient(configBuilder.build());

//      KubernetesClient client = new DefaultKubernetesClient();
      CRDOperator crdOperator = new CRDOperator();
      // create crd directly
//      crdOperator.createCRD(client);
      // load crd from file
      crdOperator.loadCRDFromFile(client);
      crdOperator.informer(client);
    } catch (Exception e) {
      logger.error("Error!", e);
    }
  }

  private void createBusyboxPod(KubernetesClient client) {
    Pod busybox = new PodBuilder().withApiVersion("v1")
        .withNewMetadata()
        .withName("busybox")
        .withLabels(Collections.singletonMap("app", "busybox"))
        .endMetadata()
        .withNewSpec()
        .addNewContainer()
        .withName("busybox")
        .withImage("busybox:latest")
        .withArgs("/bin/sh", "-c", "sleep 10; touch /tmp/healthy; sleep 30000")
        .endContainer()
        .endSpec()
        .build();
    NonNamespaceOperation<Pod, PodList, PodResource<Pod>> pods = client.pods().inNamespace(Constants.NS_DEFAULT);
    pods.create(busybox);
  }

  public void informer(KubernetesClient client) {
    SharedInformerFactory sharedInformerFactory = client.informers();
    SharedIndexInformer<Dummy> podInformer = sharedInformerFactory.sharedIndexInformerFor(Dummy.class, 60 * 1000L);
    logger.info("Informer factory initialized.");
    podInformer.addEventHandler(
        new ResourceEventHandler<Dummy>() {
          @Override
          public void onAdd(Dummy pod) {
            logger.info("{} dummy added", pod.getMetadata().getName());
//            createBusyboxPod(client);
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
        for (; ; ) {
          logger.info("podInformer.hasSynced() : {}", podInformer.hasSynced());
          Thread.sleep(10 * 1000L);
        }
      } catch (InterruptedException inEx) {
        Thread.currentThread().interrupt();
        logger.warn("HAS_SYNCED_THREAD interrupted: {}", inEx.getMessage());
      }
    });
  }

  public void loadCRDFromFile(KubernetesClient client) throws FileNotFoundException {
    CustomResourceDefinition aCustomResourceDefinition = client.apiextensions().v1().customResourceDefinitions()
        .load(new FileInputStream("patterns/dummy-crd.yaml")).get();
    logger.info("Creating CRD...");
    client.apiextensions().v1().customResourceDefinitions().create(aCustomResourceDefinition);
    logger.info("Created CRD...");
  }

  public void createCRD(KubernetesClient client) {
    CustomResourceDefinition dummyCRD = CustomResourceDefinitionContext.v1CRDFromCustomResourceType(Dummy.class)
        .editSpec()
        .editVersion(0)
        .withNewSchema()
        .withNewOpenAPIV3Schema()
        .withTitle("dummy")
        .withType("object")
        .addToRequired("spec")
        .addToProperties("spec", new JSONSchemaPropsBuilder()
            .withType("object")
            .addToProperties("foo", new JSONSchemaPropsBuilder().withType("string").build())
            .addToProperties("bar", new JSONSchemaPropsBuilder().withType("string").build())
            .build())
        .endOpenAPIV3Schema()
        .endSchema()
        .endVersion()
        .endSpec()
        .build();
    client.apiextensions().v1().customResourceDefinitions().create(dummyCRD);
    CustomResourceDefinitionList crds = client.apiextensions().v1().customResourceDefinitions().list();
    crds.getItems().forEach(crd -> logger.info(crd.getMetadata().getName()));
  }

}
