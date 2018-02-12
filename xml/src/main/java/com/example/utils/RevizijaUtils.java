package com.example.utils;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import com.example.model.naucni_rad.NaucniRad;
import com.example.model.naucni_rad.Revizija;

@Component
public class RevizijaUtils {
	private JAXBContext getRevizijaContext() throws JAXBException {
		return JAXBContext.newInstance(Revizija.class);
	}

	public Revizija unmarshalling(String file) throws JAXBException {
		Unmarshaller unmarshaller = this.getRevizijaContext().createUnmarshaller();
		return (Revizija) unmarshaller.unmarshal(new File("./upload-dir/" + file));
	}

	public String marshalling(Revizija revizija) throws JAXBException {
		Marshaller marshaller = this.getRevizijaContext().createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
		marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper",
				new NSPrefixMapper("http://www.ftn.uns.ac.rs/naucni_rad", "nr"));
		marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper",
				new NSPrefixMapper("http://www.ftn.uns.ac.rs/uloge", "ulog"));
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		StringWriter sw = new StringWriter();
		marshaller.marshal(revizija, sw);
		return sw.toString();
	}

}
