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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for edoDetailsRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="edoDetailsRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="scn" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="containerNo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "edoDetailsRequestType", propOrder = {
    "scn",
    "containerNo"
})
public class EdoDetailsRequestType {

    @XmlElement(required = true)
    protected String scn;
    @XmlElement(required = true)
    protected String containerNo;

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

}
