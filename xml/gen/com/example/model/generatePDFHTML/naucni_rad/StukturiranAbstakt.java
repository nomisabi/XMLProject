//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5.1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.02.06 at 06:53:06 PM CET 
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
 *       &lt;sequence>
 *         &lt;element name="Purpose">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="50"/>
 *               &lt;maxLength value="1000"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="DesignMethodologyApproach">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="50"/>
 *               &lt;maxLength value="1000"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Findings">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="50"/>
 *               &lt;maxLength value="1000"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="OriginalityValue">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="50"/>
 *               &lt;maxLength value="1000"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;sequence>
 *           &lt;element name="ResearchLimitationsImplications" minOccurs="0">
 *             &lt;simpleType>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                 &lt;minLength value="50"/>
 *                 &lt;maxLength value="1000"/>
 *               &lt;/restriction>
 *             &lt;/simpleType>
 *           &lt;/element>
 *           &lt;element name="PracticalImplications" minOccurs="0">
 *             &lt;simpleType>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                 &lt;minLength value="50"/>
 *                 &lt;maxLength value="1000"/>
 *               &lt;/restriction>
 *             &lt;/simpleType>
 *           &lt;/element>
 *           &lt;element name="SocialImplications" minOccurs="0">
 *             &lt;simpleType>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                 &lt;minLength value="50"/>
 *                 &lt;maxLength value="1000"/>
 *               &lt;/restriction>
 *             &lt;/simpleType>
 *           &lt;/element>
 *         &lt;/sequence>
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
    "purpose",
    "designMethodologyApproach",
    "findings",
    "originalityValue",
    "researchLimitationsImplications",
    "practicalImplications",
    "socialImplications"
})
@XmlRootElement(name = "StukturiranAbstakt")
public class StukturiranAbstakt {

    @XmlElement(name = "Purpose", required = true)
    protected String purpose;
    @XmlElement(name = "DesignMethodologyApproach", required = true)
    protected String designMethodologyApproach;
    @XmlElement(name = "Findings", required = true)
    protected String findings;
    @XmlElement(name = "OriginalityValue", required = true)
    protected String originalityValue;
    @XmlElement(name = "ResearchLimitationsImplications")
    protected String researchLimitationsImplications;
    @XmlElement(name = "PracticalImplications")
    protected String practicalImplications;
    @XmlElement(name = "SocialImplications")
    protected String socialImplications;

    /**
     * Gets the value of the purpose property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPurpose() {
        return purpose;
    }

    /**
     * Sets the value of the purpose property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPurpose(String value) {
        this.purpose = value;
    }

    /**
     * Gets the value of the designMethodologyApproach property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesignMethodologyApproach() {
        return designMethodologyApproach;
    }

    /**
     * Sets the value of the designMethodologyApproach property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesignMethodologyApproach(String value) {
        this.designMethodologyApproach = value;
    }

    /**
     * Gets the value of the findings property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFindings() {
        return findings;
    }

    /**
     * Sets the value of the findings property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFindings(String value) {
        this.findings = value;
    }

    /**
     * Gets the value of the originalityValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOriginalityValue() {
        return originalityValue;
    }

    /**
     * Sets the value of the originalityValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOriginalityValue(String value) {
        this.originalityValue = value;
    }

    /**
     * Gets the value of the researchLimitationsImplications property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResearchLimitationsImplications() {
        return researchLimitationsImplications;
    }

    /**
     * Sets the value of the researchLimitationsImplications property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResearchLimitationsImplications(String value) {
        this.researchLimitationsImplications = value;
    }

    /**
     * Gets the value of the practicalImplications property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPracticalImplications() {
        return practicalImplications;
    }

    /**
     * Sets the value of the practicalImplications property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPracticalImplications(String value) {
        this.practicalImplications = value;
    }

    /**
     * Gets the value of the socialImplications property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSocialImplications() {
        return socialImplications;
    }

    /**
     * Sets the value of the socialImplications property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSocialImplications(String value) {
        this.socialImplications = value;
    }

}
