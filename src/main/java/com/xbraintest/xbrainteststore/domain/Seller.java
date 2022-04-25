package com.xbraintest.xbrainteststore.domain;

public class Seller {
	private Long id;
	private String name;
	
	public Seller() {}
	
	public Seller(String name) {
		this.name = name;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
