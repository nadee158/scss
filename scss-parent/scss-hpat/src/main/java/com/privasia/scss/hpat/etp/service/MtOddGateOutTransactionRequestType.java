//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.11.13 at 06:36:43 PM SGT 
//


package com.privasia.scss.hpat.etp.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for mtOddGateOutTransactionRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="mtOddGateOutTransactionRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="fromProcessingDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="toProcessingDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="containerNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pmHeadNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="status" type="{http://etp.privasia.com/etp/service/schema}txStatusType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mtOddGateOutTransactionRequestType", propOrder = {
    "fromProcessingDate",
    "toProcessingDate",
    "containerNo",
    "pmHeadNumber",
    "status"
})
public class MtOddGateOutTransactionRequestType {

    protected String fromProcessingDate;
    protected String toProcessingDate;
    protected String containerNo;
    protected String pmHeadNumber;
    @XmlSchemaType(name = "string")
    protected TxStatusType status;

    /**
     * Gets the value of the fromProcessingDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFromProcessingDate() {
        return fromProcessingDate;
    }

    /**
     * Sets the value of the fromProcessingDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFromProcessingDate(String value) {
        this.fromProcessingDate = value;
    }

    /**
     * Gets the value of the toProcessingDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToProcessingDate() {
        return toProcessingDate;
    }

    /**
     * Sets the value of the toProcessingDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToProcessingDate(String value) {
        this.toProcessingDate = value;
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
     * Gets the value of the pmHeadNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPmHeadNumber() {
        return pmHeadNumber;
    }

    /**
     * Sets the value of the pmHeadNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPmHeadNumber(String value) {
        this.pmHeadNumber = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link TxStatusType }
     *     
     */
    public TxStatusType getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link TxStatusType }
     *     
     */
    public void setStatus(TxStatusType value) {
        this.status = value;
    }

}
