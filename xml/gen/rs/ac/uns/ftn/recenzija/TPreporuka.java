//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5.1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.02.06 at 09:37:29 PM CET 
//


package rs.ac.uns.ftn.recenzija;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TPreporuka.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TPreporuka">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Prihvaten"/>
 *     &lt;enumeration value="Odbijen"/>
 *     &lt;enumeration value="PotrebnoVecaispravka"/>
 *     &lt;enumeration value="PotrebnoManjaispravka"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TPreporuka")
@XmlEnum
public enum TPreporuka {

    @XmlEnumValue("Prihvaten")
    PRIHVATEN("Prihvaten"),
    @XmlEnumValue("Odbijen")
    ODBIJEN("Odbijen"),
    @XmlEnumValue("PotrebnoVecaispravka")
    POTREBNO_VECAISPRAVKA("PotrebnoVecaispravka"),
    @XmlEnumValue("PotrebnoManjaispravka")
    POTREBNO_MANJAISPRAVKA("PotrebnoManjaispravka");
    private final String value;

    TPreporuka(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TPreporuka fromValue(String v) {
        for (TPreporuka c: TPreporuka.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
