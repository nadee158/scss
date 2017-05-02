package com.privasia.scss.gateout.mongo.repository;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Component;

import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSFile;
import com.privasia.scss.common.enums.CollectionType;

@Component
public class GridFSRepository {

  private GridFsTemplate pdfFileGridFsTemplate;

  private GridFsTemplate zipFileGridFsTemplate;

  private GridFsTemplate solasCertificateGridFsTemplate;

  private GridFsTemplate gridFsTemplate;

  @Autowired
  public void setPdfFileGridFsTemplate(@Qualifier("pdfFileGridFsTemplate") GridFsTemplate pdfFileGridFsTemplate) {
    this.pdfFileGridFsTemplate = pdfFileGridFsTemplate;
  }

  @Autowired
  public void setZipFileGridFsTemplate(@Qualifier("zipFileGridFsTemplate") GridFsTemplate zipFileGridFsTemplate) {
    this.zipFileGridFsTemplate = zipFileGridFsTemplate;
  }

  @Autowired
  public void setSolasCertificateGridFsTemplate(
      @Qualifier("solasCertificateGridFsTemplate") GridFsTemplate solasCertificateGridFsTemplate) {
    this.solasCertificateGridFsTemplate = solasCertificateGridFsTemplate;
  }

  @Autowired
  public void setGridFsTemplate(@Qualifier("gridFsTemplate") GridFsTemplate gridFsTemplate) {
    this.gridFsTemplate = gridFsTemplate;
  }

  public GridFSFile storeFile(InputStream content, DBObject metadata, CollectionType collectionType) {
    System.out.println("collectionType " + collectionType);
    if (collectionType == null) {
      return gridFsTemplate.store(content, metadata);
    }
    switch (collectionType) {
      case PDF_FILE_COLLECTION:
        return pdfFileGridFsTemplate.store(content, metadata);
      case SOLAS_CERTIFICATE_COLLECTION:
        System.out.println("solasCertificateGridFsTemplate **********************************************");
        return solasCertificateGridFsTemplate.store(content, metadata);
      case ZIP_FILE_COLLECTION:
        return zipFileGridFsTemplate.store(content, metadata);
      default:
        return gridFsTemplate.store(content, metadata);
    }
  }

  public GridFSFile storeFile(InputStream content, String filename, DBObject metadata, CollectionType collectionType) {
    System.out.println("collectionType " + collectionType);
    if (collectionType == null) {
      return gridFsTemplate.store(content, filename, metadata);
    }
    switch (collectionType) {
      case PDF_FILE_COLLECTION:
        return pdfFileGridFsTemplate.store(content, filename, metadata);
      case SOLAS_CERTIFICATE_COLLECTION:
        return solasCertificateGridFsTemplate.store(content, filename, metadata);
      case ZIP_FILE_COLLECTION:
        return zipFileGridFsTemplate.store(content, filename, metadata);
      default:
        return gridFsTemplate.store(content, filename, metadata);
    }
  }

  public GridFSFile storeFile(InputStream content, String filename, String contentType, DBObject metadata,
      CollectionType collectionType) {
    System.out.println("collectionType " + collectionType);
    if (collectionType == null) {
      return gridFsTemplate.store(content, filename, contentType, metadata);
    }
    switch (collectionType) {
      case PDF_FILE_COLLECTION:
        return pdfFileGridFsTemplate.store(content, filename, contentType, metadata);
      case SOLAS_CERTIFICATE_COLLECTION:
        return solasCertificateGridFsTemplate.store(content, filename, contentType, metadata);
      case ZIP_FILE_COLLECTION:
        return zipFileGridFsTemplate.store(content, filename, contentType, metadata);
      default:
        return gridFsTemplate.store(content, filename, contentType, metadata);
    }
  }

}
