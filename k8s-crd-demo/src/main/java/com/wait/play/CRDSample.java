package com.wait.play;

import com.wait.play.model.Dummy;
import com.wait.play.model.DummyList;
import com.wait.play.model.DummySpec;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.apiextensions.v1.CustomResourceDefinition;
import io.fabric8.kubernetes.api.model.apiextensions.v1.CustomResourceDefinitionList;
import io.fabric8.kubernetes.api.model.apiextensions.v1.JSONSchemaPropsBuilder;
import io.fabric8.kubernetes.client.*;
import io.fabric8.kubernetes.client.dsl.MixedOperation;
import io.fabric8.kubernetes.client.dsl.NonNamespaceOperation;
import io.fabric8.kubernetes.client.dsl.Resource;
import io.fabric8.kubernetes.client.dsl.base.CustomResourceDefinitionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class CRDSample {
  private static Logger logger = LoggerFactory.getLogger(CRDSample.class);

  public static void main(String[] args) {
    final ConfigBuilder configBuilder = new ConfigBuilder();
    configBuilder.withMasterUrl(Constants.MASTER_URL);

    try (KubernetesClient client = new DefaultKubernetesClient(configBuilder.build())) {
      CustomResourceDefinitionList crds = client.apiextensions().v1().customResourceDefinitions().list();
      List<CustomResourceDefinition> crdsItems = crds.getItems();
      logger.info("Found " + crdsItems.size() + " CRD(s)");
      CustomResourceDefinition dummyCRD = null;
      final String dummyCRDName = CustomResource.getCRDName(Dummy.class);
      for (CustomResourceDefinition crd : crdsItems) {
        ObjectMeta metadata = crd.getMetadata();
        if (metadata != null) {
          String name = metadata.getName();
          logger.info("    " + name + " => " + metadata.getSelfLink());
          if (dummyCRDName.equals(name)) {
            dummyCRD = crd;
          }
        }
      }
      if (dummyCRD != null) {
        logger.info("Found CRD: " + dummyCRD.getMetadata().getSelfLink());
      } else {
        dummyCRD = CustomResourceDefinitionContext.v1CRDFromCustomResourceType(Dummy.class)
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
        logger.info("Created CRD " + dummyCRD.getMetadata().getName());

        // wait a beat for the endpoints to be ready
        Thread.sleep(5000);
        logger.info("===========================>");
        // lets create a client for the CRD
        NonNamespaceOperation<Dummy, DummyList, Resource<Dummy>> dummyClient = client.resources(Dummy.class, DummyList.class);
        boolean resourceNamespaced = true;
        if (resourceNamespaced) {
          dummyClient = ((MixedOperation<Dummy, DummyList, Resource<Dummy>>) dummyClient).inNamespace("default");
        }
        CustomResourceList<Dummy> dummyList = dummyClient.list();
        List<Dummy> items = dummyList.getItems();
        logger.info("  found " + items.size() + " dummies");
        for (Dummy item : items) {
          logger.info("    " + item);
        }

        Dummy dummy = new Dummy();
        ObjectMeta metadata = new ObjectMeta();
        metadata.setName("foo");
        dummy.setMetadata(metadata);
        DummySpec dummySpec = new DummySpec();
        Date now = new Date();
        dummySpec.setBar("beer: " + now);
        dummySpec.setFoo("cheese: " + now);
        dummy.setSpec(dummySpec);

        Dummy created = dummyClient.createOrReplace(dummy);

        logger.info("Upserted " + dummy);

        created.getSpec().setBar("otherBar");

        dummyClient.createOrReplace(created);

        logger.info("Watching for changes to Dummies");
        dummyClient.withResourceVersion(created.getMetadata().getResourceVersion()).watch(new Watcher<Dummy>() {
          @Override
          public void eventReceived(Action action, Dummy resource) {
            logger.info("==> " + action + " for " + resource);
            if (resource.getSpec() == null) {
              logger.error("No Spec for resource {}", resource);
            }
          }

          @Override
          public void onClose(WatcherException cause) {
          }
        });

        System.in.read();
      }
    } catch (KubernetesClientException e) {
      logger.error(e.getMessage(), e);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
