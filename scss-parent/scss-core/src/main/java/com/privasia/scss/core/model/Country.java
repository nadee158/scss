package com.privasia.scss.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


/**
 * @author Janaka
 *
 */

@Entity
@Table(name="SCSS_COUNTRY", uniqueConstraints={
		@UniqueConstraint(columnNames={"CON_CODE"}),
		@UniqueConstraint(columnNames={"CON_NAME"})
})
public class Country implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "CON_CODE")
	private String code;
	
	@Column(name = "CON_NAME")
	private String name;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
	

}
