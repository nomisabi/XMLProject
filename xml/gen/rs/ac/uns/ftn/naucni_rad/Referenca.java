//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5.1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.02.06 at 09:37:29 PM CET 
//


package rs.ac.uns.ftn.naucni_rad;

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
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element ref="{http://www.ftn.uns.ac.rs/naucni_rad}ReferencaClanak"/>
 *         &lt;element ref="{http://www.ftn.uns.ac.rs/naucni_rad}ReferencaKnjiga"/>
 *         &lt;element ref="{http://www.ftn.uns.ac.rs/naucni_rad}ReferencaWeb"/>
 *         &lt;element ref="{http://www.ftn.uns.ac.rs/naucni_rad}ReferencaNaucniRad"/>
 *         &lt;element ref="{http://www.ftn.uns.ac.rs/naucni_rad}ReferencaPoglavlja"/>
 *         &lt;element ref="{http://www.ftn.uns.ac.rs/naucni_rad}ReferencaRecenzija"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "referencaClanak",
    "referencaKnjiga",
    "referencaWeb",
    "referencaNaucniRad",
    "referencaPoglavlja",
    "referencaRecenzija"
})
@XmlRootElement(name = "Referenca")
public class Referenca {

    @XmlElement(name = "ReferencaClanak")
    protected ReferencaClanak referencaClanak;
    @XmlElement(name = "ReferencaKnjiga")
    protected ReferencaKnjiga referencaKnjiga;
    @XmlElement(name = "ReferencaWeb")
    protected ReferencaWeb referencaWeb;
    @XmlElement(name = "ReferencaNaucniRad")
    protected ReferencaNaucniRad referencaNaucniRad;
    @XmlElement(name = "ReferencaPoglavlja")
    protected ReferencaPoglavlja referencaPoglavlja;
    @XmlElement(name = "ReferencaRecenzija")
    protected ReferencaRecenzija referencaRecenzija;

    /**
     * Gets the value of the referencaClanak property.
     * 
     * @return
     *     possible object is
     *     {@link ReferencaClanak }
     *     
     */
    public ReferencaClanak getReferencaClanak() {
        return referencaClanak;
    }

    /**
     * Sets the value of the referencaClanak property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReferencaClanak }
     *     
     */
    public void setReferencaClanak(ReferencaClanak value) {
        this.referencaClanak = value;
    }

    /**
     * Gets the value of the referencaKnjiga property.
     * 
     * @return
     *     possible object is
     *     {@link ReferencaKnjiga }
     *     
     */
    public ReferencaKnjiga getReferencaKnjiga() {
        return referencaKnjiga;
    }

    /**
     * Sets the value of the referencaKnjiga property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReferencaKnjiga }
     *     
     */
    public void setReferencaKnjiga(ReferencaKnjiga value) {
        this.referencaKnjiga = value;
    }

    /**
     * Gets the value of the referencaWeb property.
     * 
     * @return
     *     possible object is
     *     {@link ReferencaWeb }
     *     
     */
    public ReferencaWeb getReferencaWeb() {
        return referencaWeb;
    }

    /**
     * Sets the value of the referencaWeb property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReferencaWeb }
     *     
     */
    public void setReferencaWeb(ReferencaWeb value) {
        this.referencaWeb = value;
    }

    /**
     * Gets the value of the referencaNaucniRad property.
     * 
     * @return
     *     possible object is
     *     {@link ReferencaNaucniRad }
     *     
     */
    public ReferencaNaucniRad getReferencaNaucniRad() {
        return referencaNaucniRad;
    }

    /**
     * Sets the value of the referencaNaucniRad property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReferencaNaucniRad }
     *     
     */
    public void setReferencaNaucniRad(ReferencaNaucniRad value) {
        this.referencaNaucniRad = value;
    }

    /**
     * Gets the value of the referencaPoglavlja property.
     * 
     * @return
     *     possible object is
     *     {@link ReferencaPoglavlja }
     *     
     */
    public ReferencaPoglavlja getReferencaPoglavlja() {
        return referencaPoglavlja;
    }

    /**
     * Sets the value of the referencaPoglavlja property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReferencaPoglavlja }
     *     
     */
    public void setReferencaPoglavlja(ReferencaPoglavlja value) {
        this.referencaPoglavlja = value;
    }

    /**
     * Gets the value of the referencaRecenzija property.
     * 
     * @return
     *     possible object is
     *     {@link ReferencaRecenzija }
     *     
     */
    public ReferencaRecenzija getReferencaRecenzija() {
        return referencaRecenzija;
    }

    /**
     * Sets the value of the referencaRecenzija property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReferencaRecenzija }
     *     
     */
    public void setReferencaRecenzija(ReferencaRecenzija value) {
        this.referencaRecenzija = value;
    }

}
