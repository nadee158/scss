//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.11.13 at 04:52:51 PM IST 
//


package com.privasia.scss.hpat.etp.service;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for pendingSsrDetailsResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pendingSsrDetailsResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ssrList" type="{http://etp.privasia.com/etp/service/schema}ssrDetailsResponseType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pendingSsrDetailsResponseType", propOrder = {
    "ssrList"
})
public class PendingSsrDetailsResponseType {

    @XmlElement(nillable = true)
    protected List<SsrDetailsResponseType> ssrList;

    /**
     * Gets the value of the ssrList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ssrList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSsrList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SsrDetailsResponseType }
     * 
     * 
     */
    public List<SsrDetailsResponseType> getSsrList() {
        if (ssrList == null) {
            ssrList = new ArrayList<SsrDetailsResponseType>();
        }
        return this.ssrList;
    }

}
