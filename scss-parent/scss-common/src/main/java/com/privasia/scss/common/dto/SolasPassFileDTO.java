package com.privasia.scss.common.dto;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SolasPassFileDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long issuerId;

  private String issueBy;

  private String issuerNRIC;

  private String weighStation;

  private int weighingMethod;

  private String gateInOK;

  private String certificateNo;

  private boolean c1WithInTolerance = true;

  private boolean c2WithInTolerance = true;

  private byte[] certificate;

  private String exportSEQ01;

  private String exportSEQ02;

  private String container01No;
  private int terminalVGMC1;

  private String container02No;
  private int terminalVGMC2;


  public Long getIssuerId() {
    return issuerId;
  }

  public void setIssuerId(Long issuerId) {
    this.issuerId = issuerId;
  }

  public String getWeighStation() {
    return weighStation;
  }

  public void setWeighStation(String weighStation) {
    this.weighStation = weighStation;
  }

  public String getGateInOK() {
    return gateInOK;
  }

  public void setGateInOK(String gateInOK) {
    this.gateInOK = gateInOK;
  }

  public String getCertificateNo() {
    return certificateNo;
  }

  public void setCertificateNo(String certificateNo) {
    this.certificateNo = certificateNo;
  }

  public boolean isC1WithInTolerance() {
    return c1WithInTolerance;
  }

  public void setC1WithInTolerance(boolean c1WithInTolerance) {
    this.c1WithInTolerance = c1WithInTolerance;
  }

  public boolean isC2WithInTolerance() {
    return c2WithInTolerance;
  }

  public void setC2WithInTolerance(boolean c2WithInTolerance) {
    this.c2WithInTolerance = c2WithInTolerance;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

  public byte[] getCertificate() {
    return certificate;
  }

  public void setCertificate(byte[] certificate) {
    this.certificate = certificate;
  }

  public String getExportSEQ01() {
    return exportSEQ01;
  }

  public void setExportSEQ01(String exportSEQ01) {
    this.exportSEQ01 = exportSEQ01;
  }

  public String getExportSEQ02() {
    return exportSEQ02;
  }

  public void setExportSEQ02(String exportSEQ02) {
    this.exportSEQ02 = exportSEQ02;
  }

  public String getIssueBy() {
    return issueBy;
  }

  public void setIssueBy(String issueBy) {
    this.issueBy = issueBy;
  }

  public String getIssuerNRIC() {
    return issuerNRIC;
  }

  public void setIssuerNRIC(String issuerNRIC) {
    this.issuerNRIC = issuerNRIC;
  }

  public String getContainer01No() {
    return container01No;
  }

  public void setContainer01No(String container01No) {
    this.container01No = container01No;
  }

  public int getTerminalVGMC1() {
    return terminalVGMC1;
  }

  public void setTerminalVGMC1(int terminalVGMC1) {
    this.terminalVGMC1 = terminalVGMC1;
  }

  public String getContainer02No() {
    return container02No;
  }

  public void setContainer02No(String container02No) {
    this.container02No = container02No;
  }

  public int getTerminalVGMC2() {
    return terminalVGMC2;
  }

  public void setTerminalVGMC2(int terminalVGMC2) {
    this.terminalVGMC2 = terminalVGMC2;
  }

  public int getWeighingMethod() {
    return weighingMethod;
  }

  public void setWeighingMethod(int weighingMethod) {
    this.weighingMethod = weighingMethod;
  }


}
