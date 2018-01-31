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
import com.example.model.uloge.TKorisnik;
import com.example.model.uloge.search.TKorisnikSearchResult;
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
public class KorisnikRepositoryXML implements KorisnikRepository {

	private static final Logger logger = LoggerFactory.getLogger(KorisnikRepositoryXML.class);

	public static final String COLLECTION_REF = "/korisnik.xml";
	public static final int PAGE_SIZE = 10;

	@Autowired
	protected QueryManager queryManager;

	@Autowired
	protected XMLDocumentManager xmlDocumentManager;

	@Override
	public void add(TKorisnik korisnici) {
		// Add this document to a dedicated collection for later retrieval
		DocumentMetadataHandle metadata = new DocumentMetadataHandle();
		metadata.getCollections().add(COLLECTION_REF);

		JAXBHandle contentHandle = getTKorisnikHandle();
		contentHandle.set(korisnici);
		xmlDocumentManager.write(getDocId(korisnici.getId()), metadata, contentHandle);
	}

	@Override
	public void remove(String id) {
		xmlDocumentManager.delete(getDocId(id));
	}

	@Override
	public TKorisnik findById(String id) {
		JAXBHandle contentHandle = getTKorisnikHandle();
		JAXBHandle result = xmlDocumentManager.read(getDocId(id), contentHandle);
		return (TKorisnik) result.get(TKorisnik.class);
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
	public TKorisnikSearchResult findAll() {
		StructuredQueryBuilder sb = queryManager.newStructuredQueryBuilder();
		StructuredQueryDefinition criteria = sb.collection(COLLECTION_REF);

		SearchHandle resultsHandle = new SearchHandle();
		queryManager.search(criteria, resultsHandle);
		return toSearchResult(resultsHandle);
	}

	// ~~

	private JAXBHandle getTKorisnikHandle() {
		try {
			JAXBContext context = JAXBContext.newInstance(TKorisnik.class);
			return new JAXBHandle(context);
		} catch (JAXBException e) {
			throw new RuntimeException("Unable to create product JAXB context", e);
		}
	}

	private String getDocId(String string) {
		return String.format("/korisnici/%s.xml", string);
	}

	private TKorisnikSearchResult toSearchResult(SearchHandle resultsHandle) {
		List<TKorisnik> korisnici = new ArrayList<>();
		for (MatchDocumentSummary summary : resultsHandle.getMatchResults()) {
			JAXBHandle contentHandle = getTKorisnikHandle();
			logger.info("  * found {}", summary.getUri());
			xmlDocumentManager.read(summary.getUri(), contentHandle);
			korisnici.add((TKorisnik) contentHandle.get(TKorisnik.class));
		}
		return new TKorisnikSearchResult(korisnici);
	}

	@Override
	public TKorisnik findByUsername(String username) {
		TKorisnik korisnik = null;
		if (username.equals("admin")) {
			korisnik = new TKorisnik();
			korisnik.setKorisnickoIme("admin");
			korisnik.setLozinka("admin");
		}
		return korisnik;

	}
}
