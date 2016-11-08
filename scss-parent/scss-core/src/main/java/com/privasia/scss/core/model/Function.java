/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

/**
 * @author Janaka
 *
 */
@Entity
@Table(name = "SCSS_FUNCTION")
@AttributeOverrides({ @AttributeOverride(name = "addBy", column = @Column(name = "ADD_BY")),
		@AttributeOverride(name = "updateBy", column = @Column(name = "UPDATE_BY")),
		@AttributeOverride(name = "dateTimeAdd", column = @Column(name = "DATETIME_ADD")),
		@AttributeOverride(name = "dateTimeUpdate", column = @Column(name = "DATETIME_UPDATE")) })
public class Function extends AuditEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "FUNCTION_ID")
	private Long functionID;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_ID", nullable = true, referencedColumnName = "FUNCTION_ID")
	private Function parentFunction;

	@Column(name = "SORT_ORDER_ID")
	private Integer sortOrderID;

	@Column(name = "MENU_FLAG")
	@Type(type = "yes_no")
	private boolean menuFlag;

	@Column(name = "FUNCTION_DESC")
	private String description;

	public Long getFunctionID() {
		return functionID;
	}

	public void setFunctionID(Long functionID) {
		this.functionID = functionID;
	}

	public Function getParentFunction() {
		return parentFunction;
	}

	public void setParentFunction(Function parentFunction) {
		this.parentFunction = parentFunction;
	}

	public Integer getSortOrderID() {
		return sortOrderID;
	}

	public void setSortOrderID(Integer sortOrderID) {
		this.sortOrderID = sortOrderID;
	}

	public boolean isMenuFlag() {
		return menuFlag;
	}

	public void setMenuFlag(boolean menuFlag) {
		this.menuFlag = menuFlag;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
