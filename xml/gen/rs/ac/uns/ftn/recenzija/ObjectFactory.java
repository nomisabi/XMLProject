//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5.1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.02.07 at 02:54:46 PM CET 
//


package rs.ac.uns.ftn.recenzija;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the rs.ac.uns.ftn.recenzija package. 
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


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: rs.ac.uns.ftn.recenzija
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Recenzija }
     * 
     */
    public Recenzija createRecenzija() {
        return new Recenzija();
    }

    /**
     * Create an instance of {@link Recenzija.Sadrzaj }
     * 
     */
    public Recenzija.Sadrzaj createRecenzijaSadrzaj() {
        return new Recenzija.Sadrzaj();
    }

    /**
     * Create an instance of {@link Recenzija.Sadrzaj.Pitanja }
     * 
     */
    public Recenzija.Sadrzaj.Pitanja createRecenzijaSadrzajPitanja() {
        return new Recenzija.Sadrzaj.Pitanja();
    }

    /**
     * Create an instance of {@link Recenzija.Sadrzaj.KommentarZaAutore }
     * 
     */
    public Recenzija.Sadrzaj.KommentarZaAutore createRecenzijaSadrzajKommentarZaAutore() {
        return new Recenzija.Sadrzaj.KommentarZaAutore();
    }

    /**
     * Create an instance of {@link Recenzija.Sadrzaj.KommentarZaUrednika }
     * 
     */
    public Recenzija.Sadrzaj.KommentarZaUrednika createRecenzijaSadrzajKommentarZaUrednika() {
        return new Recenzija.Sadrzaj.KommentarZaUrednika();
    }

}
