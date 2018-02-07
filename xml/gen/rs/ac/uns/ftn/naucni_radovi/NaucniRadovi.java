//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5.1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.02.06 at 09:37:29 PM CET 
//


package rs.ac.uns.ftn.naucni_radovi;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import rs.ac.uns.ftn.naucni_rad.NaucniRad;
import rs.ac.uns.ftn.uloge.Urednik;


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
 *         &lt;element ref="{http://www.ftn.uns.ac.rs/naucni_rad}Naucni_rad" maxOccurs="unbounded"/>
 *         &lt;element ref="{http://www.ftn.uns.ac.rs/uloge}Urednik"/>
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
    "naucniRad",
    "urednik"
})
@XmlRootElement(name = "Naucni_radovi")
public class NaucniRadovi {

    @XmlElement(name = "Naucni_rad", namespace = "http://www.ftn.uns.ac.rs/naucni_rad", required = true)
    protected List<NaucniRad> naucniRad;
    @XmlElement(name = "Urednik", namespace = "http://www.ftn.uns.ac.rs/uloge", required = true)
    protected Urednik urednik;

    /**
     * Gets the value of the naucniRad property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the naucniRad property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNaucniRad().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NaucniRad }
     * 
     * 
     */
    public List<NaucniRad> getNaucniRad() {
        if (naucniRad == null) {
            naucniRad = new ArrayList<NaucniRad>();
        }
        return this.naucniRad;
    }

    /**
     * Gets the value of the urednik property.
     * 
     * @return
     *     possible object is
     *     {@link Urednik }
     *     
     */
    public Urednik getUrednik() {
        return urednik;
    }

    /**
     * Sets the value of the urednik property.
     * 
     * @param value
     *     allowed object is
     *     {@link Urednik }
     *     
     */
    public void setUrednik(Urednik value) {
        this.urednik = value;
    }

}
