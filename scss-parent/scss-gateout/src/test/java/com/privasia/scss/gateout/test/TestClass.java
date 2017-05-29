package com.privasia.scss.gateout.test;

import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.privasia.scss.common.dto.GateOutRequest;
import com.privasia.scss.common.dto.ImportContainer;

public class TestClass {

  @Test
  public void testMethodOne() throws JsonIOException, IOException {
    Gson gson = new Gson();
    // GateOutWriteRequest obj = GateOutWriteRequest.emptyGateOutWriteRequest();

    GateOutRequest obj = new GateOutRequest();
   
    // 1. Java object to JSON, and save into a file
    gson.toJson(obj, new FileWriter("D:\\GateInRequest.json"));

    // 2. Java object to JSON, and assign to a String
    String jsonInString = gson.toJson(obj);
    System.out.println(jsonInString);
  }

  // @Test
  public void testMethod() throws JsonIOException, IOException {
    Gson gson = new Gson();
    // GateOutWriteRequest obj = GateOutWriteRequest.emptyGateOutWriteRequest();

    ImportContainer obj = new ImportContainer();

    // 1. Java object to JSON, and save into a file
    gson.toJson(obj, new FileWriter("D:\\file.json"));

    // 2. Java object to JSON, and assign to a String
    String jsonInString = gson.toJson(obj);
    System.out.println(jsonInString);

  }

}
