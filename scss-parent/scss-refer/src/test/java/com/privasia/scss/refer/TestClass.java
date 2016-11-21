package com.privasia.scss.refer;

import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.privasia.scss.refer.dto.ReferRejectUpdateObjetDto;

public class TestClass {

  @Test
  public void testMethod() throws JsonIOException, IOException {
    Gson gson = new Gson();
    ReferRejectUpdateObjetDto obj = new ReferRejectUpdateObjetDto();

    // 1. Java object to JSON, and save into a file
    gson.toJson(obj, new FileWriter("D:\\file.json"));

    // 2. Java object to JSON, and assign to a String
    String jsonInString = gson.toJson(obj);
    System.out.println(jsonInString);

  }

}
