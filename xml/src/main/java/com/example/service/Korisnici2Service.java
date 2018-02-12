package com.example.service;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
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

import com.example.korisnici.Korisnici;
import com.example.korisnici.Korisnik;
import com.example.model.naucni_rad.NaucniRad;
import com.example.model.naucni_rad.Revizija;
import com.example.repository.Korisnik2RepositoryXML;
import com.example.repository.NaucniRadRepositoryXML;
import com.example.utils.MyValidationEventHandler;
import com.example.utils.NSPrefixMapper;

@Service
public class Korisnici2Service {
	@Autowired
	protected Korisnik2RepositoryXML korisnik2RepositoryXML;
	@Autowired
	NaucniRadRepositoryXML naucniRadRepositoryXML;

	public void dodaj(String korisnikXML) throws JAXBException, SAXException {
		Korisnik korisnik = unmarshalling(korisnikXML);
		korisnik.setId(setId());
		korisnikXML = marshalling(korisnik);
		validation(korisnikXML);
		korisnik2RepositoryXML.dodaj(korisnikXML);
	}

	public String pronadjiKorisnickoIme(String korisnickoIme) throws IOException {
		return korisnik2RepositoryXML.prondjiKorisnickoIme(korisnickoIme);
	}

	public String pronadjiId(String id) throws IOException {
		return korisnik2RepositoryXML.pronadjiId(id);
	}

	public String pronadjiSve() throws IOException {
		return korisnik2RepositoryXML.pronadjiSve();
	}

	public Korisnici pronadjiSveRecenzente() throws IOException, JAXBException {
		return korisnik2RepositoryXML.pronadjiSveRecenzente();
	}

	public Korisnici pronadjiRecenzente(String id, String idRevision) throws IOException, JAXBException {
		NaucniRad naucniRad = naucniRadRepositoryXML.findById(id);
		List<String> domeni = new ArrayList<>();

		for (Revizija revizija : naucniRad.getRevizija()) {
			if (revizija.getId().equals(idRevision)) {
				domeni = revizija.getKljucnaRec();
			}
		}
		Korisnici recenzenti = korisnik2RepositoryXML.pronadjiRecenzente(domeni);

		if (recenzenti == null) {
			return korisnik2RepositoryXML.pronadjiSveRecenzente();
		}

		if (recenzenti.getKorisnik().size() < 3) {
			Korisnici sviRecenzenti = korisnik2RepositoryXML.pronadjiSveRecenzente();
			for (Korisnik recenzent : sviRecenzenti.getKorisnik()) {
				boolean flag = false;
				for (Korisnik recenznet2 : recenzenti.getKorisnik()) {
					if (recenznet2.getId().equals(recenzent.getId())) {
						flag = true;
						break;
					}
				}
				if (!flag) {
					recenzenti.getKorisnik().add(recenzent);
				}
			}
		}
		return recenzenti;
	}

	private String setId() {
		try {
			Random r = new Random();
			String id = "";
			int random = 0;
			while (true) {
				random = r.nextInt(1000000);
				id = "ID" + random;
				if (pronadjiId(id) == null) {
					return id;
				}
			}
		} catch (IOException e) {
			return "";
		}
	}

	private JAXBContext getKorisnikContext() throws JAXBException {
		return JAXBContext.newInstance(Korisnik.class);
	}

	public Korisnik unmarshalling(String korisnik) throws JAXBException {
		Unmarshaller unmarshaller = this.getKorisnikContext().createUnmarshaller();
		StringReader reader = new StringReader(korisnik);
		return (Korisnik) unmarshaller.unmarshal(reader);
	}

	private String marshalling(Korisnik korisnik) throws JAXBException {
		Marshaller marshaller = this.getKorisnikContext().createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
		marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper",
				new NSPrefixMapper("http://www.ftn.uns.ac.rs/korisnici", "ko"));
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		StringWriter sw = new StringWriter();
		marshaller.marshal(korisnik, sw);

		return sw.toString();
	}

	private void validation(String korisnik) throws JAXBException, SAXException {
		Unmarshaller unmarshaller = this.getKorisnikContext().createUnmarshaller();
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(new File("data/xsd/korisnici.xsd"));

		unmarshaller.setSchema(schema);
		unmarshaller.setEventHandler(new MyValidationEventHandler());

		StringReader reader = new StringReader(korisnik);
		unmarshaller.unmarshal(reader);
	}

}
