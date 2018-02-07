//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5.1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.02.07 at 02:54:46 PM CET 
//


package rs.ac.uns.ftn.naucni_rad;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Naziv" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *         &lt;element name="Oznak" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *         &lt;element ref="{http://www.ftn.uns.ac.rs/naucni_rad}Izdavac"/>
 *         &lt;element name="Godina" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="Strana">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *             &lt;minInclusive value="1"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "naziv",
    "oznak",
    "izdavac",
    "godina"
})
@XmlRootElement(name = "ReferencaClanak")
public class ReferencaClanak {

    @XmlElement(name = "Naziv", required = true)
    protected Object naziv;
    @XmlElement(name = "Oznak", required = true)
    protected Object oznak;
    @XmlElement(name = "Izdavac", required = true)
    protected Izdavac izdavac;
    @XmlElement(name = "Godina", required = true)
    protected Object godina;
    @XmlAttribute(name = "Strana")
    protected BigInteger strana;

    /**
     * Gets the value of the naziv property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getNaziv() {
        return naziv;
    }

    /**
     * Sets the value of the naziv property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setNaziv(Object value) {
        this.naziv = value;
    }

    /**
     * Gets the value of the oznak property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getOznak() {
        return oznak;
    }

    /**
     * Sets the value of the oznak property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setOznak(Object value) {
        this.oznak = value;
    }

    /**
     * Gets the value of the izdavac property.
     * 
     * @return
     *     possible object is
     *     {@link Izdavac }
     *     
     */
    public Izdavac getIzdavac() {
        return izdavac;
    }

    /**
     * Sets the value of the izdavac property.
     * 
     * @param value
     *     allowed object is
     *     {@link Izdavac }
     *     
     */
    public void setIzdavac(Izdavac value) {
        this.izdavac = value;
    }

    /**
     * Gets the value of the godina property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getGodina() {
        return godina;
    }

    /**
     * Sets the value of the godina property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setGodina(Object value) {
        this.godina = value;
    }

    /**
     * Gets the value of the strana property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getStrana() {
        return strana;
    }

    /**
     * Sets the value of the strana property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setStrana(BigInteger value) {
        this.strana = value;
    }

}
