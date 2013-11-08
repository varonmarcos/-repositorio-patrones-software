
package com.integration.kallsonys.kallsonysschema.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for top5 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="top5">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="top1" type="{http://kallsonys.integration.com/kallsonysschema/types}product"/>
 *         &lt;element name="top2" type="{http://kallsonys.integration.com/kallsonysschema/types}product"/>
 *         &lt;element name="top3" type="{http://kallsonys.integration.com/kallsonysschema/types}product"/>
 *         &lt;element name="top4" type="{http://kallsonys.integration.com/kallsonysschema/types}product"/>
 *         &lt;element name="top5" type="{http://kallsonys.integration.com/kallsonysschema/types}product"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "top5", propOrder = {
    "top1",
    "top2",
    "top3",
    "top4",
    "top5"
})
public class Top5 {

    @XmlElement(required = true)
    protected Product top1;
    @XmlElement(required = true)
    protected Product top2;
    @XmlElement(required = true)
    protected Product top3;
    @XmlElement(required = true)
    protected Product top4;
    @XmlElement(required = true)
    protected Product top5;

    /**
     * Gets the value of the top1 property.
     * 
     * @return
     *     possible object is
     *     {@link Product }
     *     
     */
    public Product getTop1() {
        return top1;
    }

    /**
     * Sets the value of the top1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Product }
     *     
     */
    public void setTop1(Product value) {
        this.top1 = value;
    }

    /**
     * Gets the value of the top2 property.
     * 
     * @return
     *     possible object is
     *     {@link Product }
     *     
     */
    public Product getTop2() {
        return top2;
    }

    /**
     * Sets the value of the top2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Product }
     *     
     */
    public void setTop2(Product value) {
        this.top2 = value;
    }

    /**
     * Gets the value of the top3 property.
     * 
     * @return
     *     possible object is
     *     {@link Product }
     *     
     */
    public Product getTop3() {
        return top3;
    }

    /**
     * Sets the value of the top3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Product }
     *     
     */
    public void setTop3(Product value) {
        this.top3 = value;
    }

    /**
     * Gets the value of the top4 property.
     * 
     * @return
     *     possible object is
     *     {@link Product }
     *     
     */
    public Product getTop4() {
        return top4;
    }

    /**
     * Sets the value of the top4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Product }
     *     
     */
    public void setTop4(Product value) {
        this.top4 = value;
    }

    /**
     * Gets the value of the top5 property.
     * 
     * @return
     *     possible object is
     *     {@link Product }
     *     
     */
    public Product getTop5() {
        return top5;
    }

    /**
     * Sets the value of the top5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Product }
     *     
     */
    public void setTop5(Product value) {
        this.top5 = value;
    }

}
