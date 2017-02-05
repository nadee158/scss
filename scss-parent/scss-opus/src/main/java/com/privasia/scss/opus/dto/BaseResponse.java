package com.privasia.scss.opus.dto;

import java.io.Serializable;
import java.util.List;

public class BaseResponse implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private int errorCode;// 0,
  private String errorMessage;// ,
  private String userID;// USER01,
  private List<ErrorListItem> errorList;
  private String errorDescription;// An error occurred during execution for some containers

  public int getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(int errorCode) {
    this.errorCode = errorCode;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public String getUserID() {
    return userID;
  }

  public void setUserID(String userID) {
    this.userID = userID;
  }


  public List<ErrorListItem> getErrorList() {
    return errorList;
  }

  public void setErrorList(List<ErrorListItem> errorList) {
    this.errorList = errorList;
  }

  @Override
  public String toString() {
    return "BaseResponse [errorCode=" + errorCode + ", errorMessage=" + errorMessage + ", userID=" + userID
        + ", errorList=" + errorList + "]";
  }

  public String getErrorDescription() {
    return errorDescription;
  }

  public void setErrorDescription(String errorDescription) {
    this.errorDescription = errorDescription;
  }



}
