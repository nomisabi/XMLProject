//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5.1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.02.05 at 04:36:38 PM CET 
//

package com.example.model.naucni_rad;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Poglavlje" type="{http://www.w3.org/2001/XMLSchema}IDREF"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "poglavlje"
})
@XmlRootElement(name = "ReferencaPoglavlja")
public class ReferencaPoglavlja {

    @XmlElement(name = "Poglavlje", required = true)
    @XmlIDREF
    @XmlSchemaType(name = "IDREF")
    protected Object poglavlje;

    /**
     * Gets the value of the poglavlje property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getPoglavlje() {
        return poglavlje;
    }

    /**
     * Sets the value of the poglavlje property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setPoglavlje(Object value) {
        this.poglavlje = value;
    }

}