//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.09.07 at 02:02:51 AM SGT 
//


package com.privasia.scss.etpws.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ediGatepassRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ediGatepassRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="scn" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="blNo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="containerNo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="haulageCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="haulierName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="forwardingCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="forwardingName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="freeZone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="requestBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ediGatepassRequestType", propOrder = {
    "scn",
    "blNo",
    "containerNo",
    "haulageCode",
    "haulierName",
    "forwardingCode",
    "forwardingName",
    "freeZone",
    "requestBy"
})
public class EdiGatepassRequestType {

    @XmlElement(required = true)
    protected String scn;
    @XmlElement(required = true)
    protected String blNo;
    @XmlElement(required = true)
    protected String containerNo;
    @XmlElement(required = true)
    protected String haulageCode;
    protected String haulierName;
    @XmlElement(required = true)
    protected String forwardingCode;
    protected String forwardingName;
    protected String freeZone;
    protected String requestBy;

    /**
     * Gets the value of the scn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScn() {
        return scn;
    }

    /**
     * Sets the value of the scn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScn(String value) {
        this.scn = value;
    }

    /**
     * Gets the value of the blNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBlNo() {
        return blNo;
    }

    /**
     * Sets the value of the blNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBlNo(String value) {
        this.blNo = value;
    }

    /**
     * Gets the value of the containerNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContainerNo() {
        return containerNo;
    }

    /**
     * Sets the value of the containerNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContainerNo(String value) {
        this.containerNo = value;
    }

    /**
     * Gets the value of the haulageCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHaulageCode() {
        return haulageCode;
    }

    /**
     * Sets the value of the haulageCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHaulageCode(String value) {
        this.haulageCode = value;
    }

    /**
     * Gets the value of the haulierName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHaulierName() {
        return haulierName;
    }

    /**
     * Sets the value of the haulierName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHaulierName(String value) {
        this.haulierName = value;
    }

    /**
     * Gets the value of the forwardingCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getForwardingCode() {
        return forwardingCode;
    }

    /**
     * Sets the value of the forwardingCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setForwardingCode(String value) {
        this.forwardingCode = value;
    }

    /**
     * Gets the value of the forwardingName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getForwardingName() {
        return forwardingName;
    }

    /**
     * Sets the value of the forwardingName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setForwardingName(String value) {
        this.forwardingName = value;
    }

    /**
     * Gets the value of the freeZone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFreeZone() {
        return freeZone;
    }

    /**
     * Sets the value of the freeZone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFreeZone(String value) {
        this.freeZone = value;
    }

    /**
     * Gets the value of the requestBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestBy() {
        return requestBy;
    }

    /**
     * Sets the value of the requestBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestBy(String value) {
        this.requestBy = value;
    }

}
