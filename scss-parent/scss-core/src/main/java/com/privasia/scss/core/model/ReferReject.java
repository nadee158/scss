/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Type;

import com.privasia.scss.common.enums.HpabReferStatus;
import com.privasia.scss.common.enums.TOSServiceType;

/**
 * @author Janaka
 *
 */
@Entity
@Table(name = "SCSS_REFER_REJECT")
@AttributeOverrides({ @AttributeOverride(name = "addBy", column = @Column(name = "ADD_BY")),
		@AttributeOverride(name = "updateBy", column = @Column(name = "UPDATE_BY")),
		@AttributeOverride(name = "dateTimeAdd", column = @Column(name = "DATETIME_ADD")),
		@AttributeOverride(name = "dateTimeUpdate", column = @Column(name = "DATETIME_UPDATE")) })
public class ReferReject extends AuditEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SCSS_REFER_REJECT")
	@SequenceGenerator(name = "SEQ_SCSS_REFER_REJECT", sequenceName = "SEQ_SCSS_REFER_ID", allocationSize = 1)
	@Column(name = "REFER_ID")
	private Long referRejectID;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "REF_HCID", nullable = true, referencedColumnName = "COM_ID_SEQ")
	private Company company;

	@Column(name = "EXP_WEIGHT_BRIDGE", nullable = true)
	private Integer expWeightBridge;

	@Column(name = "EXP_NET_WEIGHT", nullable = true)
	private Integer expNetWeight;

	@Column(name = "STATUS_CODE", nullable = true)
	@Type(type = "com.privasia.scss.common.enumusertype.HPATReferStatusEnumUserType")
	private HpabReferStatus statusCode;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "timeGateIn", column = @Column(name = "REF_GATEIN_TIME")),
			@AttributeOverride(name = "timeGateInOk", column = @Column(name = "REF_GATEIN_OK_TIME")),
			@AttributeOverride(name = "timeGateOut", column = @Column(name = "REF_GATEOUT_TIME")),
			@AttributeOverride(name = "timeGateOutOk", column = @Column(name = "REF_GATEOUT_OK_TIME")),
			@AttributeOverride(name = "eirStatus", column = @Column(name = "REF_EIRSTATUS")),
			@AttributeOverride(name = "pmHeadNo", column = @Column(name = "PM_HEAD_NO")),
			@AttributeOverride(name = "pmPlateNo", column = @Column(name = "EXP_TRUCK_PLATE_NO")),
			@AttributeOverride(name = "transactionSlipPrinted", column = @Column(name = "TRANSACTION_SLIP_PRINTED", nullable = true)),
			@AttributeOverride(name = "gateOutBoothNo", column = @Column(name = "REF_GATE_OUT_BOOTH_NO")),
			@AttributeOverride(name = "gateOutBoothClerk", column = @Column(name = "REF_GATEOUT_BOOTH_CLERKID")),
			@AttributeOverride(name = "timeGateOutBooth", column = @Column(name = "REF_TIMEGATEOUT_BOOTH")),
			@AttributeOverride(name = "hpabBooking", column = @Column(name = "BOOKING_ID"))

	})
	@AssociationOverrides({
			@AssociationOverride(name = "card", joinColumns = @JoinColumn(name = "CRD_CARDID_SEQ", referencedColumnName = "CRD_CARDID_SEQ")),
			@AssociationOverride(name = "gateInClerk", joinColumns = @JoinColumn(name = "GATE_CLERK_ID", referencedColumnName = "SYS_USERID_SEQ")),
			@AssociationOverride(name = "gateOutClerk", joinColumns = @JoinColumn(name = "REF_GATEOUT_CLERKID", referencedColumnName = "SYS_USERID_SEQ", nullable = true)),
			@AssociationOverride(name = "gateInClient", joinColumns = @JoinColumn(name = "CLI_CLIENT_SEQ", referencedColumnName = "CLI_CLIENTID_SEQ")),
			@AssociationOverride(name = "gateOutClient", joinColumns = @JoinColumn(name = "CLI_CLIENTID_GATEOUT", referencedColumnName = "CLI_CLIENTID_SEQ", nullable = true)),
			@AssociationOverride(name = "gateOutBoothClerk", joinColumns = @JoinColumn(name = "REF_GATEOUT_BOOTH_CLERKID", referencedColumnName = "SYS_USERID_SEQ", nullable = true)) })
	private BaseCommonGateInOutAttribute baseCommonGateInOut;

	@Column(name = "REFER_DATE_TIME")
	private LocalDateTime referDateTime;

	@Column(name = "PM_WEIGHT")
	private Integer pmWeight;

	@Column(name = "TRAILER_WEIGHT")
	private Integer trailerWeight;

	@Column(name = "TRAILER_PLATE_NO")
	private String trailerPlateNo;

	@Column(name = "AXLE_VERIFIED", nullable = true)
	@Type(type = "yes_no")
	private Boolean axleVerified;

	@Column(name = "PM_VERIFIED", nullable = true)
	@Type(type = "yes_no")
	private Boolean pmVerified;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "referReject", cascade = CascadeType.ALL)
	private Set<ReferRejectDetail> referRejectDetails;

	@Column(name = "TOS_SERVICE_TYPE", nullable = true)
	@Type(type = "com.privasia.scss.common.enumusertype.TOSServiceTypeEnumUserType")
	private TOSServiceType tosServiceType;

	public Long getReferRejectID() {
		return referRejectID;
	}

	public void setReferRejectID(Long referRejectID) {
		this.referRejectID = referRejectID;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Integer getExpWeightBridge() {
		return expWeightBridge;
	}

	public void setExpWeightBridge(Integer expWeightBridge) {
		this.expWeightBridge = expWeightBridge;
	}

	public Integer getExpNetWeight() {
		return expNetWeight;
	}

	public void setExpNetWeight(Integer expNetWeight) {
		this.expNetWeight = expNetWeight;
	}

	public HpabReferStatus getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(HpabReferStatus statusCode) {
		this.statusCode = statusCode;
	}

	public LocalDateTime getReferDateTime() {
		return referDateTime;
	}

	public void setReferDateTime(LocalDateTime referDateTime) {
		this.referDateTime = referDateTime;
	}

	public Integer getPmWeight() {
		return pmWeight;
	}

	public void setPmWeight(Integer pmWeight) {
		this.pmWeight = pmWeight;
	}

	public Integer getTrailerWeight() {
		return trailerWeight;
	}

	public void setTrailerWeight(Integer trailerWeight) {
		this.trailerWeight = trailerWeight;
	}

	public String getTrailerPlateNo() {
		return trailerPlateNo;
	}

	public void setTrailerPlateNo(String trailerPlateNo) {
		if (StringUtils.isNotEmpty(trailerPlateNo)) {
			trailerPlateNo = StringUtils.upperCase(trailerPlateNo);
		}
		this.trailerPlateNo = trailerPlateNo;
	}

	public Boolean isAxleVerified() {
		return axleVerified;
	}

	public void setAxleVerified(Boolean axleVerified) {
		this.axleVerified = axleVerified;
	}

	public Boolean isPmVerified() {
		return pmVerified;
	}

	public void setPmVerified(Boolean pmVerified) {
		this.pmVerified = pmVerified;
	}

	public BaseCommonGateInOutAttribute getBaseCommonGateInOut() {
		return baseCommonGateInOut;
	}

	public void setBaseCommonGateInOut(BaseCommonGateInOutAttribute baseCommonGateInOut) {
		this.baseCommonGateInOut = baseCommonGateInOut;
	}

	public Set<ReferRejectDetail> getReferRejectDetails() {
		return referRejectDetails;
	}

	public void setReferRejectDetails(Set<ReferRejectDetail> referRejectDetails) {
		this.referRejectDetails = referRejectDetails;
	}

	public Boolean getAxleVerified() {
		return axleVerified;
	}

	public Boolean getPmVerified() {
		return pmVerified;
	}

	public TOSServiceType getTosServiceType() {
		return tosServiceType;
	}

	public void setTosServiceType(TOSServiceType tosServiceType) {
		this.tosServiceType = tosServiceType;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
