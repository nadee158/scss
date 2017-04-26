package com.privasia.scss.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
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

  /*public ReferReasonDTO(ReferReason parent, List<ReferReason> childList) {
    constructDto(this, parent);
    if (!(childList == null || childList.isEmpty())) {
      List<ReferReasonDTO> childrenList = new ArrayList<ReferReasonDTO>();
      childList.forEach(child -> {
        childrenList.add(constructDto(new ReferReasonDTO(), child));
      });
      this.childList = childrenList;
    }
  }

  private ReferReasonDTO constructDto(ReferReasonDTO referReasonDTO, ReferReason parent) {

    referReasonDTO.setReferReasonID(parent.getReferReasonID());

    referReasonDTO.setReasonDescription(parent.getReasonDescription());

    referReasonDTO.setSortSEQ(parent.getSortSEQ());

    if (!(parent.getReferStatus() == null)) {
      referReasonDTO.setReferStatus(parent.getReferStatus().toString());
    }
    referReasonDTO.setParent(parent.isParent());
    return referReasonDTO;
  }*/

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
