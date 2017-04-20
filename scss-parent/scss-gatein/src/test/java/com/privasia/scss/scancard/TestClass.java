package com.privasia.scss.scancard;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

public class TestClass {

  private class Testing {

    private Optional<String> name = Optional.of("TESTING");
    private long age = 5;
    private LocalDateTime date = LocalDateTime.now();

    public Optional<String> getName() {
      return name;
    }

    public void setName(Optional<String> name) {
      this.name = name;
    }

    public long getAge() {
      return age;
    }

    public void setAge(long age) {
      this.age = age;
    }

    public LocalDateTime getDate() {
      return date;
    }

    public void setDate(LocalDateTime date) {
      this.date = date;
    }


  }


  private ModelMapper modelMapper;

  @Test
  public void testMethodOne() throws JsonIOException, IOException {
    Gson gson = new Gson();
    // GateOutWriteRequest obj = GateOutWriteRequest.emptyGateOutWriteRequest();

    Testing obj = new Testing();

    // 2. Java object to JSON, and assign to a String
    String jsonInString = gson.toJson(obj);

    // FileWriter f = new
    // FileWriter("D:/Projects/LatestSCSS/scss/postman_collections/json_files/GateInWriteRequest.json");
    // f.write(jsonInString);
    // f.flush();
    // f.close();

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
