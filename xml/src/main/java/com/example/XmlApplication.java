package com.example;

import com.example.model.naucni_rad.NaucniRad;
import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.JSONDocumentManager;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.JAXBHandle;
import com.marklogic.client.query.QueryManager;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.filter.HTTPDigestAuthFilter;
import com.sun.jersey.api.client.filter.LoggingFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.xml.bind.JAXBException;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableAsync
public class XmlApplication {
	@Value("${marklogic.host}")
	private String host;

	@Value("${marklogic.port}")
	private int port;

	@Value("${marklogic.username}")
	private String username;

	@Value("${marklogic.password}")
	private String password;

	@Bean
	public DatabaseClient getDatabaseClient() {
		try {
			// TODO: is this really (still) required?
			// configure once before creating a client
			DatabaseClientFactory.getHandleRegistry().register(JAXBHandle.newFactory(NaucniRad.class));
		} catch (JAXBException e) {
			e.printStackTrace();
		}

		return DatabaseClientFactory.newClient(host, port, username, password,
				DatabaseClientFactory.Authentication.DIGEST);
	}

	@Bean
	public QueryManager getQueryManager() {
		return getDatabaseClient().newQueryManager();
	}

	@Bean
	public XMLDocumentManager getXMLDocumentManager() {
		return getDatabaseClient().newXMLDocumentManager();
	}

	@Bean
	public JSONDocumentManager getJSONDocumentManager() {
		return getDatabaseClient().newJSONDocumentManager();
	}

	@Bean
	public String getMarkLogicBaseURL() {
		return String.format("http://%s:%d", host, port);
	}

	@Bean
	public Client getJerseyClient() {
		Client client = Client.create(); // thread-safe
		client.addFilter(new LoggingFilter());
		client.addFilter(new HTTPDigestAuthFilter(username, password));
		return client;
	}

	public static void main(String[] args) {
		SpringApplication.run(XmlApplication.class, args);
	}
}
