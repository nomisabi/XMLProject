//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5.1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.02.05 at 04:36:38 PM CET 
//

package com.example.model.naucni_rad;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


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
 *         &lt;element name="Naziv">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="5"/>
 *               &lt;maxLength value="200"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Paragraf" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;choice>
 *                   &lt;element ref="{http://www.ftn.uns.ac.rs/naucni_rad}Tekst"/>
 *                   &lt;element name="Figura">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="Natpis" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="Url" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element ref="{http://www.ftn.uns.ac.rs/naucni_rad}Tabela"/>
 *                   &lt;element ref="{http://www.ftn.uns.ac.rs/naucni_rad}Lista"/>
 *                 &lt;/choice>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element ref="{http://www.ftn.uns.ac.rs/naucni_rad}Poglavlje" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="Id">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}ID">
 *             &lt;minLength value="1"/>
 *             &lt;maxLength value="20"/>
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
    "paragraf",
    "poglavlje"
})
@XmlRootElement(name = "Poglavlje")
public class Poglavlje {

    @XmlElement(name = "Naziv", required = true)
    protected String naziv;
    @XmlElement(name = "Paragraf", required = true)
    protected List<Poglavlje.Paragraf> paragraf;
    @XmlElement(name = "Poglavlje")
    protected List<Poglavlje> poglavlje;
    @XmlAttribute(name = "Id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    protected String id;

    /**
     * Gets the value of the naziv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNaziv() {
        return naziv;
    }

    /**
     * Sets the value of the naziv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNaziv(String value) {
        this.naziv = value;
    }

    /**
     * Gets the value of the paragraf property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the paragraf property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParagraf().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Poglavlje.Paragraf }
     * 
     * 
     */
    public List<Poglavlje.Paragraf> getParagraf() {
        if (paragraf == null) {
            paragraf = new ArrayList<Poglavlje.Paragraf>();
        }
        return this.paragraf;
    }

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
     *         &lt;element ref="{http://www.ftn.uns.ac.rs/naucni_rad}Tekst"/>
     *         &lt;element name="Figura">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;attribute name="Natpis" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="Url" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element ref="{http://www.ftn.uns.ac.rs/naucni_rad}Tabela"/>
     *         &lt;element ref="{http://www.ftn.uns.ac.rs/naucni_rad}Lista"/>
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
        "tekst",
        "figura",
        "tabela",
        "lista"
    })
    public static class Paragraf {

        @XmlElement(name = "Tekst")
        protected Tekst tekst;
        @XmlElement(name = "Figura")
        protected Poglavlje.Paragraf.Figura figura;
        @XmlElement(name = "Tabela")
        protected Tabela tabela;
        @XmlElement(name = "Lista")
        protected Lista lista;

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

        /**
         * Gets the value of the figura property.
         * 
         * @return
         *     possible object is
         *     {@link Poglavlje.Paragraf.Figura }
         *     
         */
        public Poglavlje.Paragraf.Figura getFigura() {
            return figura;
        }

        /**
         * Sets the value of the figura property.
         * 
         * @param value
         *     allowed object is
         *     {@link Poglavlje.Paragraf.Figura }
         *     
         */
        public void setFigura(Poglavlje.Paragraf.Figura value) {
            this.figura = value;
        }

        /**
         * Gets the value of the tabela property.
         * 
         * @return
         *     possible object is
         *     {@link Tabela }
         *     
         */
        public Tabela getTabela() {
            return tabela;
        }

        /**
         * Sets the value of the tabela property.
         * 
         * @param value
         *     allowed object is
         *     {@link Tabela }
         *     
         */
        public void setTabela(Tabela value) {
            this.tabela = value;
        }

        /**
         * Gets the value of the lista property.
         * 
         * @return
         *     possible object is
         *     {@link Lista }
         *     
         */
        public Lista getLista() {
            return lista;
        }

        /**
         * Sets the value of the lista property.
         * 
         * @param value
         *     allowed object is
         *     {@link Lista }
         *     
         */
        public void setLista(Lista value) {
            this.lista = value;
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
         *       &lt;attribute name="Natpis" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="Url" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class Figura {

            @XmlAttribute(name = "Natpis")
            protected String natpis;
            @XmlAttribute(name = "Url")
            protected String url;

            /**
             * Gets the value of the natpis property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNatpis() {
                return natpis;
            }

            /**
             * Sets the value of the natpis property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNatpis(String value) {
                this.natpis = value;
            }

            /**
             * Gets the value of the url property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getUrl() {
                return url;
            }

            /**
             * Sets the value of the url property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setUrl(String value) {
                this.url = value;
            }

        }

    }

}
