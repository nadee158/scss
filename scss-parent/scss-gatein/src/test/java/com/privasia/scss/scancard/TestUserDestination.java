package com.privasia.scss.scancard;

public class TestUserDestination {

  @Override
  public String toString() {
    return "TestUserDestination [name=" + name + ", age=" + age + ", testText=" + testText + "]";
  }

  private String name;
  private String age;
  private String testText;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAge() {
    return age;
  }

  public void setAge(String age) {
    this.age = age;
  }

  public String getTestText() {
    return testText;
  }

  public void setTestText(String testText) {
    this.testText = testText;
  }

}
