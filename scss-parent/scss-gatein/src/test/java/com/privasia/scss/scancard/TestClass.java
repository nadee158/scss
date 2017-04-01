package com.privasia.scss.scancard;

import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.privasia.scss.common.dto.GateInRequest;

public class TestClass {


  private ModelMapper modelMapper;

  @Test
  public void testMethodOne() throws JsonIOException, IOException {
    Gson gson = new Gson();
    // GateOutWriteRequest obj = GateOutWriteRequest.emptyGateOutWriteRequest();

    GateInRequest obj = new GateInRequest();
    obj.initializeWithDefaultValues();
    // 1. Java object to JSON, and save into a file
    gson.toJson(obj, new FileWriter("D:\\GateInRequest.json"));

    // 2. Java object to JSON, and assign to a String
    String jsonInString = gson.toJson(obj);
    System.out.println(jsonInString);
  }

  // @Test
  // public void testMethodOne() throws JsonIOException, IOException {
  // Gson gson = new Gson();
  // // GateOutWriteRequest obj = GateOutWriteRequest.emptyGateOutWriteRequest();
  //
  // GateInOutODDDTO obj = GateInOutODDDTO.emptyGateInOutODDRequest();
  // List<WHODDDTO> whoddds = new ArrayList<WHODDDTO>();
  // whoddds.add(new WHODDDTO());
  // whoddds.add(new WHODDDTO());
  // obj.setWhoddds(whoddds);
  // // 1. Java object to JSON, and save into a file
  // gson.toJson(obj, new FileWriter("D:\\GateInOutODDDTO.json"));
  //
  // // 2. Java object to JSON, and assign to a String
  // String jsonInString = gson.toJson(obj);
  // System.out.println(jsonInString);
  // }

  // @Test
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
