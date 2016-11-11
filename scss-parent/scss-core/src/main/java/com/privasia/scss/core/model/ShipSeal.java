package com.privasia.scss.core.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SCSS_SHIP_SEAL")
@AttributeOverrides({ @AttributeOverride(name = "addBy", column = @Column(name = "CREATEDBY")),
		@AttributeOverride(name = "updateBy", column = @Column(name = "UPDATEBY")),
		@AttributeOverride(name = "dateTimeAdd", column = @Column(name = "DATECREATE")),
		@AttributeOverride(name = "dateTimeUpdate", column = @Column(name = "DATEUPDATE")) })
public class ShipSeal extends AuditEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "LINE_CODE")
	private String lineCode;

	@Id
	@Column(name = "RULES")
	private String rules;

	public String getLineCode() {
		return lineCode;
	}

	public void setLineCode(String lineCode) {
		this.lineCode = lineCode;
	}

	public String getRules() {
		return rules;
	}

	public void setRules(String rules) {
		this.rules = rules;
	}

}
