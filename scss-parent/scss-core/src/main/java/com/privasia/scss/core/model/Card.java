/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.privasia.scss.core.util.constant.CardIsusedStatus;
import com.privasia.scss.core.util.constant.CardPrintStatus;
import com.privasia.scss.core.util.constant.CardStatus;
import com.privasia.scss.core.util.constant.CardValidityType;
import com.privasia.scss.core.util.constant.CompanyType;

/**
 * @author Janaka
 *
 */
@Entity
@Table(name="SCSS_CARD", uniqueConstraints={
		@UniqueConstraint(columnNames = "CRD_SCARDNO")
})
@AttributeOverrides({
    @AttributeOverride(name="addBy",
                       column=@Column(name="CRD_CREATEDBY")),
    @AttributeOverride(name="updateBy",
                       column=@Column(name="CRD_INFOUPDATEDBY")),
    @AttributeOverride(name="dateTimeAdd",
    				   column=@Column(name="CRD_DATECREATE")),
    @AttributeOverride(name="dateTimeUpdate",
                       column=@Column(name="CRD_INFOUPDATEDDATE"))
})
public class Card extends AuditEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SCSS_CARD")
    @SequenceGenerator(name = "SEQ_SCSS_CARD", sequenceName = "CRD_CARDID_SEQ")
	@Column(name = "CRD_CARDID_SEQ")
	private Long cardID;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COM_ID", nullable = false, referencedColumnName = "COM_ID_SEQ")
	private Company company;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SCU_USERID", nullable = false, referencedColumnName = "SYS_USERID_SEQ")
	private SystemUser systemUser;
	
	@Column(name = "CRD_CONTRACTEXPIRYDATE")
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime contractExpiryDate;
	
	@Column(name = "CRD_VALIDITYTYPE")
	@Enumerated(EnumType.STRING) 
	private CardValidityType cardValidityType;
	
	@Column(name = "CRD_AUTHORISEDATE")
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime authoriseDate;
	
	@Column(name = "CRD_DATEJOIN")
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime dateJoin;
	
	@Column(name = "CRD_EMAILADDR")
	private String emailAddress;
	
	@Column(name = "CRD_SCARDNO")
	private Long cardNo;
	
	@Column(name = "CRD_DESIGNATION")
	private String designation;
	
	@Column(name = "CRD_PHONEWORK")
	private String phoneOffice;
	
	@Column(name = "CRD_DATETHRU")
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime dateThrough;
	
	@Column(name = "CRD_DATESINCE")
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime dateSince;
	
	@Column(name = "CRD_CARDSTATUS")
	@Enumerated(EnumType.STRING) 
	private CardStatus cardStatus;
	
	@Column(name = "CRD_PRINTSTATUS")
	@Enumerated(EnumType.STRING) 
	private CardPrintStatus cardPrintStatus;
	
	@Column(name = "CRD_PRINTDATE")
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime printDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CRD_PRINTEDBY", nullable = true, referencedColumnName="SYS_USERID_SEQ")
	private SystemUser printedBy; 
	
	@Column(name = "CRD_ISUSED")
	@Enumerated(EnumType.STRING) 
	private CardIsusedStatus cardIssued;
	
	@Column(name = "CRD_GOVE_AGENCY")
	private String agency;
	
	@Column(name = "CRD_REF_NO")
	private String referenceNumber;
	
	@Column(name = "CRD_TYPE")
	@Enumerated(EnumType.STRING)
	private CompanyType cardType;
	
	public Long getCardID() {
		return cardID;
	}

	public void setCardID(Long cardID) {
		this.cardID = cardID;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public SystemUser getSystemUser() {
		return systemUser;
	}

	public void setSystemUser(SystemUser systemUser) {
		this.systemUser = systemUser;
	}

	public LocalDateTime getContractExpiryDate() {
		return contractExpiryDate;
	}

	public void setContractExpiryDate(LocalDateTime contractExpiryDate) {
		this.contractExpiryDate = contractExpiryDate;
	}

	public CardValidityType getCardValidityType() {
		return cardValidityType;
	}

	public void setCardValidityType(CardValidityType cardValidityType) {
		this.cardValidityType = cardValidityType;
	}

	public LocalDateTime getAuthoriseDate() {
		return authoriseDate;
	}

	public void setAuthoriseDate(LocalDateTime authoriseDate) {
		this.authoriseDate = authoriseDate;
	}

	public LocalDateTime getDateJoin() {
		return dateJoin;
	}

	public void setDateJoin(LocalDateTime dateJoin) {
		this.dateJoin = dateJoin;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Long getCardNo() {
		return cardNo;
	}

	public void setCardNo(Long cardNo) {
		this.cardNo = cardNo;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getPhoneOffice() {
		return phoneOffice;
	}

	public void setPhoneOffice(String phoneOffice) {
		this.phoneOffice = phoneOffice;
	}

	public LocalDateTime getDateThrough() {
		return dateThrough;
	}

	public void setDateThrough(LocalDateTime dateThrough) {
		this.dateThrough = dateThrough;
	}

	public LocalDateTime getDateSince() {
		return dateSince;
	}

	public void setDateSince(LocalDateTime dateSince) {
		this.dateSince = dateSince;
	}

	public CardStatus getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(CardStatus cardStatus) {
		this.cardStatus = cardStatus;
	}

	public CardPrintStatus getCardPrintStatus() {
		return cardPrintStatus;
	}

	public void setCardPrintStatus(CardPrintStatus cardPrintStatus) {
		this.cardPrintStatus = cardPrintStatus;
	}

	public LocalDateTime getPrintDate() {
		return printDate;
	}

	public void setPrintDate(LocalDateTime printDate) {
		this.printDate = printDate;
	}

	public SystemUser getPrintedBy() {
		return printedBy;
	}

	public void setPrintedBy(SystemUser printedBy) {
		this.printedBy = printedBy;
	}

	public CardIsusedStatus getCardIssued() {
		return cardIssued;
	}

	public void setCardIssued(CardIsusedStatus cardIssued) {
		this.cardIssued = cardIssued;
	}

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public CompanyType getCardType() {
		return cardType;
	}

	public void setCardType(CompanyType cardType) {
		this.cardType = cardType;
	}


}
