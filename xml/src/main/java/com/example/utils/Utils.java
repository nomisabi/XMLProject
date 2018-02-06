package com.example.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.eval.EvalResult;
import com.marklogic.client.eval.EvalResultIterator;
import com.marklogic.client.eval.ServerEvaluationCall;

@Component
public class Utils {
	private static final String PREFIX = "data/xquery/";

	@Autowired
	protected DatabaseClient client;

	public String readQuery(String queryName) throws IOException {
		String filePath = PREFIX + queryName;
		return readFile(filePath, StandardCharsets.UTF_8);
	}

	public String getResponse(String query) {
		StringBuilder bld = new StringBuilder();

		ServerEvaluationCall invoker = client.newServerEval();
		invoker.xquery(query);
		EvalResultIterator response = invoker.eval();

		if (response.hasNext()) {
			for (EvalResult result : response) {
				bld.append("\n" + result.getString());
			}
		} else {
			System.out.println("your query returned an empty sequence.");
		}
		return bld.toString();
	}

	public String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

}
