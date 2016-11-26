package com.privasia.scss.gateout.dto;

import java.io.InputStream;
import java.io.Serializable;

public class FileDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private String oddIDSeq1;

  private String GTPPassNo1;

  private String ExpExportNoSeq1;

  private String oddIDSeq2;

  private String GTPPassNo2;

  private String ExpExportNoSeq2;

  private String fileType;

  private String trxType;

  public String getFileType() {
    return fileType;
  }

  public void setFileType(String fileType) {
    this.fileType = fileType;
  }

  private byte[] file;

  private String collectionName;

  private InputStream cameraImage;

  private long folderSize;

  public String getOddIDSeq1() {
    return oddIDSeq1;
  }

  public void setOddIDSeq1(String oddIDSeq1) {
    this.oddIDSeq1 = oddIDSeq1;
  }

  public String getGTPPassNo1() {
    return GTPPassNo1;
  }

  public void setGTPPassNo1(String gTPPassNo1) {
    GTPPassNo1 = gTPPassNo1;
  }

  public String getExpExportNoSeq1() {
    return ExpExportNoSeq1;
  }

  public void setExpExportNoSeq1(String expExportNoSeq1) {
    ExpExportNoSeq1 = expExportNoSeq1;
  }

  public String getOddIDSeq2() {
    return oddIDSeq2;
  }

  public void setOddIDSeq2(String oddIDSeq2) {
    this.oddIDSeq2 = oddIDSeq2;
  }

  public String getGTPPassNo2() {
    return GTPPassNo2;
  }

  public void setGTPPassNo2(String gTPPassNo2) {
    GTPPassNo2 = gTPPassNo2;
  }

  public String getExpExportNoSeq2() {
    return ExpExportNoSeq2;
  }

  public void setExpExportNoSeq2(String expExportNoSeq2) {
    ExpExportNoSeq2 = expExportNoSeq2;
  }

  public byte[] getFile() {
    return file;
  }

  public void setFile(byte[] file) {
    this.file = file;
  }

  public String getCollectionName() {
    return collectionName;
  }

  public void setCollectionName(String collectionName) {
    this.collectionName = collectionName;
  }

  public InputStream getCameraImage() {
    return cameraImage;
  }

  public void setCameraImage(InputStream cameraImage) {
    this.cameraImage = cameraImage;
  }

  public long getFolderSize() {
    return folderSize;
  }

  public void setFolderSize(long folderSize) {
    this.folderSize = folderSize;
  }

  public String getTrxType() {
    return trxType;
  }

  public void setTrxType(String trxType) {
    this.trxType = trxType;
  }



}
