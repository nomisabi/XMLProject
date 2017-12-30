//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5.1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.12.30 at 11:23:51 PM CET 
//


package rs.ac.uns.ftn.uloge;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.ftn.uns.ac.rs/uloge}TKorisnik">
 *       &lt;sequence>
 *         &lt;element name="Biography" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="10"/>
 *               &lt;maxLength value="2000"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element ref="{http://www.ftn.uns.ac.rs/uloge}Institucija"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "biography",
    "institucija"
})
@XmlRootElement(name = "Autor")
public class Autor
    extends TKorisnik
{

    @XmlElement(name = "Biography")
    protected String biography;
    @XmlElement(name = "Institucija", required = true)
    protected Institucija institucija;

    /**
     * Gets the value of the biography property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBiography() {
        return biography;
    }

    /**
     * Sets the value of the biography property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBiography(String value) {
        this.biography = value;
    }

    /**
     * Gets the value of the institucija property.
     * 
     * @return
     *     possible object is
     *     {@link Institucija }
     *     
     */
    public Institucija getInstitucija() {
        return institucija;
    }

    /**
     * Sets the value of the institucija property.
     * 
     * @param value
     *     allowed object is
     *     {@link Institucija }
     *     
     */
    public void setInstitucija(Institucija value) {
        this.institucija = value;
    }

}
