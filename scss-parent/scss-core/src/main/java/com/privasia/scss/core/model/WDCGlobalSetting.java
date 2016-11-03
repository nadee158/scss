package com.privasia.scss.core.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "WDC_GLOBAL_SETTINGS")
@AttributeOverrides({ @AttributeOverride(name = "addBy", column = @Column(name = "ADD_BY")),
		@AttributeOverride(name = "updateBy", column = @Column(name = "UPDATE_BY")),
		@AttributeOverride(name = "dateTimeAdd", column = @Column(name = "DATETIME_ADD")),
		@AttributeOverride(name = "dateTimeUpdate", column = @Column(name = "DATETIME_UPDATE")) })
public class WDCGlobalSetting extends AuditEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "GLOBAL_CODE", nullable = false)
	private String globalCode;

	@Column(name = "GLOBAL_NAME")
	private String globalName;

	@Column(name = "GLOBAL_ITEMS")
	private int globalItems;

	@Column(name = "GLOBAL_AMOUNT")
	private BigDecimal globalAmount;

	@Column(name = "GLOBAL_STRING")
	private String globalString;

	@Column(name = "PARAM_VALUE1")
	private String paramValue1;

	public String getGlobalCode() {
		return globalCode;
	}

	public void setGlobalCode(String globalCode) {
		this.globalCode = globalCode;
	}

	public String getGlobalName() {
		return globalName;
	}

	public void setGlobalName(String globalName) {
		this.globalName = globalName;
	}

	public int getGlobalItems() {
		return globalItems;
	}

	public void setGlobalItems(int globalItems) {
		this.globalItems = globalItems;
	}

	public BigDecimal getGlobalAmount() {
		return globalAmount;
	}

	public void setGlobalAmount(BigDecimal globalAmount) {
		this.globalAmount = globalAmount;
	}

	public String getGlobalString() {
		return globalString;
	}

	public void setGlobalString(String globalString) {
		this.globalString = globalString;
	}

	public String getParamValue1() {
		return paramValue1;
	}

	public void setParamValue1(String paramValue1) {
		this.paramValue1 = paramValue1;
	}

}
