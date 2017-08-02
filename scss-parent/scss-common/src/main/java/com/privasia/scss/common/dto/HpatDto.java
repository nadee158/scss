package com.privasia.scss.common.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class HpatDto implements Serializable {

	public static String DELETION_ACTIVE = "A";
	public static String DELETION_DELETE = "D";

	public static String ON_TIME = "ON-TIME";
	public static String ACTIVE = "ACTIVE";
	public static String LATE = "LATE";
	public static String COMPLETE = "COMP";
	public static String EXPIRED = "EXPIRED";
	public static String EARLY = "EARLY";
	public static String CANCEL = "CAN";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String bookingId;// (rs.getString("BOOKING_ID"));
	private String driverId;// (rs.getString("DRIVER_IC_PP"));
	private String buffer;// (rs.getString("BUFFER"));
	private String comId;// (rs.getString("HAULIER_CODE"));
	private String crdScardno;// (rs.getString("CRD_SCARDNO"));
	private String pmNo;// (rs.getString("PM_NO"));
	private String fromScss;// ("Y");
	private String status;// (rs.getString("STATUS_CODE"));
	private String trlrNo;// (rs.getString("TRAILER_NO"));
	private String trlrType;// (rs.getString("TRAILER_TYPE"));

	private String apptStart;// (this.convertDateToString(rs.getTimestamp("APPT_DATE_START")));
	private String apptEnd;// (this.convertDateToString(rs.getTimestamp("APPT_DATE_END")));

	private LocalDateTime apptStartDate;// (rs.getTimestamp("APPT_DATE_START")));
	private LocalDateTime apptEndDate;// (rs.getTimestamp("APPT_DATE_END")));

	private String OnTimeFlag = "N";
	private String impExpScreen;
	private String menuId;
	private String impGatePass01;
	private String impGatePass02;
	private String expContainer01;
	private String expContainer02;
	private String impContainer01;
	private String impContainer02;
	private String ittGatePass01;
	private String ittGatePass02;
	private String container01Size;
	private String container02Size;

	public HpatDto() {
		super();
	}

	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	public String getDriverId() {
		return driverId;
	}

	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}

	public String getBuffer() {
		return buffer;
	}

	public void setBuffer(String buffer) {
		this.buffer = buffer;
	}

	public String getComId() {
		return comId;
	}

	public void setComId(String comId) {
		this.comId = comId;
	}

	public String getCrdScardno() {
		return crdScardno;
	}

	public void setCrdScardno(String crdScardno) {
		this.crdScardno = crdScardno;
	}

	public String getPmNo() {
		return pmNo;
	}

	public void setPmNo(String pmNo) {
		this.pmNo = pmNo;
	}

	public String getFromScss() {
		return fromScss;
	}

	public void setFromScss(String fromScss) {
		this.fromScss = fromScss;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTrlrNo() {
		return trlrNo;
	}

	public void setTrlrNo(String trlrNo) {
		this.trlrNo = trlrNo;
	}

	public String getTrlrType() {
		return trlrType;
	}

	public void setTrlrType(String trlrType) {
		this.trlrType = trlrType;
	}

	public String getApptStart() {
		return apptStart;
	}

	public void setApptStart(String apptStart) {
		this.apptStart = apptStart;
	}

	public String getApptEnd() {
		return apptEnd;
	}

	public void setApptEnd(String apptEnd) {
		this.apptEnd = apptEnd;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getOnTimeFlag() {
		return OnTimeFlag;
	}

	public void setOnTimeFlag(String onTimeFlag) {
		OnTimeFlag = onTimeFlag;
	}

	public String getImpExpScreen() {
		return impExpScreen;
	}

	public void setImpExpScreen(String impExpScreen) {
		this.impExpScreen = impExpScreen;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public LocalDateTime getApptStartDate() {
		return apptStartDate;
	}

	public void setApptStartDate(LocalDateTime apptStartDate) {
		this.apptStartDate = apptStartDate;
	}

	public LocalDateTime getApptEndDate() {
		return apptEndDate;
	}

	public void setApptEndDate(LocalDateTime apptEndDate) {
		this.apptEndDate = apptEndDate;
	}

	public String getImpGatePass01() {
		return impGatePass01;
	}

	public void setImpGatePass01(String impGatePass01) {
		this.impGatePass01 = impGatePass01;
	}

	public String getImpGatePass02() {
		return impGatePass02;
	}

	public void setImpGatePass02(String impGatePass02) {
		this.impGatePass02 = impGatePass02;
	}

	public String getIttGatePass01() {
		return ittGatePass01;
	}

	public void setIttGatePass01(String ittGatePass01) {
		this.ittGatePass01 = ittGatePass01;
	}

	public String getIttGatePass02() {
		return ittGatePass02;
	}

	public void setIttGatePass02(String ittGatePass02) {
		this.ittGatePass02 = ittGatePass02;
	}

	public String getExpContainer01() {
		return expContainer01;
	}

	public void setExpContainer01(String expContainer01) {
		this.expContainer01 = expContainer01;
	}

	public String getExpContainer02() {
		return expContainer02;
	}

	public void setExpContainer02(String expContainer02) {
		this.expContainer02 = expContainer02;
	}

	public String getImpContainer01() {
		return impContainer01;
	}

	public void setImpContainer01(String impContainer01) {
		this.impContainer01 = impContainer01;
	}

	public String getImpContainer02() {
		return impContainer02;
	}

	public void setImpContainer02(String impContainer02) {
		this.impContainer02 = impContainer02;
	}

	public String getContainer01Size() {
		return container01Size;
	}

	public void setContainer01Size(String container01Size) {
		this.container01Size = container01Size;
	}

	public String getContainer02Size() {
		return container02Size;
	}

	public void setContainer02Size(String container02Size) {
		this.container02Size = container02Size;
	}
	
	

}
