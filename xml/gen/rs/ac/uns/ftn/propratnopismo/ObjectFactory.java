//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5.1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.02.06 at 09:37:29 PM CET 
//


package rs.ac.uns.ftn.propratnopismo;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the rs.ac.uns.ftn.propratnopismo package. 
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

    private final static QName _ProptatnoPismoSadrzajRecenzent_QNAME = new QName("http://www.ftn.uns.ac.rs/propratnoPismo", "Recenzent");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: rs.ac.uns.ftn.propratnopismo
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ProptatnoPismo }
     * 
     */
    public ProptatnoPismo createProptatnoPismo() {
        return new ProptatnoPismo();
    }

    /**
     * Create an instance of {@link ProptatnoPismo.Sadrzaj }
     * 
     */
    public ProptatnoPismo.Sadrzaj createProptatnoPismoSadrzaj() {
        return new ProptatnoPismo.Sadrzaj();
    }

    /**
     * Create an instance of {@link ProptatnoPismo.Sadrzaj.Rad }
     * 
     */
    public ProptatnoPismo.Sadrzaj.Rad createProptatnoPismoSadrzajRad() {
        return new ProptatnoPismo.Sadrzaj.Rad();
    }

    /**
     * Create an instance of {@link ProptatnoPismo.Sadrzaj.Rezultat }
     * 
     */
    public ProptatnoPismo.Sadrzaj.Rezultat createProptatnoPismoSadrzajRezultat() {
        return new ProptatnoPismo.Sadrzaj.Rezultat();
    }

    /**
     * Create an instance of {@link ProptatnoPismo.Sadrzaj.Razlog }
     * 
     */
    public ProptatnoPismo.Sadrzaj.Razlog createProptatnoPismoSadrzajRazlog() {
        return new ProptatnoPismo.Sadrzaj.Razlog();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ftn.uns.ac.rs/propratnoPismo", name = "Recenzent", scope = ProptatnoPismo.Sadrzaj.class)
    @XmlIDREF
    public JAXBElement<Object> createProptatnoPismoSadrzajRecenzent(Object value) {
        return new JAXBElement<Object>(_ProptatnoPismoSadrzajRecenzent_QNAME, Object.class, ProptatnoPismo.Sadrzaj.class, value);
    }

}
