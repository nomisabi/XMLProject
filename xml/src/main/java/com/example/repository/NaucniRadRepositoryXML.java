package com.example.repository;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.model.naucni_rad.NaucniRad;
import com.example.model.naucni_radovi.search.NaucniRadSearchResult;
import com.example.utils.NaucniRadUtils;
import com.example.utils.Utils;
import com.marklogic.client.DatabaseClient;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.eval.EvalResult;
import com.marklogic.client.eval.EvalResultIterator;
import com.marklogic.client.eval.ServerEvaluationCall;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.JAXBHandle;
import com.marklogic.client.io.SearchHandle;
//import com.marklogic.client.query.KeyValueQueryDefinition;
import com.marklogic.client.query.MatchDocumentSummary;
import com.marklogic.client.query.MatchLocation;
import com.marklogic.client.query.MatchSnippet;
import com.marklogic.client.query.QueryManager;
import com.marklogic.client.query.StringQueryDefinition;
import com.marklogic.client.query.StructuredQueryBuilder;
import com.marklogic.client.query.StructuredQueryDefinition;

@Component
public class NaucniRadRepositoryXML implements NaucniRadRepository {

	private static final Logger logger = LoggerFactory.getLogger(NaucniRadRepositoryXML.class);

	public static final String COLLECTION_REF = "/ftn/naucni_rad";
	public static final int PAGE_SIZE = 10;

	@Autowired
	protected QueryManager queryManager;
	@Autowired
	protected XMLDocumentManager xmlDocumentManager;
	@Autowired
	private Utils utils;
	@Autowired
	protected DatabaseClient client;
	@Autowired
	private NaucniRadUtils naucniRadUtils;

	@Override
	public void add(NaucniRad nr) {
		DocumentMetadataHandle metadata = new DocumentMetadataHandle();
		metadata.getCollections().add(COLLECTION_REF);

		JAXBHandle<NaucniRad> contentHandle = getNaucniRadHandle();
		contentHandle.set(nr);
		xmlDocumentManager.write(getDocId(nr.getId()), metadata, contentHandle);
	}

	@Override
	public void remove(String id) {
		if (xmlDocumentManager.exists(id) != null) {
			xmlDocumentManager.delete(getDocId(id));
		}
	}

	@Override
	public NaucniRad findById(String id) throws IOException, JAXBException {
		String queryName = "getWorkById.xqy";
		String query = utils.readQuery(queryName);
		query = query.replace("param", id);
		List<NaucniRad> radovi = getResponse(query);
		if (radovi.size() == 1) {
			return radovi.get(0);
		}
		return null;

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
	public NaucniRadSearchResult findAll() {
		StructuredQueryBuilder sb = queryManager.newStructuredQueryBuilder();
		StructuredQueryDefinition criteria = sb.collection(COLLECTION_REF);

		SearchHandle resultsHandle = new SearchHandle();
		queryManager.search(criteria, resultsHandle);
		return toSearchResult(resultsHandle);
	}

	@Override
	public List<NaucniRad> findByStatus(String status) throws IOException, JAXBException {
		String queryName = "findByStatus.xqy";
		String query = utils.readQuery(queryName);
		query = query.replace("param", status);
		return getResponse(query);
	}

	@Override
	public List<NaucniRad> findMy(String id) throws IOException, JAXBException {
		String queryName = "findMyWorks.xqy";
		String query = utils.readQuery(queryName);
		query = query.replace("param", id);
		return getResponse(query);
	}

	@Override
	public List<NaucniRad> findByReviewer(String status, String id) throws IOException, JAXBException {
		String queryName = "findByRewiever.xqy";
		String query = utils.readQuery(queryName);
		query = query.replace("status", status);
		query = query.replace("param", id);
		System.out.println(query);
		return getResponse(query);

	}

	@Override
	public NaucniRad findByReviewerAndID(String status, String idReviewer, String id, String idRevision)
			throws IOException, JAXBException {
		String queryName = "findByIdAndReviewer.xqy";
		String query = utils.readQuery(queryName);
		query = query.replace("status", status);
		query = query.replace("param", idReviewer);
		query = query.replace("id", id);
		query = query.replace("revizija", idRevision);
		System.out.println(query);
		List<NaucniRad> radovi = getResponse(query);
		if (radovi.size() == 1) {
			return radovi.get(0);
		}
		return null;

	}

	@Override
	public List<NaucniRad> searchAuthor(String ime, String prezime, String email, String param) {
		StringQueryDefinition queryDefinition = queryManager.newStringDefinition();

		String criteria = ime + " AND " + prezime + " AND " + email + " AND " + param;
		queryDefinition.setCriteria(criteria);
		queryDefinition.setCollections(COLLECTION_REF);
		SearchHandle results = queryManager.search(queryDefinition, new SearchHandle());

		// Serialize search results to the standard output
		MatchDocumentSummary matches[] = results.getMatchResults();
		System.out.println("[INFO] Showing the results for: " + param + "\n");

		MatchDocumentSummary result;
		MatchLocation locations[];
		String text;

		List<NaucniRad> nr = new ArrayList<>();
		for (MatchDocumentSummary summary : results.getMatchResults()) {
			JAXBHandle<NaucniRad> contentHandle = getNaucniRadHandle();
			logger.info("  * found {}", summary.getUri());
			xmlDocumentManager.read(summary.getUri(), contentHandle);
			nr.add((NaucniRad) contentHandle.get(NaucniRad.class));
		}
		for (int i = 0; i < matches.length; i++) {
			result = matches[i];

			System.out.println((i + 1) + ". RESULT DETAILS: ");
			System.out.println("Result URI: " + result.getUri());

			locations = result.getMatchLocations();
			System.out.println("Document locations matched: " + locations.length + "\n");

			for (MatchLocation location : locations) {

				System.out.print(" - ");
				for (MatchSnippet snippet : location.getSnippets()) {
					text = snippet.getText().trim();
					if (!text.equals("")) {
						System.out.print(snippet.isHighlighted() ? text.toUpperCase() : text);
						System.out.print(" ");
					}
				}
				System.out.println("\n - Match location XPath: " + location.getPath());
				System.out.println();
			}

			System.out.println();
		}

		return nr;

	}

	@Override
	public List<NaucniRad> search(String param) {
		StringQueryDefinition queryDefinition = queryManager.newStringDefinition();

		// String criteria = "Celija2 OR test AND Potpis0";
		queryDefinition.setCriteria(param);
		queryDefinition.setCollections(COLLECTION_REF);
		SearchHandle results = queryManager.search(queryDefinition, new SearchHandle());

		// Serialize search results to the standard output
		MatchDocumentSummary matches[] = results.getMatchResults();
		System.out.println("[INFO] Showing the results for: " + param + "\n");

		MatchDocumentSummary result;
		MatchLocation locations[];
		String text;

		List<NaucniRad> nr = new ArrayList<>();
		for (MatchDocumentSummary summary : results.getMatchResults()) {
			JAXBHandle<NaucniRad> contentHandle = getNaucniRadHandle();
			logger.info("  * found {}", summary.getUri());
			xmlDocumentManager.read(summary.getUri(), contentHandle);
			nr.add((NaucniRad) contentHandle.get(NaucniRad.class));
		}
		for (int i = 0; i < matches.length; i++) {
			result = matches[i];

			System.out.println((i + 1) + ". RESULT DETAILS: ");
			System.out.println("Result URI: " + result.getUri());

			locations = result.getMatchLocations();
			System.out.println("Document locations matched: " + locations.length + "\n");

			for (MatchLocation location : locations) {

				System.out.print(" - ");
				for (MatchSnippet snippet : location.getSnippets()) {
					text = snippet.getText().trim();
					if (!text.equals("")) {
						System.out.print(snippet.isHighlighted() ? text.toUpperCase() : text);
						System.out.print(" ");
					}
				}
				System.out.println("\n - Match location XPath: " + location.getPath());
				System.out.println();
			}

			System.out.println();
		}

		return nr;

	}

	private List<NaucniRad> getResponse(String query) throws JAXBException {
		ServerEvaluationCall invoker = client.newServerEval();
		invoker.xquery(query);
		EvalResultIterator response = invoker.eval();

		List<NaucniRad> radovi = new ArrayList<>();

		if (response.hasNext()) {
			for (EvalResult result : response) {
				NaucniRad naucniRad = unmarshalling(result.getString());
				radovi.add(naucniRad);

			}
		} else {
			System.out.println("your query returned an empty sequence.");
		}
		return radovi;
	}

	private JAXBContext getNaucniRadContext() throws JAXBException {
		return JAXBContext.newInstance(NaucniRad.class);
	}

	public NaucniRad unmarshalling(String revizija) throws JAXBException {
		Unmarshaller unmarshaller = this.getNaucniRadContext().createUnmarshaller();
		StringReader reader = new StringReader(revizija);
		return (NaucniRad) unmarshaller.unmarshal(reader);
	}

	// ~~

	private JAXBHandle<NaucniRad> getNaucniRadHandle() {
		try {
			JAXBContext context = JAXBContext.newInstance(NaucniRad.class);
			return new JAXBHandle<NaucniRad>(context);
		} catch (JAXBException e) {
			throw new RuntimeException("Unable to create product JAXB context", e);
		}
	}

	private String getDocId(String string) {
		return String.format("/naucni_radovi/%s.xml", string);
	}

	private NaucniRadSearchResult toSearchResult(SearchHandle resultsHandle) {
		List<NaucniRad> nr = new ArrayList<>();
		for (MatchDocumentSummary summary : resultsHandle.getMatchResults()) {
			JAXBHandle<NaucniRad> contentHandle = getNaucniRadHandle();
			logger.info("  * found {}", summary.getUri());
			xmlDocumentManager.read(summary.getUri(), contentHandle);
			nr.add((NaucniRad) contentHandle.get(NaucniRad.class));
		}
		return new NaucniRadSearchResult(nr);
	}
}
