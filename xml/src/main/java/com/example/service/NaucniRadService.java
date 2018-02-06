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

import com.example.dto.Revision;
import com.example.dto.Work;
import com.example.korisnici.Korisnik;
import com.example.model.naucni_rad.NaucniRad;
import com.example.model.naucni_rad.Revizija;
import com.example.model.naucni_rad.TStatus;
import com.example.model.naucni_radovi.search.NaucniRadSearchResult;
import com.example.model.propratnopismo.ProptatnoPismo;
import com.example.model.recenzija.Recenzija;
import com.example.model.uloge.Recenzent;
import com.example.repository.NaucniRadRepositoryXML;
import com.example.utils.MyValidationEventHandler;
import com.example.utils.NSPrefixMapper;
import com.example.utils.NaucniRadUtils;
import com.example.utils.PropratnoPismoUtils;

@Service
public class NaucniRadService {

	@Autowired
	protected NaucniRadRepositoryXML nrRepositoryXML;
	@Autowired
	protected Korisnici2Service korisnici2Service;
	@Autowired
	EmailService emailService;
	@Autowired
	NaucniRadUtils naucniRadUtils;
	@Autowired
	PropratnoPismoUtils propratnoPismoUtils;

	public String add(String file) throws JAXBException, SAXException, IOException {
		NaucniRad nr = naucniRadUtils.unmarshalling(file);
		nr.setId(setIdNR());
		if (nr.getRevizija().size() == 1) {
			nr.getRevizija().get(0).setStatus(TStatus.POSLAT);
			nr.getRevizija().get(0).setId("RV1");

			String nrStr = naucniRadUtils.marshalling(nr);
			naucniRadUtils.validation(nrStr);

			nrRepositoryXML.add(nr);
			return nr.getId();
		} else {
			return null;
		}
	}

	public void remove(String id, String idRevision) throws IOException, JAXBException {
		NaucniRad naucniRad = nrRepositoryXML.findById(id);
		System.out.println(id);
		System.out.println(idRevision);

		for (Revizija revizija : naucniRad.getRevizija()) {
			System.out.println(revizija.getNaslov());
			if (revizija.getId() != null && revizija.getId().equals(idRevision)) {
				revizija.setStatus(TStatus.OBRISAN);
			}
		}

		nrRepositoryXML.add(naucniRad);

	}

	public NaucniRad findById(String id) throws IOException, JAXBException {
		return nrRepositoryXML.findById(id);
	}

	public Work findByIdPoslat(String id) throws IOException, JAXBException {
		NaucniRad naucniRad = nrRepositoryXML.findById(id);
		List<Revision> revisions = new ArrayList<>();
		for (Revizija revizija : naucniRad.getRevizija()) {
			Revision revision = new Revision(revizija.getId(), revizija.getNaslov(), revizija.getStatus().toString());
			ProptatnoPismo pismo = revizija.getProptatnoPismo();
			if (pismo != null) {
				revision.setHasLetter(true);
			}
			revisions.add(revision);
		}

		return new Work(naucniRad.getId(), revisions);
	}

	public NaucniRadSearchResult findAll() {
		return nrRepositoryXML.findAll();
	}

	public List<Work> findByStatus(TStatus status, String statusStr) throws IOException, JAXBException {
		List<NaucniRad> radovi = nrRepositoryXML.findByStatus(statusStr);
		List<Work> works = new ArrayList<>();
		for (NaucniRad naucniRad : radovi) {
			List<Revision> revisions = new ArrayList<>();
			for (Revizija revizija : naucniRad.getRevizija()) {
				if (revizija.getStatus().equals(status)) {
					revisions
							.add(new Revision(revizija.getId(), revizija.getNaslov(), revizija.getStatus().toString()));
				}
				works.add(new Work(naucniRad.getId(), revisions));
			}
		}
		return works;
	}

	public List<Work> findMy(String username) throws IOException, JAXBException {
		List<NaucniRad> radovi = nrRepositoryXML.findMy(username);
		List<Work> works = new ArrayList<>();

		for (NaucniRad naucniRad : radovi) {
			List<Revision> revisions = new ArrayList<>();
			for (Revizija revizija : naucniRad.getRevizija()) {
				if (revizija.getStatus().equals(TStatus.OBRISAN)) {
					continue;
				}
				revisions.add(new Revision(revizija.getId(), revizija.getNaslov(), revizija.getStatus().toString()));
			}
			if (!revisions.isEmpty()) {
				works.add(new Work(naucniRad.getId(), revisions));
			}
		}
		return works;
	}

	public void addReview(String id, String idRevision, String username1, String username2)
			throws JAXBException, IOException, MailException, InterruptedException {
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
			if (revizija.getId().equals(idRevision)) {
				revizija.setStatus(TStatus.U_OBRADI);
				revizija.getRecenzija().add(recenzija1);
				revizija.getRecenzija().add(recenzija2);
				break;
			}
		}
		nrRepositoryXML.add(naucniRad);

	}

	public void addLetter(String id, String idRevision, String file) throws IOException, JAXBException, SAXException {
		NaucniRad naucniRad = findById(id);
		ProptatnoPismo pismo = propratnoPismoUtils.unmarshalling(file);

		String pismoStr = propratnoPismoUtils.marshalling(pismo);
		// propratnoPismoUtils.validation(pismoStr);

		for (Revizija revizija : naucniRad.getRevizija()) {
			if (revizija.getId().equals(idRevision)) {
				if (revizija.getProptatnoPismo() == null) {
					pismo.setId("PP" + revizija.getId().substring(2, revizija.getId().length()));
					revizija.setProptatnoPismo(pismo);
					break;
				}
			}
		}
		nrRepositoryXML.add(naucniRad);

	}

	public Long count() {
		return nrRepositoryXML.count();
	}

	private String setIdNR() throws IOException, JAXBException {
		Random r = new Random();
		String id = "";
		int random = 0;
		while (true) {
			random = r.nextInt(1000000);
			id = "NR" + random;
			if (findById(id) == null) {
				return id;
			}
		}
	}

}
