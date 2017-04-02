/**
 * 
 */
package com.privasia.scss.common.dto;

import java.io.Serializable;

/**
 * @author Janaka
 *
 */
public class CommonContainerDTO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String containerNumber;

  private int containerHeight;

  private String containerISOCode;

  private String containerFullOrEmpty;

  private String containerSize;

  public String getContainerNumber() {
    return containerNumber;
  }

  public void setContainerNumber(String containerNumber) {
    this.containerNumber = containerNumber;
  }

  public String getContainerISOCode() {
    return containerISOCode;
  }

  public void setContainerISOCode(String containerISOCode) {
    this.containerISOCode = containerISOCode;
  }

  public String getContainerFullOrEmpty() {
    return containerFullOrEmpty;
  }

  public void setContainerFullOrEmpty(String containerFullOrEmpty) {
    this.containerFullOrEmpty = containerFullOrEmpty;
  }

  public int getContainerHeight() {
    return containerHeight;
  }

  public void setContainerHeight(int containerHeight) {
    this.containerHeight = containerHeight;
  }

  public String getContainerSize() {
    return containerSize;
  }

  public void setContainerSize(String containerSize) {
    this.containerSize = containerSize;
  }

  public CommonContainerDTO initializeWithDefaultValues(String containerNo) {
    this.setContainerNumber(containerNo);
    this.setContainerFullOrEmpty("F");
    this.setContainerHeight(150);
    this.setContainerISOCode("2210");
    this.setContainerSize("8000");
    return this;
  }

}
