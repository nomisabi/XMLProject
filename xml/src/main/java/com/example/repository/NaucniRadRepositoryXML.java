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
import com.marklogic.client.query.QueryManager;
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
	public NaucniRad findById(String id) {
		JAXBHandle<NaucniRad> contentHandle = getNaucniRadHandle();
		if (xmlDocumentManager.exists(getDocId(id)) != null) {
			JAXBHandle<?> result = xmlDocumentManager.read(getDocId(id), contentHandle);
			return result.get(NaucniRad.class);
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
	public List<NaucniRad> findMy(String username) throws IOException, JAXBException {
		String queryName = "findMyWorks.xqy";
		String query = utils.readQuery(queryName);
		query = query.replace("param", username);
		return getResponse(query);

	}

	private List<NaucniRad> getResponse(String query) throws JAXBException {
		ServerEvaluationCall invoker = client.newServerEval();
		invoker.xquery(query);
		EvalResultIterator response = invoker.eval();

		List<NaucniRad> radovi = new ArrayList<>();

		if (response.hasNext()) {
			for (EvalResult result : response) {
				NaucniRad revizija = unmarshalling(result.getString());
				radovi.add(revizija);
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
