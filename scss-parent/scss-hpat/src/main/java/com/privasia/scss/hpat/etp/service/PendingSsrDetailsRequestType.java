//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.11.13 at 04:52:51 PM IST 
//


package com.privasia.scss.hpat.etp.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for pendingSsrDetailsRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pendingSsrDetailsRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ssrStatus" type="{http://etp.privasia.com/etp/service/schema}ssrStatusType"/&gt;
 *         &lt;element name="scn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="requestDateFrom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="requestDateTo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pendingSsrDetailsRequestType", propOrder = {
    "ssrStatus",
    "scn",
    "requestDateFrom",
    "requestDateTo"
})
public class PendingSsrDetailsRequestType {

    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected SsrStatusType ssrStatus;
    protected String scn;
    protected String requestDateFrom;
    protected String requestDateTo;

    /**
     * Gets the value of the ssrStatus property.
     * 
     * @return
     *     possible object is
     *     {@link SsrStatusType }
     *     
     */
    public SsrStatusType getSsrStatus() {
        return ssrStatus;
    }

    /**
     * Sets the value of the ssrStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link SsrStatusType }
     *     
     */
    public void setSsrStatus(SsrStatusType value) {
        this.ssrStatus = value;
    }

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
     * Gets the value of the requestDateFrom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestDateFrom() {
        return requestDateFrom;
    }

    /**
     * Sets the value of the requestDateFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestDateFrom(String value) {
        this.requestDateFrom = value;
    }

    /**
     * Gets the value of the requestDateTo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestDateTo() {
        return requestDateTo;
    }

    /**
     * Sets the value of the requestDateTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestDateTo(String value) {
        this.requestDateTo = value;
    }

}
