package com.privasia.scss.cosmos.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.privasia.scss.common.enums.AGSMessageStatus;

@Entity
@Table(name = "SCSS_AGS_LOG")
@AttributeOverrides({ @AttributeOverride(name = "addBy", column = @Column(name = "ADD_BY")),
		@AttributeOverride(name = "updateBy", column = @Column(name = "UPDATE_BY")),
		@AttributeOverride(name = "dateTimeAdd", column = @Column(name = "DATE_TIME_ADD")),
		@AttributeOverride(name = "dateTimeUpdate", column = @Column(name = "DATETIME_UPDATE")) })
public class AGSLog extends AuditEntity  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_AGS_LOG")
	@SequenceGenerator(name = "SEQ_AGS_LOG", sequenceName = "AGS_MESSAGE_ID_SEQ", allocationSize = 1)
    @Column(name = "AGS_MESSAGE_ID")
	private Long agsMessageId;

	@Column(name = "SEND_RCV")
	@Type(type = "com.privasia.scss.common.enumusertype.AGSMessageStatusEnumUserType")
	private AGSMessageStatus sendRCV;

	@Column(name = "PORT_NUMBER")
	private Integer portNumber;

	@Column(name = "XML_DATA")
	private String xmlData;

	
	public Long getAgsMessageId() {
		return agsMessageId;
	}

	public void setAgsMessageId(Long agsMessageId) {
		this.agsMessageId = agsMessageId;
	}

	public AGSMessageStatus getSendRCV() {
		return sendRCV;
	}

	public void setSendRCV(AGSMessageStatus sendRCV) {
		this.sendRCV = sendRCV;
	}

	public Integer getPortNumber() {
		return portNumber;
	}

	public void setPortNumber(Integer portNumber) {
		this.portNumber = portNumber;
	}

	public String getXmlData() {
		return xmlData;
	}

	public void setXmlData(String xmlData) {
		this.xmlData = xmlData;
	}

}
