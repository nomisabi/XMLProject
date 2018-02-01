package com.example.utils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlTransient;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

@XmlTransient
public class NSPrefixMapper extends NamespacePrefixMapper {
	private HashMap<String, String> mappings;
	private String namespace;
	private String prefix;

	public NSPrefixMapper(String namespace, String prefix) {
		this.namespace = namespace;
		this.prefix = prefix;
		// Inicijalizacija mape prefiksa
		mappings = new LinkedHashMap<>();
		setDefaultMappings();
	}

	protected void setDefaultMappings() {

		// Poništava prethodna mapiranja
		clear();

		// Za default namespace prefiks postaviti na ""
		addMapping(namespace, prefix);
		addMapping("http://www.w3.org/2001/XMLSchema-instance", "xsi");
		addMapping("http://java.sun.com/xml/ns/jaxb", "jaxb");
	}

	public void addMapping(String uri, String prefix) {
		mappings.put(uri, prefix);
	}

	public String getMapping(String uri) {
		return mappings.get(uri);
	}

	public Map<String, String> getMappings() {
		return mappings;
	}

	public void clear() {
		mappings.clear();
	}

	/**
	 * Metoda vraća preferirani prefiks za zadati namespace.
	 */
	public String getPreferredPrefix(String namespaceURI, String suggestion, boolean requirePrefix) {
		String preferredPrefix = getMapping(namespaceURI);
		if (preferredPrefix != null)
			return preferredPrefix;
		return suggestion;
	}

}
