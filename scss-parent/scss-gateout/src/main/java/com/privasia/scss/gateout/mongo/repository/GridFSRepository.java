package com.privasia.scss.gateout.mongo.repository;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Component;

import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSFile;

@Component
public class GridFSRepository {

  @Autowired
  private GridFsTemplate gridFsTemplate;

  public GridFSFile storeFile(InputStream content, DBObject metadata) {
    return gridFsTemplate.store(content, metadata);
  }

  public GridFSFile storeFile(InputStream content, String filename, DBObject metadata) {
    return gridFsTemplate.store(content, filename, metadata);
  }

  public GridFSFile storeFile(InputStream content, String filename, String contentType, DBObject metadata) {
    return gridFsTemplate.store(content, filename, contentType, metadata);
  }

}
