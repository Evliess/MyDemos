package com.wait.play.model;

import io.fabric8.kubernetes.api.model.Namespaced;
import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Plural;
import io.fabric8.kubernetes.model.annotation.Version;

@Version(Dummy.VERSION)
@Group(Dummy.GROUP)
@Plural("dummys")
public class Dummy extends CustomResource<DummySpec, Void> implements Namespaced {
  public static final String GROUP = "wait.play.com";
  public static final String VERSION = "v1";
}
