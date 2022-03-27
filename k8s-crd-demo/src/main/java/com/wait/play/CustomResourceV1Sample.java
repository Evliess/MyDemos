package com.wait.play;

import io.fabric8.kubernetes.api.model.Namespaced;
import io.fabric8.kubernetes.api.model.ObjectMetaBuilder;
import io.fabric8.kubernetes.api.model.apiextensions.v1.CustomResourceDefinition;
import io.fabric8.kubernetes.api.model.apiextensions.v1.JSONSchemaPropsBuilder;
import io.fabric8.kubernetes.client.*;
import io.fabric8.kubernetes.client.dsl.NonNamespaceOperation;
import io.fabric8.kubernetes.client.dsl.Resource;
import io.fabric8.kubernetes.client.dsl.base.CustomResourceDefinitionContext;
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class CustomResourceV1Sample {
  private static Logger logger = LoggerFactory.getLogger(CustomResourceV1Sample.class);

  public static void main(String[] args) {
    final ConfigBuilder configBuilder = new ConfigBuilder().withMasterUrl(Constants.MASTER_URL);
    Config config = configBuilder.build();
    try (KubernetesClient client = new DefaultKubernetesClient(config)){
      final CustomResourceDefinition crd = CustomResourceDefinitionContext.v1CRDFromCustomResourceType(Show.class)
          .editSpec().editVersion(0)
          .withNewSchema().withNewOpenAPIV3Schema()
          .withTitle("Shows")
          .withType("object")
          .addToRequired("spec")
          .addToProperties("spec", new JSONSchemaPropsBuilder()
              .withType("object")
              .addToProperties("name", new JSONSchemaPropsBuilder().withType("string").build())
              .addToProperties("score", new JSONSchemaPropsBuilder().withType("number").build())
              .build())
          .endOpenAPIV3Schema().endSchema()
          .endVersion().endSpec().build();
      // @formatter:on
      client.apiextensions().v1().customResourceDefinitions().createOrReplace(crd);
      System.out.println("Created custom shows.example.com Kubernetes API");
      final NonNamespaceOperation<Show, ShowList, Resource<Show>> shows =
          client.customResources(Show.class, ShowList.class)
              .inNamespace("default");
      shows.list();
      shows.createOrReplace(new Show("breaking-bad", new ShowSpec("Breaking Bad", 10)));
      shows.createOrReplace(new Show("better-call-saul", new ShowSpec("Better call Saul", 8)));
      shows.createOrReplace(new Show("the-wire", new ShowSpec("The Wire", 10)));
      System.out.println("Added three shows");
      shows.list().getItems()
          .forEach(s -> System.out.printf(" - %s%n", s.getSpec().name));
      final Show theWire = shows.withName("the-wire").fromServer().get();
      System.out.printf("The Wire Score is: %s%n", theWire.getSpec().score);
    } catch (Exception e) {
      logger.error("Error!", e);
    }

  }

  @Group("example.com")
  @Version("v1")
  public static final class Show extends CustomResource<ShowSpec, Void> implements Namespaced {

    @SuppressWarnings("unused")
    public Show() {
      super();
    }
    public Show(String metaName, ShowSpec spec) {
      setMetadata(new ObjectMetaBuilder().withName(metaName).build());
      setSpec(spec);
    }
  }

  public static final class ShowList extends CustomResourceList<Show> {}

  @SuppressWarnings("unused")
  public static final class ShowSpec implements Serializable {

    private static final long serialVersionUID = -1548881019086449848L;

    private String name;
    private Number score;

    public ShowSpec() {
      super();
    }

    public ShowSpec(String name, int score) {
      this.name = name;
      this.score = score;
    }

    public String getName() {
      return name;
    }

    public Number getScore() {
      return score;
    }

    public void setName(String name) {
      this.name = name;
    }

    public void setScore(Number score) {
      this.score = score;
    }
  }
}
