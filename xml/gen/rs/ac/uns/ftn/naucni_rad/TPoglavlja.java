//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5.1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.01.31 at 05:13:24 PM CET 
//


package rs.ac.uns.ftn.naucni_rad;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TPoglavlja complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TPoglavlja">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.ftn.uns.ac.rs/naucni_rad}TReferenca">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.ftn.uns.ac.rs/naucni_rad}Poglavlje"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TPoglavlja", propOrder = {
    "poglavlje"
})
public class TPoglavlja
    extends TReferenca
{

    @XmlElement(name = "Poglavlje", required = true)
    protected Poglavlje poglavlje;

    /**
     * Gets the value of the poglavlje property.
     * 
     * @return
     *     possible object is
     *     {@link Poglavlje }
     *     
     */
    public Poglavlje getPoglavlje() {
        return poglavlje;
    }

    /**
     * Sets the value of the poglavlje property.
     * 
     * @param value
     *     allowed object is
     *     {@link Poglavlje }
     *     
     */
    public void setPoglavlje(Poglavlje value) {
        this.poglavlje = value;
    }

}
