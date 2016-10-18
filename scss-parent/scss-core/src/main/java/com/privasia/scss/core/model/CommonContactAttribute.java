/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;


/**
 * @author Janaka
 *
 */
public class CommonContactAttribute implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String phoneOffice;
	private String faxOffice;
	private String personName;
	private String emailAddress;
	private String phoneMobile;
	private String newNRICNO;
	private String oldNRICNO;
	private String designation;
	private String postalCode;
	private String blockNo;
	private String buildingName;
	private String buildingNo;
	private String streetName01;
	private String streetName02;
	private String streetName03;
	private String city;
	private String state;
	private Country country;
	
	
	public String getPhoneOffice() {
		return phoneOffice;
	}
	public void setPhoneOffice(String phoneOffice) {
		this.phoneOffice = phoneOffice;
	}
	public String getFaxOffice() {
		return faxOffice;
	}
	public void setFaxOffice(String faxOffice) {
		this.faxOffice = faxOffice;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getPhoneMobile() {
		return phoneMobile;
	}
	public void setPhoneMobile(String phoneMobile) {
		this.phoneMobile = phoneMobile;
	}
	public String getNewNRICNO() {
		return newNRICNO;
	}
	public void setNewNRICNO(String newNRICNO) {
		this.newNRICNO = newNRICNO;
	}
	public String getOldNRICNO() {
		return oldNRICNO;
	}
	public void setOldNRICNO(String oldNRICNO) {
		this.oldNRICNO = oldNRICNO;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getBlockNo() {
		return blockNo;
	}
	public void setBlockNo(String blockNo) {
		this.blockNo = blockNo;
	}
	public String getBuildingName() {
		return buildingName;
	}
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	public String getBuildingNo() {
		return buildingNo;
	}
	public void setBuildingNo(String buildingNo) {
		this.buildingNo = buildingNo;
	}
	public String getStreetName01() {
		return streetName01;
	}
	public void setStreetName01(String streetName01) {
		this.streetName01 = streetName01;
	}
	public String getStreetName02() {
		return streetName02;
	}
	public void setStreetName02(String streetName02) {
		this.streetName02 = streetName02;
	}
	public String getStreetName03() {
		return streetName03;
	}
	public void setStreetName03(String streetName03) {
		this.streetName03 = streetName03;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
	
	

}
