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
import com.example.model.uloge.Recenzent;
import com.example.model.uloge.TKorisnik;
import com.example.model.uloge.search.RecenzentSearchResult;
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
public class RecenzentRepositoryXML implements RecenzentRepository {

	private static final Logger logger = LoggerFactory.getLogger(RecenzentRepositoryXML.class);

	public static final String COLLECTION_REF = "/recenzenti.xml";
	public static final int PAGE_SIZE = 10;

	@Autowired
	protected QueryManager queryManager;

	@Autowired
	protected XMLDocumentManager xmlDocumentManager;

	@Override
	public void add(Recenzent recenzent) {
		// Add this document to a dedicated collection for later retrieval
		DocumentMetadataHandle metadata = new DocumentMetadataHandle();
		metadata.getCollections().add(COLLECTION_REF);

		JAXBHandle contentHandle = getRecenzentHandle();
		contentHandle.set(recenzent);
		xmlDocumentManager.write(getDocId(recenzent.getId()), metadata, contentHandle);
	}

	@Override
	public void remove(String id) {
		xmlDocumentManager.delete(getDocId(id));
	}

	@Override
	public Recenzent findById(String id) {
		JAXBHandle contentHandle = getRecenzentHandle();
		JAXBHandle result = xmlDocumentManager.read(getDocId(id), contentHandle);
		return (Recenzent) result.get(Recenzent.class);
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
	public RecenzentSearchResult findAll() {
		StructuredQueryBuilder sb = queryManager.newStructuredQueryBuilder();
		StructuredQueryDefinition criteria = sb.collection(COLLECTION_REF);

		SearchHandle resultsHandle = new SearchHandle();
		queryManager.search(criteria, resultsHandle);
		return toSearchResult(resultsHandle);
	}

	// ~~

	private JAXBHandle getRecenzentHandle() {
		try {
			JAXBContext context = JAXBContext.newInstance(Recenzent.class);
			return new JAXBHandle(context);
		} catch (JAXBException e) {
			throw new RuntimeException("Unable to create product JAXB context", e);
		}
	}

	private String getDocId(String string) {
		return String.format("/recenzenti/%s.xml", string);
	}

	private RecenzentSearchResult toSearchResult(SearchHandle resultsHandle) {
		List<Recenzent> recenzenti = new ArrayList<>();
		for (MatchDocumentSummary summary : resultsHandle.getMatchResults()) {
			JAXBHandle contentHandle = getRecenzentHandle();
			logger.info("  * found {}", summary.getUri());
			xmlDocumentManager.read(summary.getUri(), contentHandle);
			recenzenti.add((Recenzent) contentHandle.get(Recenzent.class));
		}
		return new RecenzentSearchResult(recenzenti);
	}

}
