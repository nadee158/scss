/**
 * 
 */
package com.privasia.scss.common.dto;

import java.io.Serializable;

/**
 * @author Janaka
 *
 */
public class CommonSealDTO implements Serializable {

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

	private String opusSeal01Origin;

	private String opusSeal01Type;

	private String opuSeal01Number;

	private String opusSeal02Origin;

	private String opusSeal02Type;

	private String opusSeal02Number;

	public String getSeal01Origin() {
		return seal01Origin;
	}

	public void setSeal01Origin(String seal01Origin) {
		this.seal01Origin = seal01Origin;
	}

	public String getSeal01Type() {
		return seal01Type;
	}

	public void setSeal01Type(String seal01Type) {
		this.seal01Type = seal01Type;
	}

	public String getSeal01Number() {
		return seal01Number;
	}

	public void setSeal01Number(String seal01Number) {
		this.seal01Number = seal01Number;
	}

	public String getSeal02Origin() {
		return seal02Origin;
	}

	public void setSeal02Origin(String seal02Origin) {
		this.seal02Origin = seal02Origin;
	}

	public String getSeal02Type() {
		return seal02Type;
	}

	public void setSeal02Type(String seal02Type) {
		this.seal02Type = seal02Type;
	}

	public String getSeal02Number() {
		return seal02Number;
	}

	public void setSeal02Number(String seal02Number) {
		this.seal02Number = seal02Number;
	}

	public String getOpusSeal01Origin() {
		return opusSeal01Origin;
	}

	public void setOpusSeal01Origin(String opusSeal01Origin) {
		this.opusSeal01Origin = opusSeal01Origin;
	}

	public String getOpusSeal01Type() {
		return opusSeal01Type;
	}

	public void setOpusSeal01Type(String opusSeal01Type) {
		this.opusSeal01Type = opusSeal01Type;
	}

	public String getOpuSeal01Number() {
		return opuSeal01Number;
	}

	public void setOpuSeal01Number(String opuSeal01Number) {
		this.opuSeal01Number = opuSeal01Number;
	}

	public String getOpusSeal02Origin() {
		return opusSeal02Origin;
	}

	public void setOpusSeal02Origin(String opusSeal02Origin) {
		this.opusSeal02Origin = opusSeal02Origin;
	}

	public String getOpusSeal02Type() {
		return opusSeal02Type;
	}

	public void setOpusSeal02Type(String opusSeal02Type) {
		this.opusSeal02Type = opusSeal02Type;
	}

	public String getOpusSeal02Number() {
		return opusSeal02Number;
	}

	public void setOpusSeal02Number(String opusSeal02Number) {
		this.opusSeal02Number = opusSeal02Number;
	}

	public CommonSealDTO initializeWithDefaultValues() {
		this.seal01Origin = "L";

		this.seal01Type = "SL";

		this.seal01Number = "B109612";

		this.seal02Origin = "S";

		this.seal02Type = "SL";

		this.seal02Number = "MY0338571";
		return this;
	}

}
