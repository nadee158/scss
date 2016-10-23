package com.privasia.scss.common;

import java.util.UUID;

public class Identifier {

  private UUID value = null;

  public String getValue() {
    return value.toString();
  }

  public void setValue(String value) {
    // this.value = new UUID().fromString(value);
  }

  public Identifier(String value) {
    this.setValue(value);
  }

  public Identifier(int value) {

  }

  public Identifier(long value) {

  }

  public Identifier(UUID value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return this.getValue();
  }

  public int getValueAsInt() {
    return 0;
  }

  public long getValueAsLong() {
    return 0;
  }

  public UUID getValueAsUUID() {
    return null;
  }

}
