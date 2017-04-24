package com.privasia.scss.refer;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.privasia.scss.common.dto.GateInWriteRequest;
import com.privasia.scss.common.dto.ReferRejectDTO;

public class TestClass {

  @Test
  public void testMethod()
      throws JsonIOException, IOException /* throws JsonIOException, IOException */ {
    Gson gson = new Gson();
    ReferRejectDTO obj = new ReferRejectDTO();
    obj.initializeWithDefaultValues();
    GateInWriteRequest gateInWriteRequest = new GateInWriteRequest();
    gateInWriteRequest.setReferRejectDTO(Optional.of(obj));
    // 1. Java object to JSON, and save into a file
    gson.toJson(obj, new FileWriter("D:\\file.json"));

    // 2. Java object to JSON, and assign to a String
    String jsonInString = gson.toJson(gateInWriteRequest);
    System.out.println(jsonInString);

  }

}
