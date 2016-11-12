package com.privasia.scss.core.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import org.hibernate.annotations.Type;

import com.privasia.scss.core.util.constant.GateInOutStatus;

@Embeddable
public class PrintEIRContainerInfo extends CommonContainerAttribute implements Serializable {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;

	private String containerBayCode;
	
	@Type(type = "com.privasia.scss.core.util.enumusertype.GateInOutStatusEnumUserType")
	private GateInOutStatus containerInOrOut;

	private String containerPositionOnTruck;

	private String containerLine;

	private String containerHeight;

	private String containerNetWeight;

	private String containerType;

	private String containerLineInfo1;

	private String containerLineinfo2;

	private String containerSeal;

	public String getContainerBayCode() {
		return containerBayCode;
	}

	public void setContainerBayCode(String containerBayCode) {
		this.containerBayCode = containerBayCode;
	}

	public GateInOutStatus getContainerInOrOut() {
		return containerInOrOut;
	}

	public void setContainerInOrOut(GateInOutStatus containerInOrOut) {
		this.containerInOrOut = containerInOrOut;
	}

	public String getContainerPositionOnTruck() {
		return containerPositionOnTruck;
	}

	public void setContainerPositionOnTruck(String containerPositionOnTruck) {
		this.containerPositionOnTruck = containerPositionOnTruck;
	}

	public String getContainerLine() {
		return containerLine;
	}

	public void setContainerLine(String containerLine) {
		this.containerLine = containerLine;
	}

	public String getContainerHeight() {
		return containerHeight;
	}

	public void setContainerHeight(String containerHeight) {
		this.containerHeight = containerHeight;
	}

	public String getContainerNetWeight() {
		return containerNetWeight;
	}

	public void setContainerNetWeight(String containerNetWeight) {
		this.containerNetWeight = containerNetWeight;
	}

	public String getContainerType() {
		return containerType;
	}

	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	public String getContainerLineInfo1() {
		return containerLineInfo1;
	}

	public void setContainerLineInfo1(String containerLineInfo1) {
		this.containerLineInfo1 = containerLineInfo1;
	}

	public String getContainerLineinfo2() {
		return containerLineinfo2;
	}

	public void setContainerLineinfo2(String containerLineinfo2) {
		this.containerLineinfo2 = containerLineinfo2;
	}

	public String getContainerSeal() {
		return containerSeal;
	}

	public void setContainerSeal(String containerSeal) {
		this.containerSeal = containerSeal;
	}

}
