//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.11.13 at 05:08:53 PM IST 
//


package com.privasia.scss.hpat.etp.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for edoExpiryForLineResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="edoExpiryForLineResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="edoExpiryEnabled" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "edoExpiryForLineResponseType", propOrder = {
    "edoExpiryEnabled"
})
public class EdoExpiryForLineResponseType {

    protected boolean edoExpiryEnabled;

    /**
     * Gets the value of the edoExpiryEnabled property.
     * 
     */
    public boolean isEdoExpiryEnabled() {
        return edoExpiryEnabled;
    }

    /**
     * Sets the value of the edoExpiryEnabled property.
     * 
     */
    public void setEdoExpiryEnabled(boolean value) {
        this.edoExpiryEnabled = value;
    }

}
