//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5.1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.02.11 at 06:58:11 PM CET 
//


package rs.ac.uns.ftn.naucni_rad;

import java.math.BigInteger;
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
import rs.ac.uns.ftn.propratnopismo.ProptatnoPismo;
import rs.ac.uns.ftn.recenzija.Recenzija;
import rs.ac.uns.ftn.uloge.Autor;


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
 *         &lt;element name="Naslov">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="1000"/>
 *               &lt;minLength value="5"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element ref="{http://www.ftn.uns.ac.rs/uloge}Autor" maxOccurs="unbounded"/>
 *         &lt;element name="Abstrakt">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;choice>
 *                   &lt;element ref="{http://www.ftn.uns.ac.rs/naucni_rad}NeStrukturiranAbstrakt"/>
 *                   &lt;element ref="{http://www.ftn.uns.ac.rs/naucni_rad}StukturiranAbstakt"/>
 *                 &lt;/choice>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Kljucna_rec" maxOccurs="unbounded">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="50"/>
 *               &lt;minLength value="1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Sadrzaj">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://www.ftn.uns.ac.rs/naucni_rad}Poglavlje" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element ref="{http://www.ftn.uns.ac.rs/naucni_rad}Referenca" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.ftn.uns.ac.rs/recenzija}Recenzija" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.ftn.uns.ac.rs/propratnoPismo}ProptatnoPismo" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="Status" type="{http://www.ftn.uns.ac.rs/naucni_rad}TStatus" default="Poslat" />
 *       &lt;attribute name="Duzina" type="{http://www.w3.org/2001/XMLSchema}integer" />
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
    "naslov",
    "autor",
    "abstrakt",
    "kljucnaRec",
    "sadrzaj",
    "referenca",
    "recenzija",
    "proptatnoPismo"
})
@XmlRootElement(name = "Revizija")
public class Revizija {

    @XmlElement(name = "Naslov", required = true)
    protected String naslov;
    @XmlElement(name = "Autor", namespace = "http://www.ftn.uns.ac.rs/uloge", required = true)
    protected List<Autor> autor;
    @XmlElement(name = "Abstrakt", required = true)
    protected Revizija.Abstrakt abstrakt;
    @XmlElement(name = "Kljucna_rec", required = true)
    protected List<String> kljucnaRec;
    @XmlElement(name = "Sadrzaj", required = true)
    protected Revizija.Sadrzaj sadrzaj;
    @XmlElement(name = "Referenca")
    protected List<Referenca> referenca;
    @XmlElement(name = "Recenzija", namespace = "http://www.ftn.uns.ac.rs/recenzija")
    protected List<Recenzija> recenzija;
    @XmlElement(name = "ProptatnoPismo", namespace = "http://www.ftn.uns.ac.rs/propratnoPismo")
    protected ProptatnoPismo proptatnoPismo;
    @XmlAttribute(name = "Status")
    protected TStatus status;
    @XmlAttribute(name = "Duzina")
    protected BigInteger duzina;
    @XmlAttribute(name = "Id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;

    /**
     * Gets the value of the naslov property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNaslov() {
        return naslov;
    }

    /**
     * Sets the value of the naslov property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNaslov(String value) {
        this.naslov = value;
    }

    /**
     * Gets the value of the autor property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the autor property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAutor().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Autor }
     * 
     * 
     */
    public List<Autor> getAutor() {
        if (autor == null) {
            autor = new ArrayList<Autor>();
        }
        return this.autor;
    }

    /**
     * Gets the value of the abstrakt property.
     * 
     * @return
     *     possible object is
     *     {@link Revizija.Abstrakt }
     *     
     */
    public Revizija.Abstrakt getAbstrakt() {
        return abstrakt;
    }

    /**
     * Sets the value of the abstrakt property.
     * 
     * @param value
     *     allowed object is
     *     {@link Revizija.Abstrakt }
     *     
     */
    public void setAbstrakt(Revizija.Abstrakt value) {
        this.abstrakt = value;
    }

    /**
     * Gets the value of the kljucnaRec property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the kljucnaRec property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKljucnaRec().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getKljucnaRec() {
        if (kljucnaRec == null) {
            kljucnaRec = new ArrayList<String>();
        }
        return this.kljucnaRec;
    }

    /**
     * Gets the value of the sadrzaj property.
     * 
     * @return
     *     possible object is
     *     {@link Revizija.Sadrzaj }
     *     
     */
    public Revizija.Sadrzaj getSadrzaj() {
        return sadrzaj;
    }

    /**
     * Sets the value of the sadrzaj property.
     * 
     * @param value
     *     allowed object is
     *     {@link Revizija.Sadrzaj }
     *     
     */
    public void setSadrzaj(Revizija.Sadrzaj value) {
        this.sadrzaj = value;
    }

    /**
     * Gets the value of the referenca property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the referenca property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReferenca().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Referenca }
     * 
     * 
     */
    public List<Referenca> getReferenca() {
        if (referenca == null) {
            referenca = new ArrayList<Referenca>();
        }
        return this.referenca;
    }

    /**
     * Gets the value of the recenzija property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the recenzija property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRecenzija().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Recenzija }
     * 
     * 
     */
    public List<Recenzija> getRecenzija() {
        if (recenzija == null) {
            recenzija = new ArrayList<Recenzija>();
        }
        return this.recenzija;
    }

    /**
     * Gets the value of the proptatnoPismo property.
     * 
     * @return
     *     possible object is
     *     {@link ProptatnoPismo }
     *     
     */
    public ProptatnoPismo getProptatnoPismo() {
        return proptatnoPismo;
    }

    /**
     * Sets the value of the proptatnoPismo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProptatnoPismo }
     *     
     */
    public void setProptatnoPismo(ProptatnoPismo value) {
        this.proptatnoPismo = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link TStatus }
     *     
     */
    public TStatus getStatus() {
        if (status == null) {
            return TStatus.POSLAT;
        } else {
            return status;
        }
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link TStatus }
     *     
     */
    public void setStatus(TStatus value) {
        this.status = value;
    }

    /**
     * Gets the value of the duzina property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getDuzina() {
        return duzina;
    }

    /**
     * Sets the value of the duzina property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setDuzina(BigInteger value) {
        this.duzina = value;
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
     *         &lt;element ref="{http://www.ftn.uns.ac.rs/naucni_rad}NeStrukturiranAbstrakt"/>
     *         &lt;element ref="{http://www.ftn.uns.ac.rs/naucni_rad}StukturiranAbstakt"/>
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
        "neStrukturiranAbstrakt",
        "stukturiranAbstakt"
    })
    public static class Abstrakt {

        @XmlElement(name = "NeStrukturiranAbstrakt")
        protected NeStrukturiranAbstrakt neStrukturiranAbstrakt;
        @XmlElement(name = "StukturiranAbstakt")
        protected StukturiranAbstakt stukturiranAbstakt;

        /**
         * Gets the value of the neStrukturiranAbstrakt property.
         * 
         * @return
         *     possible object is
         *     {@link NeStrukturiranAbstrakt }
         *     
         */
        public NeStrukturiranAbstrakt getNeStrukturiranAbstrakt() {
            return neStrukturiranAbstrakt;
        }

        /**
         * Sets the value of the neStrukturiranAbstrakt property.
         * 
         * @param value
         *     allowed object is
         *     {@link NeStrukturiranAbstrakt }
         *     
         */
        public void setNeStrukturiranAbstrakt(NeStrukturiranAbstrakt value) {
            this.neStrukturiranAbstrakt = value;
        }

        /**
         * Gets the value of the stukturiranAbstakt property.
         * 
         * @return
         *     possible object is
         *     {@link StukturiranAbstakt }
         *     
         */
        public StukturiranAbstakt getStukturiranAbstakt() {
            return stukturiranAbstakt;
        }

        /**
         * Sets the value of the stukturiranAbstakt property.
         * 
         * @param value
         *     allowed object is
         *     {@link StukturiranAbstakt }
         *     
         */
        public void setStukturiranAbstakt(StukturiranAbstakt value) {
            this.stukturiranAbstakt = value;
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
     *         &lt;element ref="{http://www.ftn.uns.ac.rs/naucni_rad}Poglavlje" maxOccurs="unbounded"/>
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
    public static class Sadrzaj {

        @XmlElement(name = "Poglavlje", required = true)
        protected List<Poglavlje> poglavlje;

        /**
         * Gets the value of the poglavlje property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the poglavlje property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPoglavlje().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Poglavlje }
         * 
         * 
         */
        public List<Poglavlje> getPoglavlje() {
            if (poglavlje == null) {
                poglavlje = new ArrayList<Poglavlje>();
            }
            return this.poglavlje;
        }

    }

}
