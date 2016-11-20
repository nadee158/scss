package com.privasia.scss.common.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author Nadeeshani Senevirathna
 *
 * @param <APIResponseObject>
 */
public class CustomResponseEntity<ApiResponseObject> extends ResponseEntity<ApiResponseObject> {

  private ApiResponseObject body;

  private HttpStatus status;

  public CustomResponseEntity(ApiResponseObject body, HttpStatus status) {
    super(body, status);
    this.status = status;
    this.body = body;
  }



  public ApiResponseObject getBody() {
    return body;
  }

  public void setBody(ApiResponseObject body) {
    this.body = body;
  }

  public HttpStatus getStatus() {
    return status;
  }

  public void setStatus(HttpStatus status) {
    this.status = status;
  }



}
