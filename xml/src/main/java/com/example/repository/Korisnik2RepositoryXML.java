package com.example.repository;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.korisnici.Korisnici;
import com.example.korisnici.Korisnik;
import com.example.model.naucni_rad.NaucniRad;
import com.example.utils.Utils;
import com.marklogic.client.DatabaseClient;
import com.marklogic.client.document.DocumentPatchBuilder;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.document.DocumentPatchBuilder.Position;
import com.marklogic.client.eval.EvalResult;
import com.marklogic.client.eval.EvalResultIterator;
import com.marklogic.client.eval.ServerEvaluationCall;
import com.marklogic.client.io.marker.DocumentPatchHandle;
import com.marklogic.client.util.EditableNamespaceContext;

@Component
public class Korisnik2RepositoryXML implements Korisnik2Repository {

	private static final String DOCID = "korisnici.xml";

	@Autowired
	private XMLDocumentManager xmlDocumentManager;

	@Autowired
	private Utils utils;

	@Autowired
	protected DatabaseClient client;

	@Override
	public void dodaj(String korisnik) {
		EditableNamespaceContext namespaces = new EditableNamespaceContext();
		namespaces.put("ko", "http://www.ftn.uns.ac.rs/korisnici");
		namespaces.put("fn", "http://www.w3.org/2005/xpath-functions");

		DocumentPatchBuilder patchBuilder = xmlDocumentManager.newPatchBuilder();
		patchBuilder.setNamespaces(namespaces);
		patchBuilder.insertFragment("/ko:korisnici", Position.LAST_CHILD, korisnik);

		DocumentPatchHandle patchHandle = patchBuilder.build();
		xmlDocumentManager.patch(DOCID, patchHandle);
	}

	@Override
	public String prondjiKorisnickoIme(String korisnickoIme) throws IOException {
		String queryName = "getUserByUsername.xqy";
		String query = utils.readQuery(queryName);
		query = query.replace("param", korisnickoIme);
		return utils.getResponse(query);
	}

	@Override
	public String pronadjiId(String id) throws IOException {
		String queryName = "getUserById.xqy";
		String query = utils.readQuery(queryName);
		query = query.replace("param", id);
		return utils.getResponse(query);
	}

	@Override
	public String pronadjiSve() throws IOException {
		String queryName = "retrieveCollection.xqy";
		String query = utils.readQuery(queryName);
		return utils.getResponse(query);
	}

	@Override
	public Korisnici pronadjiSveRecenzente() throws IOException, JAXBException {
		String queryName = "findReviews.xqy";
		String query = utils.readQuery(queryName);
		return getResponse(query);
	}

	@Override
	public Korisnici pronadjiRecenzente(List<String> domeni) throws IOException, JAXBException {
		String queryName = "findByDomen.xqy";
		String query = utils.readQuery(queryName);
		StringBuilder bld = new StringBuilder();
		int i = 0;
		for (String string : domeni) {
			bld.append("$korisnik/ko:domen = '" + string + "'");
			if (i + 2 <= domeni.size()) {
				bld.append(" or ");
			}
			i++;
		}
		query = query.replace("domeni", bld.toString());

		System.out.println(query);
		return getResponse(query);

	}

	@Override
	public Korisnici pronadjiKorisnike(String param) throws IOException, JAXBException {
		String queryName = "findKorisnici.xqy";
		String query = utils.readQuery(queryName);
		query = query.replace("param", param);
		System.out.println(query);
		return getResponse(query);

	}

	public Korisnici getResponse(String query) throws JAXBException {
		ServerEvaluationCall invoker = client.newServerEval();
		invoker.xquery(query);
		EvalResultIterator response = invoker.eval();

		Korisnici korisnici = new Korisnici();
		if (response.hasNext()) {
			for (EvalResult result : response) {
				Korisnik korisnik = unmarshalling(result.getString());
				korisnici.getKorisnik().add(korisnik);
			}
		} else {
			return null;
		}
		return korisnici;
	}

	public Korisnik unmarshalling(String korisnik) throws JAXBException {
		Unmarshaller unmarshaller = this.getKorisnikContext().createUnmarshaller();
		StringReader reader = new StringReader(korisnik);
		return (Korisnik) unmarshaller.unmarshal(reader);
	}

	private JAXBContext getKorisnikContext() throws JAXBException {
		return JAXBContext.newInstance(Korisnik.class);
	}

}
