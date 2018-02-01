package com.example.repository;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.model.naucni_rad.NaucniRad;
import com.example.model.naucni_radovi.search.NaucniRadSearchResult;
import com.example.model.uloge.Autor;
import com.example.model.uloge.TKorisnik;
import com.example.model.uloge.Urednik;
import com.example.model.uloge.search.AutorSearchResult;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.JAXBHandle;
import com.marklogic.client.io.SearchHandle;
//import com.marklogic.client.query.KeyValueQueryDefinition;
import com.marklogic.client.query.MatchDocumentSummary;
import com.marklogic.client.query.QueryManager;
import com.marklogic.client.query.StructuredQueryBuilder;
import com.marklogic.client.query.StructuredQueryDefinition;

/**
 * Sample implementation of the {@link NaucniRadRepository} making use of
 * MarkLogic's {@link XMLDocumentManager}.
 *
 * @author Niko Schmuck
 */
@Component
public class UrednikRepositoryXML implements UrednikRepository {

	private static final Logger logger = LoggerFactory.getLogger(UrednikRepositoryXML.class);

	public static final String COLLECTION_REF = "/urednici.xml";
	public static final int PAGE_SIZE = 10;

	@Autowired
	protected QueryManager queryManager;

	@Autowired
	protected XMLDocumentManager xmlDocumentManager;

	@Override
	public void add(Urednik urednik) {
		// Add this document to a dedicated collection for later retrieval
		DocumentMetadataHandle metadata = new DocumentMetadataHandle();
		metadata.getCollections().add(COLLECTION_REF);

		JAXBHandle contentHandle = getUrednikHandle();
		contentHandle.set(urednik);
		xmlDocumentManager.write(getDocId(urednik.getId()), metadata, contentHandle);
	}

	@Override
	public void remove(String id) {
		xmlDocumentManager.delete(getDocId(id));
	}

	@Override
	public Urednik findById(String id) {
		JAXBHandle contentHandle = getUrednikHandle();
		JAXBHandle result = xmlDocumentManager.read(getDocId(id), contentHandle);
		return (Urednik) result.get(Urednik.class);
	}


	// ~~

	private JAXBHandle getUrednikHandle() {
		try {
			JAXBContext context = JAXBContext.newInstance(Autor.class);
			return new JAXBHandle(context);
		} catch (JAXBException e) {
			throw new RuntimeException("Unable to create product JAXB context", e);
		}
	}

	private String getDocId(String string) {
		return String.format("/urednici/%s.xml", string);
	}

}
