
package com.integration.kallsonys.kallsonysschema.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for pagingDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pagingDto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="maxSize" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="startPage" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="filter" type="{http://kallsonys.integration.com/kallsonysschema/types}filterlist"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pagingDto", propOrder = {
    "maxSize",
    "startPage",
    "filter"
})
public class PagingDto {

    @XmlElement(required = true)
    protected String maxSize;
    @XmlElement(required = true)
    protected String startPage;
    @XmlElement(required = true)
    protected Filterlist filter;

    /**
     * Gets the value of the maxSize property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaxSize() {
        return maxSize;
    }

    /**
     * Sets the value of the maxSize property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaxSize(String value) {
        this.maxSize = value;
    }

    /**
     * Gets the value of the startPage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStartPage() {
        return startPage;
    }

    /**
     * Sets the value of the startPage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartPage(String value) {
        this.startPage = value;
    }

    /**
     * Gets the value of the filter property.
     * 
     * @return
     *     possible object is
     *     {@link Filterlist }
     *     
     */
    public Filterlist getFilter() {
        return filter;
    }

    /**
     * Sets the value of the filter property.
     * 
     * @param value
     *     allowed object is
     *     {@link Filterlist }
     *     
     */
    public void setFilter(Filterlist value) {
        this.filter = value;
    }

}
