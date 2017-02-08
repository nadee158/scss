package com.privasia.scss.scancard;

import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

public class TestClass {


  private ModelMapper modelMapper;

  @Test
  public void testMethod() {
    modelMapper = new ModelMapper();
    modelMapper.addMappings(new TestUserMap());
    // all properties are set inside constructor
    TestUserSource testUserSource = new TestUserSource();

    TestUserDestination testUserDestination = new TestUserDestination();
    testUserDestination.setName("NADEESHANI");
    System.out.println("testUserSource before " + testUserSource);
    System.out.println("testUserDestination before " + testUserDestination);
    modelMapper.map(testUserSource, testUserDestination);
    System.out.println("_______________________________________");
    System.out.println("_______________________________________");
    System.out.println("testUserSource after " + testUserSource);
    System.out.println("testUserDestination after " + testUserDestination);

  }

  class TestUserMap extends PropertyMap<TestUserSource, TestUserDestination> {

    protected void configure() {
      // map().setName(source.getFirstName());
      skip().setName(null);
    }

  }


}
