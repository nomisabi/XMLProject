//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5.1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.02.01 at 03:11:47 PM CET 
//
package com.example.model.uloge;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the rs.ac.uns.ftn.uloge package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _UrednikNaucniRadovi_QNAME = new QName("http://www.ftn.uns.ac.rs/uloge", "Naucni_radovi");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: rs.ac.uns.ftn.uloge
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Autor }
     * 
     */
    public Autor createAutor() {
        return new Autor();
    }

    /**
     * Create an instance of {@link TKorisnik }
     * 
     */
    public TKorisnik createTKorisnik() {
        return new TKorisnik();
    }

    /**
     * Create an instance of {@link Institucija }
     * 
     */
    public Institucija createInstitucija() {
        return new Institucija();
    }

    /**
     * Create an instance of {@link Adresa }
     * 
     */
    public Adresa createAdresa() {
        return new Adresa();
    }

    /**
     * Create an instance of {@link Recenzent }
     * 
     */
    public Recenzent createRecenzent() {
        return new Recenzent();
    }

    /**
     * Create an instance of {@link Urednik }
     * 
     */
    public Urednik createUrednik() {
        return new Urednik();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ftn.uns.ac.rs/uloge", name = "Naucni_radovi", scope = Urednik.class)
    @XmlIDREF
    public JAXBElement<Object> createUrednikNaucniRadovi(Object value) {
        return new JAXBElement<Object>(_UrednikNaucniRadovi_QNAME, Object.class, Urednik.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ftn.uns.ac.rs/uloge", name = "Naucni_radovi", scope = Recenzent.class)
    @XmlIDREF
    public JAXBElement<Object> createRecenzentNaucniRadovi(Object value) {
        return new JAXBElement<Object>(_UrednikNaucniRadovi_QNAME, Object.class, Recenzent.class, value);
    }

}
