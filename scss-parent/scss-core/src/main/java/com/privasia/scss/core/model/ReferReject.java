/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

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

import org.hibernate.annotations.Type;

import com.privasia.scss.core.util.constant.HpatReferStatus;

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
	@SequenceGenerator(name = "SEQ_SCSS_REFER_REJECT", sequenceName = "SEQ_SCSS_REFER_ID")
	@Column(name = "REFER_ID")
	private Long referRejectID;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "REF_HCID", nullable = true, referencedColumnName = "COM_ID_SEQ")
	private Company company;

	@Column(name = "EXP_WEIGHT_BRIDGE")
	private int expWeightBridge;

	@Column(name = "EXP_NET_WEIGHT")
	private int expNetWeight;

	@Column(name = "STATUS_CODE")
	@Type(type = "com.privasia.scss.core.util.enumusertype.HPATReferStatusEnumUserType")
	private HpatReferStatus statusCode;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "gateInClerk", column = @Column(name = "GATE_CLERK_ID")),
			@AttributeOverride(name = "timeGateIn", column = @Column(name = "REF_GATEIN_TIME")),
			@AttributeOverride(name = "timeGateInOk", column = @Column(name = "REF_GATEIN_OK_TIME")),
			@AttributeOverride(name = "gateOutClerk", column = @Column(name = "REF_GATEOUT_CLERKID")),
			@AttributeOverride(name = "timeGateOut", column = @Column(name = "REF_GATEOUT_TIME")),
			@AttributeOverride(name = "timeGateOutOk", column = @Column(name = "REF_GATEOUT_OK_TIME")),
			@AttributeOverride(name = "eirStatus", column = @Column(name = "REF_EIRSTATUS")),
			@AttributeOverride(name = "gateInClient", column = @Column(name = "CLI_CLIENT_SEQ")),
			@AttributeOverride(name = "gateOutClient", column = @Column(name = "CLI_CLIENTID_GATEOUT")),
			@AttributeOverride(name = "pmHeadNo", column = @Column(name = "PM_HEAD_NO")),
			@AttributeOverride(name = "pmPlateNo", column = @Column(name = "EXP_TRUCK_PLATE_NO")),
			@AttributeOverride(name = "transactionSlipPrinted", column = @Column(name = "TRANSACTION_SLIP_PRINTED")),
			@AttributeOverride(name = "gateOutBoothNo", column = @Column(name = "REF_GATE_OUT_BOOTH_NO")),
			@AttributeOverride(name = "gateOutBoothClerk", column = @Column(name = "REF_GATEOUT_BOOTH_CLERKID")),
			@AttributeOverride(name = "timeGateOutBooth", column = @Column(name = "REF_TIMEGATEOUT_BOOTH")),
			@AttributeOverride(name = "hpatBooking", column = @Column(name = "BOOKING_ID"))

	})
	private BaseCommonGateInOutAttribute baseCommonGateInOut;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "CRD_CARDID_SEQ", nullable = true, referencedColumnName = "CRD_CARDID_SEQ")
	private Card card;

	@Column(name = "REFER_DATE_TIME")
	private LocalDateTime referDateTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUG_ID_SEQ", nullable = true, referencedColumnName = "CUG_ID_SEQ")
	private CardUsage cardUsage;

	@Column(name = "PM_WEIGHT")
	private int pmWeight;

	@Column(name = "TRAILER_WEIGHT")
	private int trailerWeight;

	@Column(name = "TRAILER_PLATE_NO")
	private String trailerPlateNo;

	@Column(name = "AXLE_VERIFIED")
	@Type(type = "yes_no")
	private boolean axleVerified;

	@Column(name = "PM_VERIFIED")
	@Type(type = "yes_no")
	private boolean pmVerified;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "referReject", cascade = CascadeType.ALL)
	private Set<ReferRejectDetail> referRejectDetails;

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

	public int getExpWeightBridge() {
		return expWeightBridge;
	}

	public void setExpWeightBridge(int expWeightBridge) {
		this.expWeightBridge = expWeightBridge;
	}

	public int getExpNetWeight() {
		return expNetWeight;
	}

	public void setExpNetWeight(int expNetWeight) {
		this.expNetWeight = expNetWeight;
	}

	public HpatReferStatus getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(HpatReferStatus statusCode) {
		this.statusCode = statusCode;
	}

	public BaseCommonGateInOutAttribute getBaseCommonGateInOut() {
		return baseCommonGateInOut;
	}

	public void setBaseCommonGateInOut(BaseCommonGateInOutAttribute baseCommonGateInOut) {
		this.baseCommonGateInOut = baseCommonGateInOut;
	}

	public LocalDateTime getReferDateTime() {
		return referDateTime;
	}

	public void setReferDateTime(LocalDateTime referDateTime) {
		this.referDateTime = referDateTime;
	}

	public CardUsage getCardUsage() {
		return cardUsage;
	}

	public void setCardUsage(CardUsage cardUsage) {
		this.cardUsage = cardUsage;
	}

	public int getPmWeight() {
		return pmWeight;
	}

	public void setPmWeight(int pmWeight) {
		this.pmWeight = pmWeight;
	}

	public int getTrailerWeight() {
		return trailerWeight;
	}

	public void setTrailerWeight(int trailerWeight) {
		this.trailerWeight = trailerWeight;
	}

	public String getTrailerPlateNo() {
		return trailerPlateNo;
	}

	public void setTrailerPlateNo(String trailerPlateNo) {
		this.trailerPlateNo = trailerPlateNo;
	}

	public boolean isAxleVerified() {
		return axleVerified;
	}

	public void setAxleVerified(boolean axleVerified) {
		this.axleVerified = axleVerified;
	}

	public boolean isPmVerified() {
		return pmVerified;
	}

	public void setPmVerified(boolean pmVerified) {
		this.pmVerified = pmVerified;
	}

	public Set<ReferRejectDetail> getReferRejectDetails() {
		return referRejectDetails;
	}

	public void setReferRejectDetails(Set<ReferRejectDetail> referRejectDetails) {
		this.referRejectDetails = referRejectDetails;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

}
