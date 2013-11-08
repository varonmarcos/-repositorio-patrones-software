
package com.integration.kallsonys.kallsonysschema.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for severity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="severity">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="FATAL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="UNKWON" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="VALIDATION" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "severity", propOrder = {
    "fatal",
    "unkwon",
    "validation"
})
public class Severity {

    @XmlElement(name = "FATAL")
    protected String fatal;
    @XmlElement(name = "UNKWON")
    protected String unkwon;
    @XmlElement(name = "VALIDATION")
    protected String validation;

    /**
     * Gets the value of the fatal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFATAL() {
        return fatal;
    }

    /**
     * Sets the value of the fatal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFATAL(String value) {
        this.fatal = value;
    }

    /**
     * Gets the value of the unkwon property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUNKWON() {
        return unkwon;
    }

    /**
     * Sets the value of the unkwon property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUNKWON(String value) {
        this.unkwon = value;
    }

    /**
     * Gets the value of the validation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVALIDATION() {
        return validation;
    }

    /**
     * Sets the value of the validation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVALIDATION(String value) {
        this.validation = value;
    }

}
