//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5.1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.02.05 at 04:36:38 PM CET 
//


package com.example.model.naucni_rad;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TTipLista.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TTipLista">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="neuređena"/>
 *     &lt;enumeration value="uređena"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TTipLista")
@XmlEnum
public enum TTipLista {

    @XmlEnumValue("neure\u0111ena")
    NEUREĐENA("neure\u0111ena"),
    @XmlEnumValue("ure\u0111ena")
    UREĐENA("ure\u0111ena");
    private final String value;

    TTipLista(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TTipLista fromValue(String v) {
        for (TTipLista c: TTipLista.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
