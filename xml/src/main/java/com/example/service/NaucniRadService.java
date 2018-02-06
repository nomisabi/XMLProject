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
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import com.example.dto.Work;
import com.example.korisnici.Korisnik;
import com.example.model.naucni_rad.NaucniRad;
import com.example.model.naucni_rad.Revizija;
import com.example.model.naucni_rad.TStatus;
import com.example.model.naucni_radovi.search.NaucniRadSearchResult;
import com.example.model.recenzija.Recenzija;
import com.example.model.uloge.Recenzent;
import com.example.repository.NaucniRadRepositoryXML;
import com.example.utils.MyValidationEventHandler;
import com.example.utils.NSPrefixMapper;

@Service
public class NaucniRadService {

	@Autowired
	protected NaucniRadRepositoryXML nrRepositoryXML;
	@Autowired
	protected Korisnici2Service korisnici2Service;
	@Autowired
	EmailService emailService;

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

	public List<Work> findByStatus(String status) throws IOException, JAXBException {
		List<NaucniRad> radovi = nrRepositoryXML.findByStatus(status);
		List<Work> works = new ArrayList<>();
		for (NaucniRad naucniRad : radovi) {
			for (Revizija revizija : naucniRad.getRevizija()) {
				// if (revizija.getStatus().equals(TStatus.ODOBRODENO)) {
				works.add(new Work(naucniRad.getId(), revizija.getNaslov()));
				// break;
				// }
			}
		}
		return works;
	}

	public List<Work> findMy(String username) throws IOException, JAXBException {
		List<NaucniRad> radovi = nrRepositoryXML.findMy(username);
		List<Work> works = new ArrayList<>();
		for (NaucniRad naucniRad : radovi) {
			for (Revizija revizija : naucniRad.getRevizija()) {
				works.add(new Work(naucniRad.getId(), revizija.getNaslov(), revizija.getStatus().toString()));
			}
		}
		return works;
	}

	public void addReview(String id, String username1, String username2) throws JAXBException, IOException, MailException, InterruptedException {
		NaucniRad naucniRad = findById(id);
		String korisnikStr1 = korisnici2Service.pronadjiKorisnickoIme(username1);
		Korisnik korisnik1 = korisnici2Service.unmarshalling(korisnikStr1);
		Recenzent recenzent1 = new Recenzent();
		recenzent1.setEmail(korisnik1.getEmail());
		recenzent1.setIme(korisnik1.getIme());
		recenzent1.setPrezime(korisnik1.getPrezime());

		String korisnikStr2 = korisnici2Service.pronadjiKorisnickoIme(username2);
		Korisnik korisnik2 = korisnici2Service.unmarshalling(korisnikStr2);
		Recenzent recenzent2 = new Recenzent();
		recenzent2.setEmail(korisnik2.getEmail());
		recenzent2.setIme(korisnik2.getIme());
		recenzent2.setPrezime(korisnik2.getPrezime());
		emailService.sendMail(korisnik1.getEmail());

		Recenzija recenzija1 = new Recenzija();
		recenzija1.setRecenzent(recenzent1);
		Recenzija recenzija2 = new Recenzija();
		recenzija2.setRecenzent(recenzent2);
		emailService.sendMail(korisnik2.getEmail());
		
		for (Revizija revizija : naucniRad.getRevizija()) {
			if (revizija.getStatus().equals(TStatus.POSLAT)) {
				revizija.setStatus(TStatus.U_OBRADI);
				revizija.getRecenzija().add(recenzija1);
				revizija.getRecenzija().add(recenzija2);
				break;
			}
		}
		nrRepositoryXML.add(naucniRad);

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
