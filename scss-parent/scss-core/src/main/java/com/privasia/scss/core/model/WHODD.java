package com.privasia.scss.core.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SCSS_WH_ODD")
@AttributeOverrides({ @AttributeOverride(name = "addBy", column = @Column(name = "ADD_BY")),
		@AttributeOverride(name = "updateBy", column = @Column(name = "ODD_UPDATEDBY")),
		@AttributeOverride(name = "dateTimeAdd", column = @Column(name = "DATETIME_ADD")),
		@AttributeOverride(name = "dateTimeUpdate", column = @Column(name = "ODD_DATEUPDATE")) })
public class WHODD extends AuditEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "GLOBAL_CODE", nullable = false)
	private String globalCode;
	

}
