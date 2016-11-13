//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.11.13 at 05:08:53 PM IST 
//


package com.privasia.scss.hpat.etp.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ssrContainersType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ssrContainersType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="containerNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="length" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="height" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="boxOperator" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="remarks" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="fullEmpty" type="{http://etp.privasia.com/etp/service/schema}fullEmptyType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ssrContainersType", propOrder = {
    "containerNumber",
    "length",
    "height",
    "type",
    "boxOperator",
    "remarks",
    "fullEmpty"
})
public class SsrContainersType {

    @XmlElement(required = true)
    protected String containerNumber;
    @XmlElement(required = true)
    protected String length;
    @XmlElement(required = true)
    protected String height;
    @XmlElement(required = true)
    protected String type;
    @XmlElement(required = true)
    protected String boxOperator;
    @XmlElement(required = true)
    protected String remarks;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected FullEmptyType fullEmpty;

    /**
     * Gets the value of the containerNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContainerNumber() {
        return containerNumber;
    }

    /**
     * Sets the value of the containerNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContainerNumber(String value) {
        this.containerNumber = value;
    }

    /**
     * Gets the value of the length property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLength() {
        return length;
    }

    /**
     * Sets the value of the length property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLength(String value) {
        this.length = value;
    }

    /**
     * Gets the value of the height property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHeight() {
        return height;
    }

    /**
     * Sets the value of the height property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHeight(String value) {
        this.height = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the boxOperator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBoxOperator() {
        return boxOperator;
    }

    /**
     * Sets the value of the boxOperator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBoxOperator(String value) {
        this.boxOperator = value;
    }

    /**
     * Gets the value of the remarks property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * Sets the value of the remarks property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemarks(String value) {
        this.remarks = value;
    }

    /**
     * Gets the value of the fullEmpty property.
     * 
     * @return
     *     possible object is
     *     {@link FullEmptyType }
     *     
     */
    public FullEmptyType getFullEmpty() {
        return fullEmpty;
    }

    /**
     * Sets the value of the fullEmpty property.
     * 
     * @param value
     *     allowed object is
     *     {@link FullEmptyType }
     *     
     */
    public void setFullEmpty(FullEmptyType value) {
        this.fullEmpty = value;
    }

}
