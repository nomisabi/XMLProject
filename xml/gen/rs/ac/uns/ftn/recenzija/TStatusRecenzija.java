//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5.1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.02.08 at 12:43:01 AM CET 
//


package rs.ac.uns.ftn.recenzija;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TStatusRecenzija.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TStatusRecenzija">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Ceka se"/>
 *     &lt;enumeration value="Prihvacen"/>
 *     &lt;enumeration value="Odbijen"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TStatusRecenzija")
@XmlEnum
public enum TStatusRecenzija {

    @XmlEnumValue("Ceka se")
    CEKA_SE("Ceka se"),
    @XmlEnumValue("Prihvacen")
    PRIHVACEN("Prihvacen"),
    @XmlEnumValue("Odbijen")
    ODBIJEN("Odbijen");
    private final String value;

    TStatusRecenzija(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TStatusRecenzija fromValue(String v) {
        for (TStatusRecenzija c: TStatusRecenzija.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
