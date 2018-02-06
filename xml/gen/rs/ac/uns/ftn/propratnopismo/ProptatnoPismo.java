//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5.1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.02.06 at 09:37:29 PM CET 
//


package rs.ac.uns.ftn.propratnopismo;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import rs.ac.uns.ftn.naucni_rad.Tekst;


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
 *         &lt;element name="Autor" type="{http://www.w3.org/2001/XMLSchema}IDREF"/>
 *         &lt;element name="Urednik" type="{http://www.w3.org/2001/XMLSchema}IDREF"/>
 *         &lt;element name="Datum" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="Sadrzaj">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;choice>
 *                   &lt;element name="Rad">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element ref="{http://www.ftn.uns.ac.rs/naucni_rad}Tekst" maxOccurs="unbounded"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="Rezultat" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element ref="{http://www.ftn.uns.ac.rs/naucni_rad}Tekst"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="Razlog" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element ref="{http://www.ftn.uns.ac.rs/naucni_rad}Tekst"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="Recenzent" type="{http://www.w3.org/2001/XMLSchema}IDREF" maxOccurs="unbounded"/>
 *                 &lt;/choice>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Potpis">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="1"/>
 *               &lt;maxLength value="100"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="Id" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "autor",
    "urednik",
    "datum",
    "sadrzaj",
    "potpis"
})
@XmlRootElement(name = "ProptatnoPismo")
public class ProptatnoPismo {

    @XmlElement(name = "Autor", required = true)
    @XmlIDREF
    @XmlSchemaType(name = "IDREF")
    protected Object autor;
    @XmlElement(name = "Urednik", required = true)
    @XmlIDREF
    @XmlSchemaType(name = "IDREF")
    protected Object urednik;
    @XmlElement(name = "Datum", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar datum;
    @XmlElement(name = "Sadrzaj", required = true)
    protected ProptatnoPismo.Sadrzaj sadrzaj;
    @XmlElement(name = "Potpis", required = true)
    protected String potpis;
    @XmlAttribute(name = "Id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;

    /**
     * Gets the value of the autor property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getAutor() {
        return autor;
    }

    /**
     * Sets the value of the autor property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setAutor(Object value) {
        this.autor = value;
    }

    /**
     * Gets the value of the urednik property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getUrednik() {
        return urednik;
    }

    /**
     * Sets the value of the urednik property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setUrednik(Object value) {
        this.urednik = value;
    }

    /**
     * Gets the value of the datum property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDatum() {
        return datum;
    }

    /**
     * Sets the value of the datum property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDatum(XMLGregorianCalendar value) {
        this.datum = value;
    }

    /**
     * Gets the value of the sadrzaj property.
     * 
     * @return
     *     possible object is
     *     {@link ProptatnoPismo.Sadrzaj }
     *     
     */
    public ProptatnoPismo.Sadrzaj getSadrzaj() {
        return sadrzaj;
    }

    /**
     * Sets the value of the sadrzaj property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProptatnoPismo.Sadrzaj }
     *     
     */
    public void setSadrzaj(ProptatnoPismo.Sadrzaj value) {
        this.sadrzaj = value;
    }

    /**
     * Gets the value of the potpis property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPotpis() {
        return potpis;
    }

    /**
     * Sets the value of the potpis property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPotpis(String value) {
        this.potpis = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
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
     *       &lt;choice>
     *         &lt;element name="Rad">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element ref="{http://www.ftn.uns.ac.rs/naucni_rad}Tekst" maxOccurs="unbounded"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="Rezultat" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element ref="{http://www.ftn.uns.ac.rs/naucni_rad}Tekst"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="Razlog" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element ref="{http://www.ftn.uns.ac.rs/naucni_rad}Tekst"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="Recenzent" type="{http://www.w3.org/2001/XMLSchema}IDREF" maxOccurs="unbounded"/>
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
        "rad",
        "rezultat",
        "razlog",
        "recenzent"
    })
    public static class Sadrzaj {

        @XmlElement(name = "Rad")
        protected ProptatnoPismo.Sadrzaj.Rad rad;
        @XmlElement(name = "Rezultat")
        protected List<ProptatnoPismo.Sadrzaj.Rezultat> rezultat;
        @XmlElement(name = "Razlog")
        protected List<ProptatnoPismo.Sadrzaj.Razlog> razlog;
        @XmlElementRef(name = "Recenzent", namespace = "http://www.ftn.uns.ac.rs/propratnoPismo", type = JAXBElement.class, required = false)
        protected List<JAXBElement<Object>> recenzent;

        /**
         * Gets the value of the rad property.
         * 
         * @return
         *     possible object is
         *     {@link ProptatnoPismo.Sadrzaj.Rad }
         *     
         */
        public ProptatnoPismo.Sadrzaj.Rad getRad() {
            return rad;
        }

        /**
         * Sets the value of the rad property.
         * 
         * @param value
         *     allowed object is
         *     {@link ProptatnoPismo.Sadrzaj.Rad }
         *     
         */
        public void setRad(ProptatnoPismo.Sadrzaj.Rad value) {
            this.rad = value;
        }

        /**
         * Gets the value of the rezultat property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the rezultat property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRezultat().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ProptatnoPismo.Sadrzaj.Rezultat }
         * 
         * 
         */
        public List<ProptatnoPismo.Sadrzaj.Rezultat> getRezultat() {
            if (rezultat == null) {
                rezultat = new ArrayList<ProptatnoPismo.Sadrzaj.Rezultat>();
            }
            return this.rezultat;
        }

        /**
         * Gets the value of the razlog property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the razlog property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRazlog().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ProptatnoPismo.Sadrzaj.Razlog }
         * 
         * 
         */
        public List<ProptatnoPismo.Sadrzaj.Razlog> getRazlog() {
            if (razlog == null) {
                razlog = new ArrayList<ProptatnoPismo.Sadrzaj.Razlog>();
            }
            return this.razlog;
        }

        /**
         * Gets the value of the recenzent property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the recenzent property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRecenzent().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link JAXBElement }{@code <}{@link Object }{@code >}
         * 
         * 
         */
        public List<JAXBElement<Object>> getRecenzent() {
            if (recenzent == null) {
                recenzent = new ArrayList<JAXBElement<Object>>();
            }
            return this.recenzent;
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
         *         &lt;element ref="{http://www.ftn.uns.ac.rs/naucni_rad}Tekst" maxOccurs="unbounded"/>
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
            "tekst"
        })
        public static class Rad {

            @XmlElement(name = "Tekst", namespace = "http://www.ftn.uns.ac.rs/naucni_rad", required = true)
            protected List<Tekst> tekst;

            /**
             * Gets the value of the tekst property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the tekst property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getTekst().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Tekst }
             * 
             * 
             */
            public List<Tekst> getTekst() {
                if (tekst == null) {
                    tekst = new ArrayList<Tekst>();
                }
                return this.tekst;
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
         *         &lt;element ref="{http://www.ftn.uns.ac.rs/naucni_rad}Tekst"/>
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
            "tekst"
        })
        public static class Razlog {

            @XmlElement(name = "Tekst", namespace = "http://www.ftn.uns.ac.rs/naucni_rad", required = true)
            protected Tekst tekst;

            /**
             * Gets the value of the tekst property.
             * 
             * @return
             *     possible object is
             *     {@link Tekst }
             *     
             */
            public Tekst getTekst() {
                return tekst;
            }

            /**
             * Sets the value of the tekst property.
             * 
             * @param value
             *     allowed object is
             *     {@link Tekst }
             *     
             */
            public void setTekst(Tekst value) {
                this.tekst = value;
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
         *         &lt;element ref="{http://www.ftn.uns.ac.rs/naucni_rad}Tekst"/>
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
            "tekst"
        })
        public static class Rezultat {

            @XmlElement(name = "Tekst", namespace = "http://www.ftn.uns.ac.rs/naucni_rad", required = true)
            protected Tekst tekst;

            /**
             * Gets the value of the tekst property.
             * 
             * @return
             *     possible object is
             *     {@link Tekst }
             *     
             */
            public Tekst getTekst() {
                return tekst;
            }

            /**
             * Sets the value of the tekst property.
             * 
             * @param value
             *     allowed object is
             *     {@link Tekst }
             *     
             */
            public void setTekst(Tekst value) {
                this.tekst = value;
            }

        }

    }

}
