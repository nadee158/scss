package com.privasia.scss.scancard;

import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.privasia.scss.common.dto.WHODDDTO;

public class TestClass {

  @Test
  public void testMethod() throws JsonIOException, IOException {
    Gson gson = new Gson();
    WHODDDTO obj = new WHODDDTO();

    // 1. Java object to JSON, and save into a file
    gson.toJson(obj, new FileWriter("D:\\file.json"));

    // 2. Java object to JSON, and assign to a String
    String jsonInString = gson.toJson(obj);
    System.out.println(jsonInString);

  }

}
