package com.example.dto;

public class Work {
	private String id;
	private String title;
	private String status;
	private String review1;
	private String review2;

	public Work() {

	}

	public Work(String id, String title) {
		super();
		this.id = id;
		this.title = title;
	}

	public Work(String id, String title, String status) {
		super();
		this.id = id;
		this.title = title;
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReview1() {
		return review1;
	}

	public void setReview1(String review1) {
		this.review1 = review1;
	}

	public String getReview2() {
		return review2;
	}

	public void setReview2(String review2) {
		this.review2 = review2;
	}

}
