package com.example.dto;

import java.util.List;

public class Work {
	private String id;
	private List<Revision> revisions;

	public Work() {
	}

	public Work(String id, List<Revision> revisions) {
		super();
		this.id = id;
		this.revisions = revisions;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Revision> getRevisions() {
		return revisions;
	}

	public void setRevisions(List<Revision> revisions) {
		this.revisions = revisions;
	}

}
