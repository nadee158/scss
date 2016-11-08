package com.privasia.scss.core.reponse;

public class ChangePasswordReponse extends BaseResponse {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private int emailSentStatus = 0;

  private int smsSentStatus = 0;

  public int getEmailSentStatus() {
    return emailSentStatus;
  }

  public void setEmailSentStatus(int emailSentStatus) {
    this.emailSentStatus = emailSentStatus;
  }

  public int getSmsSentStatus() {
    return smsSentStatus;
  }

  public void setSmsSentStatus(int smsSentStatus) {
    this.smsSentStatus = smsSentStatus;
  }



}
