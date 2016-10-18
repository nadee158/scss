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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Type;



/**
 * @author Janaka
 *
 */
@Entity
@Table(name="SCSS_GLOBAL_SETTINGS", uniqueConstraints={
		@UniqueConstraint(columnNames={"GLB_SYSEMAILADDR1", "GLB_SYSEMAILADDR2", "GLB_SYSEMAILADDR3"})
})
@AttributeOverrides({
    @AttributeOverride(
        name = "addBy", column = @Column( name = "CREATEDBY")),
    @AttributeOverride(
        name = "updateBy", column = @Column( name = "UPDATEDBY")),
    @AttributeOverride(
        name = "dateTimeAdd", column = @Column( name = "DATE_TIME_ADD")),
    @AttributeOverride(
        name = "dateTimeUpdate", column = @Column( name = "DATE_TIME_UPDATE"))
})
public class GlobalSetting extends AuditEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SCSS_GLOBAL_SETTINGS")
    @SequenceGenerator(name = "SEQ_SCSS_GLOBAL_SETTINGS", sequenceName = "GLOBAL_SETTINGS_SEQ")
	@Column(name = "GLOBAL_SETTINGS_SEQ")
	private Long globalSettingID;
	
	@Column(name = "GLB_MIN_USERNAME_LENGTH")
	private int minUserNameLength;
	
	@Column(name = "GLB_MIN_PWD_LENGTH")
	private int minPasswordLength;
	
	@Column(name = "GLB_SMTPIP")
	private String smtpIP;
	
	@Column(name = "GLB_SYSEMAILADDR1")
	private String systemEmailAddress01;
	
	@Column(name = "GLB_SYSEMAILADDR2")
	private String systemEmailAddress02;
	
	@Column(name = "GLB_SYSEMAILADDR3")
	private String systemEmailAddress03;
	
	@Column(name = "GLB_MAX_MC_ALLOW")
	private int maxMCAllow;
	
	@Column(name = "GLB_LAST_ARC_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime lastARCDate;
	
	@Column(name = "GLB_CARDNO")
	private String cardNo;
	
	@Column(name="GLB_CUSTOM_CHECK_BEFORE_TRANS")
	@Type(type="YES_NO")
	private boolean customCheckBeforeTransaction;
	
	@Column(name="GLB_XPRESS_LANE_REJECT")
	@Type(type="YES_NO")
	private boolean xpressLaneReject;
	
	public Long getGlobalSettingID() {
		return globalSettingID;
	}

	public void setGlobalSettingID(Long globalSettingID) {
		this.globalSettingID = globalSettingID;
	}


	public int getMinUserNameLength() {
		return minUserNameLength;
	}


	public void setMinUserNameLength(int minUserNameLength) {
		this.minUserNameLength = minUserNameLength;
	}


	public int getMinPasswordLength() {
		return minPasswordLength;
	}


	public void setMinPasswordLength(int minPasswordLength) {
		this.minPasswordLength = minPasswordLength;
	}


	public String getSmtpIP() {
		return smtpIP;
	}


	public void setSmtpIP(String smtpIP) {
		this.smtpIP = smtpIP;
	}


	public String getSystemEmailAddress01() {
		return systemEmailAddress01;
	}


	public void setSystemEmailAddress01(String systemEmailAddress01) {
		this.systemEmailAddress01 = systemEmailAddress01;
	}


	public String getSystemEmailAddress02() {
		return systemEmailAddress02;
	}


	public void setSystemEmailAddress02(String systemEmailAddress02) {
		this.systemEmailAddress02 = systemEmailAddress02;
	}


	public String getSystemEmailAddress03() {
		return systemEmailAddress03;
	}


	public void setSystemEmailAddress03(String systemEmailAddress03) {
		this.systemEmailAddress03 = systemEmailAddress03;
	}


	public int getMaxMCAllow() {
		return maxMCAllow;
	}


	public void setMaxMCAllow(int maxMCAllow) {
		this.maxMCAllow = maxMCAllow;
	}


	public LocalDateTime getLastARCDate() {
		return lastARCDate;
	}


	public void setLastARCDate(LocalDateTime lastARCDate) {
		this.lastARCDate = lastARCDate;
	}


	public String getCardNo() {
		return cardNo;
	}


	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}


	public boolean isCustomCheckBeforeTransaction() {
		return customCheckBeforeTransaction;
	}


	public void setCustomCheckBeforeTransaction(boolean customCheckBeforeTransaction) {
		this.customCheckBeforeTransaction = customCheckBeforeTransaction;
	}


	public boolean isXpressLaneReject() {
		return xpressLaneReject;
	}


	public void setXpressLaneReject(boolean xpressLaneReject) {
		this.xpressLaneReject = xpressLaneReject;
	}


}
