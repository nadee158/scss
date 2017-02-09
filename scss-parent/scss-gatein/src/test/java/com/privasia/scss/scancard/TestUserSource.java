package com.privasia.scss.scancard;

public class TestUserSource {

  private String name;
  private String age;
  private String testText;

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return "TestUserSource [name=" + name + ", age=" + age + ", testText=" + testText + "]";
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

  public TestUserSource() {
    super();
    this.name = "janaka";
    this.age = "10";
    this.testText = "testText Val";
  }



}
