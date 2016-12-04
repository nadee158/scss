package com.privasia.scss.common.dto;

import java.io.Serializable;

public class NotificationDTO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String id;
  private String root;
  private String description;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getRoot() {
    return root;
  }

  public void setRoot(String root) {
    this.root = root;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  @Override
  public boolean equals(Object obj) {
    return (this.id.equals(((NotificationDTO) obj).id) && this.root.equals(((NotificationDTO) obj).root));
  }

}
