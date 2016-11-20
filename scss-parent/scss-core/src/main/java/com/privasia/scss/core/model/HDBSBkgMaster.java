package com.privasia.scss.core.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.privasia.scss.common.enums.CardStatus;
import com.privasia.scss.common.enums.CompanyStatus;

@Entity
@Table(name = "HDBS_BKG_MASTER")
@AttributeOverrides({ @AttributeOverride(name = "addBy", column = @Column(name = "ADD_BY")),
		@AttributeOverride(name = "updateBy", column = @Column(name = "UPDATED_BY")),
		@AttributeOverride(name = "dateTimeAdd", column = @Column(name = "DATETIME_ADD")),
		@AttributeOverride(name = "dateTimeUpdate", column = @Column(name = "DATETIME_UPDATE")) })
public class HDBSBkgMaster extends AuditEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "BKG_MASTER_ID")
	private String bkgMasterID;
	
	@Basic(fetch=FetchType.LAZY)
	@Column(name = "APP_NAME")
	private String appName;

	@Column(name = "HDBS_BKG_SEQ")
	private String hdbsBookingSeq;
	
	@Basic(fetch=FetchType.LAZY)
	@Column(name = "CUS_ID")
	private String customerID;
	
	@Basic(fetch=FetchType.LAZY)
	@Column(name = "COM_NAME")
	private String companyName;
	
	@Basic(fetch=FetchType.LAZY)
	@Column(name = "CUS_CODE")
	private String customerCode;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPANY_ID", nullable = true, referencedColumnName = "COM_ID_SEQ")
	private Company company;

	@Column(name = "DEPOT_CODE")
	private String depotCode;

	@Column(name = "DEPOT_LOCATION")
	private String depotLocation;
	
	@Basic(fetch=FetchType.LAZY)
	@Column(name = "DRIVER_IC_PP")
	private String driverICOrPP;
	
	@Basic(fetch=FetchType.LAZY)
	@Column(name = "SCU_NAME")
	private String scuName;

	@Column(name = "CRD_SCARDNO")
	private String cardNo;

	@Column(name = "CRD_CARDSTATUS", nullable = true)
	@Type(type = "com.privasia.scss.core.util.enumusertype.CardStatusEnumUserType")
	private CardStatus cardStatus;

	@Column(name = "HDBS_BKG_REF_NO")
	private String hdbsBkgRefNo;

	@Column(name = "STATUS_CODE", nullable = true)
	@Type(type = "com.privasia.scss.core.util.enumusertype.CompanyStatusEnumUserType")
	private CompanyStatus statusCode;

	@Column(name = "PM_HEAD_NO")
	private String pmHeadNo;

	@Column(name = "PLATE_NO")
	private String plateNo;
	
	@Basic(fetch=FetchType.LAZY)
	@Column(name = "DRAYAGE_BOOKING")
	private Integer drayageBooking;

	public String getBkgMasterID() {
		return bkgMasterID;
	}

	public void setBkgMasterID(String bkgMasterID) {
		this.bkgMasterID = bkgMasterID;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getHdbsBookingSeq() {
		return hdbsBookingSeq;
	}

	public void setHdbsBookingSeq(String hdbsBookingSeq) {
		this.hdbsBookingSeq = hdbsBookingSeq;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getDepotCode() {
		return depotCode;
	}

	public void setDepotCode(String depotCode) {
		this.depotCode = depotCode;
	}

	public String getDepotLocation() {
		return depotLocation;
	}

	public void setDepotLocation(String depotLocation) {
		this.depotLocation = depotLocation;
	}

	public String getDriverICOrPP() {
		return driverICOrPP;
	}

	public void setDriverICOrPP(String driverICOrPP) {
		this.driverICOrPP = driverICOrPP;
	}

	public String getScuName() {
		return scuName;
	}

	public void setScuName(String scuName) {
		this.scuName = scuName;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public CardStatus getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(CardStatus cardStatus) {
		this.cardStatus = cardStatus;
	}

	public String getHdbsBkgRefNo() {
		return hdbsBkgRefNo;
	}

	public void setHdbsBkgRefNo(String hdbsBkgRefNo) {
		this.hdbsBkgRefNo = hdbsBkgRefNo;
	}

	public CompanyStatus getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(CompanyStatus statusCode) {
		this.statusCode = statusCode;
	}

	public String getPmHeadNo() {
		return pmHeadNo;
	}

	public void setPmHeadNo(String pmHeadNo) {
		this.pmHeadNo = pmHeadNo;
	}

	public String getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}

	public Integer getDrayageBooking() {
		return drayageBooking;
	}

	public void setDrayageBooking(Integer drayageBooking) {
		this.drayageBooking = drayageBooking;
	}

}
