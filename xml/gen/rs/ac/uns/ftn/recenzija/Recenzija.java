//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5.1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.02.11 at 06:58:11 PM CET 
//


package rs.ac.uns.ftn.recenzija;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import rs.ac.uns.ftn.naucni_rad.Tekst;
import rs.ac.uns.ftn.uloge.Recenzent;


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
 *         &lt;element ref="{http://www.ftn.uns.ac.rs/uloge}Recenzent"/>
 *         &lt;element name="Sadrzaj">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Preporuka" type="{http://www.ftn.uns.ac.rs/recenzija}TPreporuka"/>
 *                   &lt;element name="Pitanja" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="TekstPitanja">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;minLength value="5"/>
 *                                   &lt;maxLength value="100"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="Odgovor">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;minLength value="1"/>
 *                                   &lt;maxLength value="50"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="KommentarZaAutore">
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
 *                   &lt;element name="KommentarZaUrednika">
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
 *                   &lt;element name="Dodatak">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;minLength value="10"/>
 *                         &lt;maxLength value="300"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="Id" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *       &lt;attribute name="Status" type="{http://www.ftn.uns.ac.rs/recenzija}TStatusRecenzija" default="Ceka se" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "recenzent",
    "sadrzaj"
})
@XmlRootElement(name = "Recenzija")
public class Recenzija {

    @XmlElement(name = "Recenzent", namespace = "http://www.ftn.uns.ac.rs/uloge", required = true)
    protected Recenzent recenzent;
    @XmlElement(name = "Sadrzaj", required = true)
    protected Recenzija.Sadrzaj sadrzaj;
    @XmlAttribute(name = "Id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;
    @XmlAttribute(name = "Status")
    protected TStatusRecenzija status;

    /**
     * Gets the value of the recenzent property.
     * 
     * @return
     *     possible object is
     *     {@link Recenzent }
     *     
     */
    public Recenzent getRecenzent() {
        return recenzent;
    }

    /**
     * Sets the value of the recenzent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Recenzent }
     *     
     */
    public void setRecenzent(Recenzent value) {
        this.recenzent = value;
    }

    /**
     * Gets the value of the sadrzaj property.
     * 
     * @return
     *     possible object is
     *     {@link Recenzija.Sadrzaj }
     *     
     */
    public Recenzija.Sadrzaj getSadrzaj() {
        return sadrzaj;
    }

    /**
     * Sets the value of the sadrzaj property.
     * 
     * @param value
     *     allowed object is
     *     {@link Recenzija.Sadrzaj }
     *     
     */
    public void setSadrzaj(Recenzija.Sadrzaj value) {
        this.sadrzaj = value;
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
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link TStatusRecenzija }
     *     
     */
    public TStatusRecenzija getStatus() {
        if (status == null) {
            return TStatusRecenzija.CEKA_SE;
        } else {
            return status;
        }
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link TStatusRecenzija }
     *     
     */
    public void setStatus(TStatusRecenzija value) {
        this.status = value;
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
     *         &lt;element name="Preporuka" type="{http://www.ftn.uns.ac.rs/recenzija}TPreporuka"/>
     *         &lt;element name="Pitanja" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="TekstPitanja">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;minLength value="5"/>
     *                         &lt;maxLength value="100"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="Odgovor">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;minLength value="1"/>
     *                         &lt;maxLength value="50"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="KommentarZaAutore">
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
     *         &lt;element name="KommentarZaUrednika">
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
     *         &lt;element name="Dodatak">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;minLength value="10"/>
     *               &lt;maxLength value="300"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
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
        "preporuka",
        "pitanja",
        "kommentarZaAutore",
        "kommentarZaUrednika",
        "dodatak"
    })
    public static class Sadrzaj {

        @XmlElement(name = "Preporuka", required = true)
        protected TPreporuka preporuka;
        @XmlElement(name = "Pitanja", required = true)
        protected List<Recenzija.Sadrzaj.Pitanja> pitanja;
        @XmlElement(name = "KommentarZaAutore", required = true)
        protected Recenzija.Sadrzaj.KommentarZaAutore kommentarZaAutore;
        @XmlElement(name = "KommentarZaUrednika", required = true)
        protected Recenzija.Sadrzaj.KommentarZaUrednika kommentarZaUrednika;
        @XmlElement(name = "Dodatak", required = true)
        protected String dodatak;

        /**
         * Gets the value of the preporuka property.
         * 
         * @return
         *     possible object is
         *     {@link TPreporuka }
         *     
         */
        public TPreporuka getPreporuka() {
            return preporuka;
        }

        /**
         * Sets the value of the preporuka property.
         * 
         * @param value
         *     allowed object is
         *     {@link TPreporuka }
         *     
         */
        public void setPreporuka(TPreporuka value) {
            this.preporuka = value;
        }

        /**
         * Gets the value of the pitanja property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the pitanja property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPitanja().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Recenzija.Sadrzaj.Pitanja }
         * 
         * 
         */
        public List<Recenzija.Sadrzaj.Pitanja> getPitanja() {
            if (pitanja == null) {
                pitanja = new ArrayList<Recenzija.Sadrzaj.Pitanja>();
            }
            return this.pitanja;
        }

        /**
         * Gets the value of the kommentarZaAutore property.
         * 
         * @return
         *     possible object is
         *     {@link Recenzija.Sadrzaj.KommentarZaAutore }
         *     
         */
        public Recenzija.Sadrzaj.KommentarZaAutore getKommentarZaAutore() {
            return kommentarZaAutore;
        }

        /**
         * Sets the value of the kommentarZaAutore property.
         * 
         * @param value
         *     allowed object is
         *     {@link Recenzija.Sadrzaj.KommentarZaAutore }
         *     
         */
        public void setKommentarZaAutore(Recenzija.Sadrzaj.KommentarZaAutore value) {
            this.kommentarZaAutore = value;
        }

        /**
         * Gets the value of the kommentarZaUrednika property.
         * 
         * @return
         *     possible object is
         *     {@link Recenzija.Sadrzaj.KommentarZaUrednika }
         *     
         */
        public Recenzija.Sadrzaj.KommentarZaUrednika getKommentarZaUrednika() {
            return kommentarZaUrednika;
        }

        /**
         * Sets the value of the kommentarZaUrednika property.
         * 
         * @param value
         *     allowed object is
         *     {@link Recenzija.Sadrzaj.KommentarZaUrednika }
         *     
         */
        public void setKommentarZaUrednika(Recenzija.Sadrzaj.KommentarZaUrednika value) {
            this.kommentarZaUrednika = value;
        }

        /**
         * Gets the value of the dodatak property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDodatak() {
            return dodatak;
        }

        /**
         * Sets the value of the dodatak property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDodatak(String value) {
            this.dodatak = value;
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
        public static class KommentarZaAutore {

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
        public static class KommentarZaUrednika {

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
         *         &lt;element name="TekstPitanja">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;minLength value="5"/>
         *               &lt;maxLength value="100"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="Odgovor">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;minLength value="1"/>
         *               &lt;maxLength value="50"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
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
            "tekstPitanja",
            "odgovor"
        })
        public static class Pitanja {

            @XmlElement(name = "TekstPitanja", required = true)
            protected String tekstPitanja;
            @XmlElement(name = "Odgovor", required = true)
            protected String odgovor;

            /**
             * Gets the value of the tekstPitanja property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTekstPitanja() {
                return tekstPitanja;
            }

            /**
             * Sets the value of the tekstPitanja property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTekstPitanja(String value) {
                this.tekstPitanja = value;
            }

            /**
             * Gets the value of the odgovor property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getOdgovor() {
                return odgovor;
            }

            /**
             * Sets the value of the odgovor property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setOdgovor(String value) {
                this.odgovor = value;
            }

        }

    }

}
