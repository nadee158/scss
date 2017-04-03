package com.privasia.scss.opus.dto;

import java.io.Serializable;
import java.util.List;

public class OpusBaseResponse extends OpusBase implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private List<OpusErrorListItem> errorList;
  private String errorDescription;// An error occurred during execution for some containers


  public List<OpusErrorListItem> getErrorList() {
    return errorList;
  }

  public void setErrorList(List<OpusErrorListItem> errorList) {
    this.errorList = errorList;
  }
  
  public String getErrorDescription() {
    return errorDescription;
  }

  public void setErrorDescription(String errorDescription) {
    this.errorDescription = errorDescription;
  }
  
  @Override
  public String toString() {
    return "OpusBaseResponse [userID=" + getUserID()+ ", errorList=" + errorList + "]";
  }



}
