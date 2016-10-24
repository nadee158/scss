/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;

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

import com.privasia.scss.core.util.constant.CardUsageStatus;

/**
 * @author Janaka
 *
 */
@Entity
@Table(name="SCSS_CARD_USAGE")
@AttributeOverrides({
    @AttributeOverride(
        name = "addBy", column = @Column( name = "CUG_CREATEDBY")),
    @AttributeOverride(
        name = "updateBy", column = @Column( name = "CUG_UPDATEDBY")),
    @AttributeOverride(
        name = "dateTimeAdd", column = @Column( name = "CUG_TIME_START")),
    @AttributeOverride(
        name = "dateTimeUpdate", column = @Column( name = "CUG_TIME_END"))
})
public class CardUsage extends AuditEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SCSS_CLIENT")
    @SequenceGenerator(name = "SEQ_SCSS_CLIENT", sequenceName = "CLI_CLIENTID_SEQ")
	@Column(name = "CUG_ID_SEQ")
	private Long cardUsageID;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CRD_CARDID", nullable = false, referencedColumnName = "CRD_CARDID_SEQ")
	private Card card;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CLI_CLIENTID", nullable = false, referencedColumnName = "CLI_CLIENTID_SEQ")
	private Client client;
	
	@Column(name = "CUG_STATUS")
	@Enumerated(EnumType.STRING) 
	private CardUsageStatus usageStatus;
	
	@Column(name = "EXP_WEIGHT_BRIDGE")
	private int expWeightBridge;
	
	public Long getCardUsageID() {
		return cardUsageID;
	}

	public void setCardUsageID(Long cardUsageID) {
		this.cardUsageID = cardUsageID;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public CardUsageStatus getUsageStatus() {
		return usageStatus;
	}

	public void setUsageStatus(CardUsageStatus usageStatus) {
		this.usageStatus = usageStatus;
	}

	public int getExpWeightBridge() {
		return expWeightBridge;
	}

	public void setExpWeightBridge(int expWeightBridge) {
		this.expWeightBridge = expWeightBridge;
	}

	
}
