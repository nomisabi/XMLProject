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

import com.example.model.propratnopismo.ProptatnoPismo;

@Component
public class PropratnoPismoUtils {
	private JAXBContext getPropratnoPismoContext() throws JAXBException {
		return JAXBContext.newInstance(ProptatnoPismo.class);
	}

	public ProptatnoPismo unmarshalling(String file) throws JAXBException {
		Unmarshaller unmarshaller = this.getPropratnoPismoContext().createUnmarshaller();
		return (ProptatnoPismo) unmarshaller.unmarshal(new File("./upload-dir/" + file));
	}

	public String marshalling(ProptatnoPismo pismo) throws JAXBException {
		Marshaller marshaller = this.getPropratnoPismoContext().createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		StringWriter sw = new StringWriter();
		marshaller.marshal(pismo, sw);
		return sw.toString();
	}

	public void validation(String pismo) throws JAXBException, SAXException {
		Unmarshaller unmarshaller = this.getPropratnoPismoContext().createUnmarshaller();
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(new File("data/xsd/protpratno_pismo.xsd"));

		unmarshaller.setSchema(schema);
		unmarshaller.setEventHandler(new MyValidationEventHandler());

		StringReader reader = new StringReader(pismo);
		unmarshaller.unmarshal(reader);
	}

}
