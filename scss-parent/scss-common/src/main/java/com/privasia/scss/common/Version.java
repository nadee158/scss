package com.privasia.scss.common;

public class Version {

  private static final int DEFAULT = 1;
  private int value = 0;

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    if (value < DEFAULT) {
      throw new RuntimeException();
    }
    this.value = value;
  }

  public Version() {
    this.setValue(Version.DEFAULT);
  }

  public Version(int value) {
    this.setValue(value);
  }

}
