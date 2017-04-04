/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import org.apache.commons.lang3.StringUtils;


/**
 * @author Janaka
 *
 */
@Embeddable
public class CommonSealAttribute implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String seal01Origin;
	
	private String seal01Type;
	
	private String seal01Number;
	
	private String seal02Origin;
	
	private String seal02Type;
	
	private String seal02Number;

	public String getSeal01Origin() {
		return seal01Origin;
	}

	public void setSeal01Origin(String seal01Origin) {
		if(StringUtils.isNotEmpty(seal01Origin)){
			seal01Origin = seal01Origin.toUpperCase();
		}
		this.seal01Origin = seal01Origin;
	}

	public String getSeal01Type() {
		return seal01Type;
	}

	public void setSeal01Type(String seal01Type) {
		if(StringUtils.isNotEmpty(seal01Type)){
			seal01Type = seal01Type.toUpperCase();
		}
		this.seal01Type = seal01Type;
	}

	public String getSeal01Number() {
		return seal01Number;
	}

	public void setSeal01Number(String seal01Number) {
		if(StringUtils.isNotEmpty(seal01Number)){
			seal01Number = seal01Number.toUpperCase();
		}
		this.seal01Number = seal01Number;
	}

	public String getSeal02Origin() {
		return seal02Origin;
	}

	public void setSeal02Origin(String seal02Origin) {
		if(StringUtils.isNotEmpty(seal02Origin)){
			seal02Origin = seal02Origin.toUpperCase();
		}
		this.seal02Origin = seal02Origin;
	}

	public String getSeal02Type() {
		return seal02Type;
	}

	public void setSeal02Type(String seal02Type) {
		if(StringUtils.isNotEmpty(seal02Type)){
			seal02Type = seal02Type.toUpperCase();
		}
		this.seal02Type = seal02Type;
	}

	public String getSeal02Number() {
		return seal02Number;
	}

	public void setSeal02Number(String seal02Number) {
		if(StringUtils.isNotEmpty(seal02Number)){
			seal02Number = seal02Number.toUpperCase();
		}
		this.seal02Number = seal02Number;
	}
	

}
