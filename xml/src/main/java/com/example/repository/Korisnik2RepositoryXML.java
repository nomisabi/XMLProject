package com.example.repository;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.utils.Utils;
import com.marklogic.client.document.DocumentPatchBuilder;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.document.DocumentPatchBuilder.Position;
import com.marklogic.client.io.marker.DocumentPatchHandle;
import com.marklogic.client.util.EditableNamespaceContext;

@Component
public class Korisnik2RepositoryXML implements Korisnik2Repository {

	private static final String DOCID = "korisnici.xml";

	@Autowired
	private XMLDocumentManager xmlDocumentManager;

	@Autowired
	private Utils utils;

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

}
