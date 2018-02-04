package com.example.service;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Random;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import com.example.model.naucni_rad.NaucniRad;
import com.example.model.naucni_rad.TStatus;
import com.example.model.naucni_radovi.search.NaucniRadSearchResult;
import com.example.repository.NaucniRadRepositoryXML;
import com.example.utils.MyValidationEventHandler;
import com.example.utils.NSPrefixMapper;

@Service
public class NaucniRadService {

	@Autowired
	protected NaucniRadRepositoryXML nrRepositoryXML;

	public void add(String file) throws JAXBException, SAXException {
		NaucniRad nr = unmarshalling(file);
		nr.setId(setId());
		nr.getRevizija().get(0).setStatus(TStatus.POSLAT);
		// String nrStr = marshalling(nr);
		// validation(nrStr);
		nrRepositoryXML.add(nr);
	}

	public void remove(String id) {
		nrRepositoryXML.remove(id);
	}

	public NaucniRad findById(String id) {
		return nrRepositoryXML.findById(id);
	}

	public NaucniRadSearchResult findAll() {
		return nrRepositoryXML.findAll();
	}

	public String findByStatus(String status) throws IOException {
		return nrRepositoryXML.findByStatus(status);
	}

	public Long count() {
		return nrRepositoryXML.count();
	}

	private String setId() {
		Random r = new Random();
		String id = "";
		int random = 0;
		while (true) {
			random = r.nextInt(1000000);
			id = "ID" + random;
			if (findById(id) == null) {
				return id;
			}
		}
	}

	private JAXBContext getNaucniRadContext() throws JAXBException {
		return JAXBContext.newInstance(NaucniRad.class);
	}

	public NaucniRad unmarshalling(String file) throws JAXBException {
		Unmarshaller unmarshaller = this.getNaucniRadContext().createUnmarshaller();
		return (NaucniRad) unmarshaller.unmarshal(new File("./upload-dir/" + file));
	}

	private String marshalling(NaucniRad naucniRad) throws JAXBException {
		Marshaller marshaller = this.getNaucniRadContext().createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
		marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper",
				new NSPrefixMapper("http://www.ftn.uns.ac.rs/naucni_rad", "nr"));
		marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper",
				new NSPrefixMapper("http://www.ftn.uns.ac.rs/uloge", "ulog"));
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		StringWriter sw = new StringWriter();
		marshaller.marshal(naucniRad, sw);
		return sw.toString();
	}

	private void validation(String naucniRad) throws JAXBException, SAXException {
		Unmarshaller unmarshaller = this.getNaucniRadContext().createUnmarshaller();
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(new File("data/xsd/naucni_rad.xsd"));

		unmarshaller.setSchema(schema);
		unmarshaller.setEventHandler(new MyValidationEventHandler());

		StringReader reader = new StringReader(naucniRad);
		unmarshaller.unmarshal(reader);
	}

}
