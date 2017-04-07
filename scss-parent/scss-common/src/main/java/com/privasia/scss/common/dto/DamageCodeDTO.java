package com.privasia.scss.common.dto;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class DamageCodeDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String damageCode = StringUtils.EMPTY;

	private String damageDesc = StringUtils.EMPTY;

	public String getDamageCode() {
		return damageCode;
	}

	public void setDamageCode(String damageCode) {
		this.damageCode = damageCode;
	}

	public String getDamageDesc() {
		return damageDesc;
	}

	public void setDamageDesc(String damageDesc) {
		this.damageDesc = damageDesc;
	}

	@Override
	public String toString() {
		return "DamageCodeDTO [damageCode=" + damageCode + ", damageDesc=" + damageDesc + "]";
	}

	public DamageCodeDTO initializeWithDefaultValues(String damageCode) {
		this.damageCode = damageCode;
		this.damageDesc = damageCode + " Description";
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((damageCode == null) ? 0 : damageCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DamageCodeDTO other = (DamageCodeDTO) obj;
		if (damageCode == null) {
			if (other.damageCode != null)
				return false;
		} else if (!damageCode.equals(other.damageCode))
			return false;
		return true;
	}

}
