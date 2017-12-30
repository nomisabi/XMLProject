//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5.1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.12.30 at 11:23:51 PM CET 
//


package rs.ac.uns.ftn.naucni_rad;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
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
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element ref="{http://www.ftn.uns.ac.rs/naucni_rad}ObicanTekst"/>
 *         &lt;element name="Highlight">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://www.ftn.uns.ac.rs/naucni_rad}ObicanTekst"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="BojaPozadine" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Citat">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://www.ftn.uns.ac.rs/naucni_rad}ObicanTekst"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Link">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://www.ftn.uns.ac.rs/naucni_rad}ObicanTekst"/>
 *                   &lt;element ref="{http://www.ftn.uns.ac.rs/naucni_rad}Referenca"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="MatematickaForma">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://www.ftn.uns.ac.rs/naucni_rad}ObicanTekst"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Footnote">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://www.ftn.uns.ac.rs/naucni_rad}ObicanTekst"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="Broj">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                       &lt;minInclusive value="1"/>
 *                       &lt;maxInclusive value="1000"/>
 *                     &lt;/restriction>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element ref="{http://www.ftn.uns.ac.rs/naucni_rad}Tekst"/>
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
    "obicanTekstOrHighlightOrCitat"
})
@XmlRootElement(name = "Tekst")
public class Tekst {

    @XmlElements({
        @XmlElement(name = "ObicanTekst", type = TTekst.class),
        @XmlElement(name = "Highlight", type = Tekst.Highlight.class),
        @XmlElement(name = "Citat", type = Tekst.Citat.class),
        @XmlElement(name = "Link", type = Tekst.Link.class),
        @XmlElement(name = "MatematickaForma", type = Tekst.MatematickaForma.class),
        @XmlElement(name = "Footnote", type = Tekst.Footnote.class),
        @XmlElement(name = "Tekst", type = Tekst.class)
    })
    protected List<Object> obicanTekstOrHighlightOrCitat;

    /**
     * Gets the value of the obicanTekstOrHighlightOrCitat property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the obicanTekstOrHighlightOrCitat property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getObicanTekstOrHighlightOrCitat().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TTekst }
     * {@link Tekst.Highlight }
     * {@link Tekst.Citat }
     * {@link Tekst.Link }
     * {@link Tekst.MatematickaForma }
     * {@link Tekst.Footnote }
     * {@link Tekst }
     * 
     * 
     */
    public List<Object> getObicanTekstOrHighlightOrCitat() {
        if (obicanTekstOrHighlightOrCitat == null) {
            obicanTekstOrHighlightOrCitat = new ArrayList<Object>();
        }
        return this.obicanTekstOrHighlightOrCitat;
    }


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
     *         &lt;element ref="{http://www.ftn.uns.ac.rs/naucni_rad}ObicanTekst"/>
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
        "obicanTekst"
    })
    public static class Citat {

        @XmlElement(name = "ObicanTekst", required = true)
        protected TTekst obicanTekst;

        /**
         * Gets the value of the obicanTekst property.
         * 
         * @return
         *     possible object is
         *     {@link TTekst }
         *     
         */
        public TTekst getObicanTekst() {
            return obicanTekst;
        }

        /**
         * Sets the value of the obicanTekst property.
         * 
         * @param value
         *     allowed object is
         *     {@link TTekst }
         *     
         */
        public void setObicanTekst(TTekst value) {
            this.obicanTekst = value;
        }

    }


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
     *         &lt;element ref="{http://www.ftn.uns.ac.rs/naucni_rad}ObicanTekst"/>
     *       &lt;/sequence>
     *       &lt;attribute name="Broj">
     *         &lt;simpleType>
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *             &lt;minInclusive value="1"/>
     *             &lt;maxInclusive value="1000"/>
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
        "obicanTekst"
    })
    public static class Footnote {

        @XmlElement(name = "ObicanTekst", required = true)
        protected TTekst obicanTekst;
        @XmlAttribute(name = "Broj")
        protected Integer broj;

        /**
         * Gets the value of the obicanTekst property.
         * 
         * @return
         *     possible object is
         *     {@link TTekst }
         *     
         */
        public TTekst getObicanTekst() {
            return obicanTekst;
        }

        /**
         * Sets the value of the obicanTekst property.
         * 
         * @param value
         *     allowed object is
         *     {@link TTekst }
         *     
         */
        public void setObicanTekst(TTekst value) {
            this.obicanTekst = value;
        }

        /**
         * Gets the value of the broj property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getBroj() {
            return broj;
        }

        /**
         * Sets the value of the broj property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setBroj(Integer value) {
            this.broj = value;
        }

    }


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
     *         &lt;element ref="{http://www.ftn.uns.ac.rs/naucni_rad}ObicanTekst"/>
     *       &lt;/sequence>
     *       &lt;attribute name="BojaPozadine" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "obicanTekst"
    })
    public static class Highlight {

        @XmlElement(name = "ObicanTekst", required = true)
        protected TTekst obicanTekst;
        @XmlAttribute(name = "BojaPozadine")
        protected String bojaPozadine;

        /**
         * Gets the value of the obicanTekst property.
         * 
         * @return
         *     possible object is
         *     {@link TTekst }
         *     
         */
        public TTekst getObicanTekst() {
            return obicanTekst;
        }

        /**
         * Sets the value of the obicanTekst property.
         * 
         * @param value
         *     allowed object is
         *     {@link TTekst }
         *     
         */
        public void setObicanTekst(TTekst value) {
            this.obicanTekst = value;
        }

        /**
         * Gets the value of the bojaPozadine property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBojaPozadine() {
            return bojaPozadine;
        }

        /**
         * Sets the value of the bojaPozadine property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBojaPozadine(String value) {
            this.bojaPozadine = value;
        }

    }


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
     *         &lt;element ref="{http://www.ftn.uns.ac.rs/naucni_rad}ObicanTekst"/>
     *         &lt;element ref="{http://www.ftn.uns.ac.rs/naucni_rad}Referenca"/>
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
        "obicanTekst",
        "referenca"
    })
    public static class Link {

        @XmlElement(name = "ObicanTekst", required = true)
        protected TTekst obicanTekst;
        @XmlElement(name = "Referenca", required = true)
        @XmlIDREF
        @XmlSchemaType(name = "IDREF")
        protected Object referenca;

        /**
         * Gets the value of the obicanTekst property.
         * 
         * @return
         *     possible object is
         *     {@link TTekst }
         *     
         */
        public TTekst getObicanTekst() {
            return obicanTekst;
        }

        /**
         * Sets the value of the obicanTekst property.
         * 
         * @param value
         *     allowed object is
         *     {@link TTekst }
         *     
         */
        public void setObicanTekst(TTekst value) {
            this.obicanTekst = value;
        }

        /**
         * Gets the value of the referenca property.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getReferenca() {
            return referenca;
        }

        /**
         * Sets the value of the referenca property.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setReferenca(Object value) {
            this.referenca = value;
        }

    }


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
     *         &lt;element ref="{http://www.ftn.uns.ac.rs/naucni_rad}ObicanTekst"/>
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
        "obicanTekst"
    })
    public static class MatematickaForma {

        @XmlElement(name = "ObicanTekst", required = true)
        protected TTekst obicanTekst;

        /**
         * Gets the value of the obicanTekst property.
         * 
         * @return
         *     possible object is
         *     {@link TTekst }
         *     
         */
        public TTekst getObicanTekst() {
            return obicanTekst;
        }

        /**
         * Sets the value of the obicanTekst property.
         * 
         * @param value
         *     allowed object is
         *     {@link TTekst }
         *     
         */
        public void setObicanTekst(TTekst value) {
            this.obicanTekst = value;
        }

    }

}
