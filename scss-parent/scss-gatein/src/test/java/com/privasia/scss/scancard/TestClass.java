package com.privasia.scss.scancard;

import java.io.IOException;

import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.JsonIOException;

public class TestClass {

  @Autowired
  private ModelMapper modelMapper;

  @Test
  public void testMethod()
      throws JsonIOException, IOException /* throws JsonIOException, IOException */ {

    modelMapper.addMappings(new TestUserMap());
  }

  class TestUserMap extends PropertyMap<TestUserSource, TestUserDestination> {

    protected void configure() {
      // map().setName(source.getFirstName());
      skip().setName(null);
    }

  }


}
