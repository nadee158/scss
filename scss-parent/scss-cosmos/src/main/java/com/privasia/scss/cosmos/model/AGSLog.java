package com.privasia.scss.cosmos.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SCSS_AGS_LOG")
@AttributeOverrides({ @AttributeOverride(name = "addBy", column = @Column(name = "ADD_BY")),
		@AttributeOverride(name = "updateBy", column = @Column(name = "UPDATE_BY")),
		@AttributeOverride(name = "dateTimeAdd", column = @Column(name = "DATE_TIME_ADD")),
		@AttributeOverride(name = "dateTimeUpdate", column = @Column(name = "DATETIME_UPDATE")) })
public class AGSLog extends AuditEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    @Id
	@Column(name = "AGS_MESSAGE_ID")
	private Long agsMessageId;

	@Column(name = "SEND_RCV")
	private String sendRCV;

	@Column(name = "PORT_NUMBER")
	private String portNumber;

	@Column(name = "XML_DATA")
	private String xmlData;

	
	public Long getAgsMessageId() {
		return agsMessageId;
	}

	public void setAgsMessageId(Long agsMessageId) {
		this.agsMessageId = agsMessageId;
	}

	public String getSendRCV() {
		return sendRCV;
	}

	public void setSendRCV(String sendRCV) {
		this.sendRCV = sendRCV;
	}

	public String getPortNumber() {
		return portNumber;
	}

	public void setPortNumber(String portNumber) {
		this.portNumber = portNumber;
	}

	public String getXmlData() {
		return xmlData;
	}

	public void setXmlData(String xmlData) {
		this.xmlData = xmlData;
	}

}
