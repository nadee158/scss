//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.11.13 at 04:52:51 PM IST 
//


package com.privasia.scss.hpat.etp.service;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for edoStatusType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="edoStatusType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="BLOCK"/&gt;
 *     &lt;enumeration value="CLEAR"/&gt;
 *     &lt;enumeration value="PCLEAR"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "edoStatusType")
@XmlEnum
public enum EdoStatusType {

    BLOCK,
    CLEAR,
    PCLEAR;

    public String value() {
        return name();
    }

    public static EdoStatusType fromValue(String v) {
        return valueOf(v);
    }

}
