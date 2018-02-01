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
import com.example.model.recenzija.Recenzija;
import com.example.model.recenzija.search.RecenzijaSearchResult;
import com.example.model.uloge.TKorisnik;
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
public class RecenzijaRepositoryXML implements RecenzijaRepository {

	private static final Logger logger = LoggerFactory.getLogger(RecenzijaRepositoryXML.class);

	public static final String COLLECTION_REF = "/recenzije.xml";
	public static final int PAGE_SIZE = 10;

	@Autowired
	protected QueryManager queryManager;

	@Autowired
	protected XMLDocumentManager xmlDocumentManager;

	@Override
	public void add(Recenzija recenzije) {
		// Add this document to a dedicated collection for later retrieval
		DocumentMetadataHandle metadata = new DocumentMetadataHandle();
		metadata.getCollections().add(COLLECTION_REF);

		JAXBHandle contentHandle = getRecenzijaHandle();
		contentHandle.set(recenzije);
		xmlDocumentManager.write(getDocId(recenzije.getId()), metadata, contentHandle);
	}

	@Override
	public void remove(String id) {
		xmlDocumentManager.delete(getDocId(id));
	}

	@Override
	public Recenzija findById(String id) {
		JAXBHandle contentHandle = getRecenzijaHandle();
		JAXBHandle result = xmlDocumentManager.read(getDocId(id), contentHandle);
		return (Recenzija) result.get(Recenzija.class);
	}

	@Override
	public Long count() {
		StructuredQueryBuilder sb = queryManager.newStructuredQueryBuilder();
		StructuredQueryDefinition criteria = sb.collection(COLLECTION_REF);

		SearchHandle resultsHandle = new SearchHandle();
		queryManager.search(criteria, resultsHandle);
		return resultsHandle.getTotalResults();
	}

	@Override
	public RecenzijaSearchResult findAll() {
		StructuredQueryBuilder sb = queryManager.newStructuredQueryBuilder();
		StructuredQueryDefinition criteria = sb.collection(COLLECTION_REF);

		SearchHandle resultsHandle = new SearchHandle();
		queryManager.search(criteria, resultsHandle);
		return toSearchResult(resultsHandle);
	}

	// ~~

	private JAXBHandle getRecenzijaHandle() {
		try {
			JAXBContext context = JAXBContext.newInstance(Recenzija.class);
			return new JAXBHandle(context);
		} catch (JAXBException e) {
			throw new RuntimeException("Unable to create product JAXB context", e);
		}
	}

	private String getDocId(String string) {
		return String.format("/recenzije/%s.xml", string);
	}

	private RecenzijaSearchResult toSearchResult(SearchHandle resultsHandle) {
		List<Recenzija> recenzije = new ArrayList<>();
		for (MatchDocumentSummary summary : resultsHandle.getMatchResults()) {
			JAXBHandle contentHandle = getRecenzijaHandle();
			logger.info("  * found {}", summary.getUri());
			xmlDocumentManager.read(summary.getUri(), contentHandle);
			recenzije.add((Recenzija) contentHandle.get(Recenzija.class));
		}
		return new RecenzijaSearchResult(recenzije);
	}

}
