package com.example.repository.init;

import java.io.FileInputStream;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.InputStreamHandle;

@Service
public class KorisnikRepositoryInit implements InitializingBean {
	@Autowired
	protected XMLDocumentManager xmlDocumentManager;

	@Override
	public void afterPropertiesSet() throws Exception {
		String docId = "korisnici.xml";
		String collId = "ftn/korisnici";

		InputStreamHandle handle = new InputStreamHandle(new FileInputStream("data/xml/korisnici.xml"));

		DocumentMetadataHandle metadataHandle = new DocumentMetadataHandle();
		metadataHandle.getCollections().add(collId);

		System.out.println("[INFO] Inserting \"" + docId + "\" to \" database.");
		xmlDocumentManager.write(docId, metadataHandle, handle);

	}

}
