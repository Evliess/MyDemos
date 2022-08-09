package com.my.service.dp.prototype;

public abstract class GameUnit implements Cloneable {
  public static final String WORD = "Hello, ";

  private String position;

  public GameUnit() {
    position = WORD;
  }

  @Override
  public GameUnit clone() throws CloneNotSupportedException {
    GameUnit unit = (GameUnit) super.clone();
    unit.initialize();
    return unit;
  }

  protected void initialize() {
    this.position = WORD;
    reset();
  }

  protected abstract void reset();

  public void say(String word) {
    this.position = this.position.concat(word);
  }

  public String getPosition() {
    return position;
  }
}
