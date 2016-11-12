/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.privasia.scss.core.util.constant.HpatReferStatus;

/**
 * @author Janaka
 *
 */
@Entity
@Table(name = "SCSS_PRINT_EIR")
@AttributeOverrides({ @AttributeOverride(name = "addBy", column = @Column(name = "ADD_BY")),
		@AttributeOverride(name = "updateBy", column = @Column(name = "UPDATE_BY")),
		@AttributeOverride(name = "dateTimeAdd", column = @Column(name = "DATETIME_ADD")),
		@AttributeOverride(name = "dateTimeUpdate", column = @Column(name = "DATETIME_UPDATE")) })
public class PrintEir extends AuditEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SCSS_PRINT_EIR")
	@SequenceGenerator(name = "SEQ_SCSS_PRINT_EIR", sequenceName = "SCSS_PRINT_EIR_SEQ")
	@Column(name = "PRINT_NO")
	private Long printEIRID;

	@Column(name = "TIME_IN")
	private String timeIn;

	@Column(name = "CALL_CARD")
	private String callCard;

	@Column(name = "TRUCK_HEAD_NO")
	private String truckHeadNo;

	@Column(name = "COMP_NAME_PRINT")
	private String companyName;

	@Column(name = "ICNOORPASSPORT")
	private String icNoOrPassport;

	@Column(name = "TRUCKPLATENO")
	private String truckNo;

	@Column(name = "STATUS")
	@Type(type = "com.privasia.scss.core.util.enumusertype.HPATReferStatusEnumUserType")
	private HpatReferStatus status;

	@Column(name = "SCUNAME")
	private String scuName;

	@Column(name = "GATEINNO")
	private String gateInNo;

	@Column(name = "CLIENT_IP")
	private String clientIp;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "containerNumber", column = @Column(name = "CONTAINERNO_C1")),
			@AttributeOverride(name = "containerISOCode", column = @Column(name = "ISO_C1")),
			@AttributeOverride(name = "containerLength", column = @Column(name = "SIZE_C1")),
			@AttributeOverride(name = "containerFullOrEmpty", column = @Column(name = "FULL_OR_EMPTY_C1")),
			@AttributeOverride(name = "containerBayCode", column = @Column(name = "BAYCODE_C1")),
			@AttributeOverride(name = "containerInOrOut", column = @Column(name = "INOROUT_C1")),
			@AttributeOverride(name = "containerPositionOnTruck", column = @Column(name = "POSITIONONTRUCK_C1")),
			@AttributeOverride(name = "containerHeight", column = @Column(name = "HEIGHT_C1")),
			@AttributeOverride(name = "containerNetWeight", column = @Column(name = "NETWEIGHT_C1")),
			@AttributeOverride(name = "containerType", column = @Column(name = "TYPE_C1")),
			@AttributeOverride(name = "lineOneInfo", column = @Column(name = "LINE_INFO1_C1")),
			@AttributeOverride(name = "lineTwoInfo", column = @Column(name = "LINE_INFO2_C1")),
			@AttributeOverride(name = "containerSeal", column = @Column(name = "SEAL_C1")),
			@AttributeOverride(name = "containerLine", column = @Column(name = "LINE_C1")) })
	private PrintEIRContainerInfo container01;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "containerNumber", column = @Column(name = "CONTAINERNO_C2")),
			@AttributeOverride(name = "containerISOCode", column = @Column(name = "ISO_C2")),
			@AttributeOverride(name = "containerLength", column = @Column(name = "SIZE_C2")),
			@AttributeOverride(name = "containerFullOrEmpty", column = @Column(name = "FULL_OR_EMPTY_C2")),
			@AttributeOverride(name = "containerBayCode", column = @Column(name = "BAYCODE_C2")),
			@AttributeOverride(name = "containerInOrOut", column = @Column(name = "INOROUT_C2")),
			@AttributeOverride(name = "containerPositionOnTruck", column = @Column(name = "POSITIONONTRUCK_C2")),
			@AttributeOverride(name = "containerHeight", column = @Column(name = "HEIGHT_C2")),
			@AttributeOverride(name = "containerNetWeight", column = @Column(name = "NETWEIGHT_C2")),
			@AttributeOverride(name = "containerType", column = @Column(name = "TYPE_C2")),
			@AttributeOverride(name = "lineOneInfo", column = @Column(name = "LINE_INFO1_C2")),
			@AttributeOverride(name = "lineTwoInfo", column = @Column(name = "LINE_INFO2_C2")),
			@AttributeOverride(name = "containerSeal", column = @Column(name = "SEAL_C2")),
			@AttributeOverride(name = "containerLine", column = @Column(name = "LINE_C2")) })
	private PrintEIRContainerInfo container02;

	public Long getPrintEIRID() {
		return printEIRID;
	}

	public void setPrintEIRID(Long printEIRID) {
		this.printEIRID = printEIRID;
	}

	public String getTimeIn() {
		return timeIn;
	}

	public void setTimeIn(String timeIn) {
		this.timeIn = timeIn;
	}

	public String getCallCard() {
		return callCard;
	}

	public void setCallCard(String callCard) {
		this.callCard = callCard;
	}

	public String getTruckHeadNo() {
		return truckHeadNo;
	}

	public void setTruckHeadNo(String truckHeadNo) {
		this.truckHeadNo = truckHeadNo;
	}

	public String getIcNoOrPassport() {
		return icNoOrPassport;
	}

	public void setIcNoOrPassport(String icNoOrPassport) {
		this.icNoOrPassport = icNoOrPassport;
	}

	public String getTruckNo() {
		return truckNo;
	}

	public void setTruckNo(String truckNo) {
		this.truckNo = truckNo;
	}

	public String getScuName() {
		return scuName;
	}

	public void setScuName(String scuName) {
		this.scuName = scuName;
	}

	public String getGateInNo() {
		return gateInNo;
	}

	public void setGateInNo(String gateInNo) {
		this.gateInNo = gateInNo;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public PrintEIRContainerInfo getContainer01() {
		return container01;
	}

	public void setContainer01(PrintEIRContainerInfo container01) {
		this.container01 = container01;
	}

	public PrintEIRContainerInfo getContainer02() {
		return container02;
	}

	public void setContainer02(PrintEIRContainerInfo container02) {
		this.container02 = container02;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public HpatReferStatus getStatus() {
		return status;
	}

	public void setStatus(HpatReferStatus status) {
		this.status = status;
	}

}
