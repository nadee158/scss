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



  public ClientDTO(String lpsIPAddress, Enum<ClientType> type, boolean csmControl) {
    super();
    this.lpsIPAddress = lpsIPAddress;
    this.type = ClientType.fromName(type.name()).getValue();;
    this.csmControl = csmControl;
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



}
