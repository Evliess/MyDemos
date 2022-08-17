package com.my.service.dp.prototype;

public class Swordsman extends GameUnit {
  private String state = "World";

  @Override
  protected void reset() {
    this.state = "World";
  }

  public void action() {
    this.state = "Prototype";
    this.say("Swordsman");
  }

  @Override
  public String toString() {
    return "Swordsman " + state + "@" + getPosition();
  }
}
