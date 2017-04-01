package com.privasia.scss.common.dto;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

/**
 * @author Nadeeshani Senevirathna
 *
 * @param <T>
 */
public class ApiResponseObject<T> implements Serializable {

  private static final long serialVersionUID = 1L;

  private int apiResponseStatus;

  private String apiResponseCode;

  private T apiResponseResults;


  public ApiResponseObject(HttpStatus httpStatus, T apiResponseResults) {
    super();
    this.apiResponseCode = httpStatus.name();
    this.apiResponseStatus = httpStatus.value();
    this.apiResponseResults = apiResponseResults;
  }

  public String getApiResponseCode() {
    return apiResponseCode;
  }

  public void setApiResponseCode(String apiResponseCode) {
    this.apiResponseCode = apiResponseCode;
  }

  public T getApiResponseResults() {
    return apiResponseResults;
  }

  public void setApiResponseResults(T apiResponseResults) {
    this.apiResponseResults = apiResponseResults;
  }

  public int getApiResponseStatus() {
    return apiResponseStatus;
  }

  public void setApiResponseStatus(int apiResponseStatus) {
    this.apiResponseStatus = apiResponseStatus;
  }

  @Override
  public String toString() {
    return "ApiResponseObject [apiResponseStatus=" + apiResponseStatus + ", apiResponseCode=" + apiResponseCode
        + ", apiResponseResults=" + apiResponseResults + "]";
  }



}
