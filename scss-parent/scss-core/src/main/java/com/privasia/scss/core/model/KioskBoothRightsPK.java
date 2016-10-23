/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


/**
 * @author Janaka
 *
 */
@Embeddable
public class KioskBoothRightsPK implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "BOOTH_ID", nullable = false, referencedColumnName = "CLI_CLIENTID_SEQ")
  private Client boothID;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "KIOSK_ID", nullable = false, referencedColumnName = "CLI_CLIENTID_SEQ")
  private Client kioskID;


  public Client getBoothID() {
    return boothID;
  }

  public void setBoothID(Client boothID) {
    this.boothID = boothID;
  }

  public Client getKioskID() {
    return kioskID;
  }

  public void setKioskID(Client kioskID) {
    this.kioskID = kioskID;
  }



}
