package com.privasia.scss.core.dto;

import java.io.Serializable;

import com.privasia.scss.core.util.constant.ClientType;

public class ClientDTO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private long clientID;

  private String lpsIPAddress;

  private String type;

  private boolean csmControl;

  private String unitNo;

  private int cosmosPortNo;

  public ClientDTO(String lpsIPAddress, Enum<ClientType> type, boolean csmControl) {
    super();
    this.lpsIPAddress = lpsIPAddress;
    this.type = ClientType.fromName(type.name()).getValue();;
    this.csmControl = csmControl;
  }



  public ClientDTO(String unitNo) {
    super();
    this.unitNo = unitNo;
  }



  public ClientDTO(String unitNo, int cosmosPortNo) {
    super();
    this.unitNo = unitNo;
    this.cosmosPortNo = cosmosPortNo;
  }



  public long getClientID() {
    return clientID;
  }

  public void setClientID(long clientID) {
    this.clientID = clientID;
  }

  public String getLpsIPAddress() {
    return lpsIPAddress;
  }

  public void setLpsIPAddress(String lpsIPAddress) {
    this.lpsIPAddress = lpsIPAddress;
  }


  public boolean isCsmControl() {
    return csmControl;
  }

  public void setCsmControl(boolean csmControl) {
    this.csmControl = csmControl;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getUnitNo() {
    return unitNo;
  }

  public void setUnitNo(String unitNo) {
    this.unitNo = unitNo;
  }



  public int getCosmosPortNo() {
    return cosmosPortNo;
  }



  public void setCosmosPortNo(int cosmosPortNo) {
    this.cosmosPortNo = cosmosPortNo;
  }



}
