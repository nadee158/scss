package com.privasia.scss.common.dto;

import java.io.Serializable;
import java.util.List;


/**
 * @author nadeeshani
 *
 */
public class ReferReasonDTO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private Long referReasonID;

  private String reasonDescription;

  private int sortSEQ;

  private String referStatus;

  private boolean parent;

  private List<ReferReasonDTO> childList;

  public ReferReasonDTO() {
    super();
  }

  public Long getReferReasonID() {
    return referReasonID;
  }

  public void setReferReasonID(Long referReasonID) {
    this.referReasonID = referReasonID;
  }

  public String getReasonDescription() {
    return reasonDescription;
  }

  public void setReasonDescription(String reasonDescription) {
    this.reasonDescription = reasonDescription;
  }

  public int getSortSEQ() {
    return sortSEQ;
  }

  public void setSortSEQ(int sortSEQ) {
    this.sortSEQ = sortSEQ;
  }

  public String getReferStatus() {
    return referStatus;
  }

  public void setReferStatus(String referStatus) {
    this.referStatus = referStatus;
  }

  public boolean isParent() {
    return parent;
  }

  public void setParent(boolean parent) {
    this.parent = parent;
  }

  public List<ReferReasonDTO> getChildList() {
    return childList;
  }

  public void setChildList(List<ReferReasonDTO> childList) {
    this.childList = childList;
  }



}
