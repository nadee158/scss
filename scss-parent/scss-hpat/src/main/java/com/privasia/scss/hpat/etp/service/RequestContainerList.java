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
 * <p>Java class for requestContainerList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="requestContainerList"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="containerNo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dgType" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "requestContainerList", propOrder = {
    "containerNo",
    "dgType"
})
public class RequestContainerList {

    @XmlElement(required = true)
    protected String containerNo;
    protected boolean dgType;

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
     * Gets the value of the dgType property.
     * 
     */
    public boolean isDgType() {
        return dgType;
    }

    /**
     * Sets the value of the dgType property.
     * 
     */
    public void setDgType(boolean value) {
        this.dgType = value;
    }

}
