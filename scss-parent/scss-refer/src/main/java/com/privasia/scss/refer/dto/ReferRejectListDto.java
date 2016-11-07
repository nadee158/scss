package com.privasia.scss.refer.dto;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;

import com.privasia.scss.core.model.BaseCommonGateInOutAttribute;
import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.model.Company;
import com.privasia.scss.core.model.ReferReject;
import com.privasia.scss.core.model.ReferRejectDetail;
import com.privasia.scss.core.model.SmartCardUser;

public class ReferRejectListDto implements Serializable {

  private static final long serialVersionUID = 1L;

  // ReferReject.referRejectID;
  private String referId;;

  // ReferReject.baseCommonGateInOut.gateInClient.unitNo
  private String boothNo;

  // ReferReject.baseCommonGateInOut.pmHeadNo
  private String pmHeadNo;

  // ReferReject.baseCommonGateInOut.card.company.companyName
  private String haulierCompany;

  // ReferReject.baseCommonGateInOut.card.smartCardUser.commonContactAttribute.name
  private String driverName;

  // ReferReject.referDateTime
  private String referDateTime;

  // ReferReject.referRejectDetails.containerNo
  private String contNo;

  // ReferReject.referRejectDetails.doubleBooking
  private String doubleBooking;



  public ReferRejectListDto(ReferRejectDetail referRejectDetail) {
    super();
    ReferReject referReject = referRejectDetail.getReferReject();
    if (!(referReject == null)) {
      this.referId = Long.toString(referReject.getReferRejectID());
      BaseCommonGateInOutAttribute baseCommonGateInOut = referReject.getBaseCommonGateInOut();
      this.pmHeadNo = baseCommonGateInOut.getPmHeadNo();

      Client client = baseCommonGateInOut.getGateInClient();
      if (!(client == null)) {
        this.boothNo = client.getUnitNo();
      }

      Card card = referReject.getCard();
      if (!(card == null)) {
        Company company = card.getCompany();
        if (!(company == null)) {
          this.haulierCompany = company.getCompanyName();
        }

        if (!(company == null)) {
          SmartCardUser smartCardUser = card.getSmartCardUser();
          this.driverName = smartCardUser.getCommonContactAttribute().getPersonName();
        }

        if (!(referReject.getReferDateTime() == null)) {
          this.referDateTime =
              referReject.getReferDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm a"));
        }

      }

      this.contNo = referRejectDetail.getContainerNo();
      this.doubleBooking = referRejectDetail.getDoubleBooking();

    }



  }

  public String getReferId() {
    return referId;
  }

  public void setReferId(String referId) {
    this.referId = referId;
  }

  public String getBoothNo() {
    return boothNo;
  }

  public void setBoothNo(String boothNo) {
    this.boothNo = boothNo;
  }

  public String getPmHeadNo() {
    return pmHeadNo;
  }

  public void setPmHeadNo(String pmHeadNo) {
    this.pmHeadNo = pmHeadNo;
  }

  public String getHaulierCompany() {
    return haulierCompany;
  }

  public void setHaulierCompany(String haulierCompany) {
    this.haulierCompany = haulierCompany;
  }

  public String getDriverName() {
    return driverName;
  }

  public void setDriverName(String driverName) {
    this.driverName = driverName;
  }

  public String getReferDateTime() {
    return referDateTime;
  }

  public void setReferDateTime(String referDateTime) {
    this.referDateTime = referDateTime;
  }

  public String getContNo() {
    return contNo;
  }

  public void setContNo(String contNo) {
    this.contNo = contNo;
  }

  public String getDoubleBooking() {
    return doubleBooking;
  }

  public void setDoubleBooking(String doubleBooking) {
    this.doubleBooking = doubleBooking;
  }



  //@formatter:off
  /*sql = " SELECT referReject.REFER_ID, client.CLI_UNITNO, referReject.PM_HEAD_NO, com.COM_NAME , scuser.SCU_NAME "
      + " , TO_CHAR(referReject.REFER_DATE_TIME,'DD/MM/YYYY - HH24MI') AS REFER_DATE, det.CONT_NO, det.DOUBLE_BOOKING "
      + " FROM SCSS_REFER_REJECT referReject "
      + " INNER JOIN SCSS_REFER_REJECT_DET det ON referReject.REFER_ID = det.REFER_ID "
      + " INNER JOIN SCSS_CARD card ON referReject.CRD_CARDID_SEQ = card.CRD_CARDID_SEQ "
      + " INNER JOIN SCSS_SCUSER scuser ON card.SCU_USERID = scuser.SCU_USERID_SEQ "
      + " INNER JOIN SCSS_COMPANY com ON card.COM_ID = com.COM_ID_SEQ "
      + " INNER JOIN SCSS_CLIENT client ON client.CLI_CLIENTID_SEQ = referReject.CLI_CLIENT_SEQ ";
  
  obj = new ReferRejectListObject();
  obj.setReferId(rs.getString("REFER_ID"));
  obj.setBoothNo(rs.getString("CLI_UNITNO"));
  obj.setPmHeadNo(rs.getString("PM_HEAD_NO"));
  obj.setHaulierCompany(rs.getString("COM_NAME"));
  obj.setDriverName(rs.getString("SCU_NAME"));
  obj.setReferDateTime(rs.getString("REFER_DATE"));
  obj.setContNo1(rs.getString("CONT_NO"));
  obj.setDoubleBooking(rs.getString("DOUBLE_BOOKING"));
  
  if (StringUtils.isNotBlank(statusCode)) {
            sql += " AND referReject.STATUS_CODE = " + SQL.format(statusCode);
        }

        //sql += " ORDER BY referReject.REFER_DATE_TIME DESC, det.REFER_REJECT_DET_ID DESC";
        sql += " ORDER BY referReject.REFER_DATE_TIME DESC";
*/


}
