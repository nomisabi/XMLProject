package com.example.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marklogic.client.DatabaseClient;

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
		return client.newServerEval().xquery(query).evalAs(String.class);
	}

	public String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

}
