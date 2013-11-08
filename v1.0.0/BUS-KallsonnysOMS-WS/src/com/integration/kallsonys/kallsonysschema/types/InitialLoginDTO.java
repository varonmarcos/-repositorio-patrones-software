
package com.integration.kallsonys.kallsonysschema.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for initialLoginDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="initialLoginDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="customerInfo" type="{http://kallsonys.integration.com/kallsonysschema/types}customer"/>
 *         &lt;element name="tgt" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "initialLoginDTO", propOrder = {
    "customerInfo",
    "tgt"
})
public class InitialLoginDTO {

    @XmlElement(required = true)
    protected Customer customerInfo;
    @XmlElement(required = true)
    protected String tgt;

    /**
     * Gets the value of the customerInfo property.
     * 
     * @return
     *     possible object is
     *     {@link Customer }
     *     
     */
    public Customer getCustomerInfo() {
        return customerInfo;
    }

    /**
     * Sets the value of the customerInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Customer }
     *     
     */
    public void setCustomerInfo(Customer value) {
        this.customerInfo = value;
    }

    /**
     * Gets the value of the tgt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTgt() {
        return tgt;
    }

    /**
     * Sets the value of the tgt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTgt(String value) {
        this.tgt = value;
    }

}
