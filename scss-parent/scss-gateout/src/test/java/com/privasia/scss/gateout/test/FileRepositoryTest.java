package com.privasia.scss.gateout.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.privasia.scss.gateout.GateOutEntryPoint;
import com.privasia.scss.gateout.MongoConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {GateOutEntryPoint.class, MongoConfig.class})
public class FileRepositoryTest {

  @Autowired
  private GridFsTemplate gridFsTemplate;

  // @Autowired PersonRepository repository;
  //
  @Test
  public void readsFirstPageCorrectly() {
    DBObject metaData = new BasicDBObject();
    metaData.put("extra1", "anything 1");
    metaData.put("extra2", "anything 2");

    InputStream inputStream = null;
    try {
      inputStream = new FileInputStream("D:/download.jpg");
      gridFsTemplate.store(inputStream, "testing.png", "image/png", metaData);

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } finally {
      if (inputStream != null) {
        try {
          inputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    System.out.println("Done");
  }

}
