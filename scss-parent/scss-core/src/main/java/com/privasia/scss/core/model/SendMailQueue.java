package com.privasia.scss.core.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "APP_SENDMAIL_QUEUE")
@AttributeOverrides({ @AttributeOverride(name = "addBy", column = @Column(name = "ADD_BY")),
		@AttributeOverride(name = "updateBy", column = @Column(name = "DATETIME_UPDATE")),
		@AttributeOverride(name = "dateTimeAdd", column = @Column(name = "DATETIME_ADD")),
		@AttributeOverride(name = "dateTimeUpdate", column = @Column(name = "DATEUPDATE")) })
public class SendMailQueue extends AuditEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "TIMESTAMP_ID")
	private String timeStampId;

	@Column(name = "EMAIL_TO")
	private String gateInTime;

	@Column(name = "EMAIL_CC")
	private String emailCC;

	@Column(name = "EMAIL_BCC")
	private String emailBCC;

	@Column(name = "EMAIL_FROM")
	private String emailFrom;

	@Column(name = "EMAIL_SUBJECT")
	private String emailSubject;

	@Column(name = "RETRY_COUNT")
	private int retryCount;

	@Column(name = "EMAIL_MSG_CLOB")
	@Lob
	private String emailMsgClob;

	public String getTimeStampId() {
		return timeStampId;
	}

	public void setTimeStampId(String timeStampId) {
		this.timeStampId = timeStampId;
	}

	public String getGateInTime() {
		return gateInTime;
	}

	public void setGateInTime(String gateInTime) {
		this.gateInTime = gateInTime;
	}

	public String getEmailCC() {
		return emailCC;
	}

	public void setEmailCC(String emailCC) {
		this.emailCC = emailCC;
	}

	public String getEmailBCC() {
		return emailBCC;
	}

	public void setEmailBCC(String emailBCC) {
		this.emailBCC = emailBCC;
	}

	public String getEmailFrom() {
		return emailFrom;
	}

	public void setEmailFrom(String emailFrom) {
		this.emailFrom = emailFrom;
	}

	public String getEmailSubject() {
		return emailSubject;
	}

	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	public int getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}

	public String getEmailMsgClob() {
		return emailMsgClob;
	}

	public void setEmailMsgClob(String emailMsgClob) {
		this.emailMsgClob = emailMsgClob;
	}

}
