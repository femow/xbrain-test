package com.xbraintest.xbrainteststore.domain;

import java.time.LocalDate;

public class Sale {

	private Long id;
	private LocalDate dateOfSale;
	private Float value;
	private Long sellerId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDate getDateOfSale() {
		return dateOfSale;
	}
	public void setDateOfSale(LocalDate dateOfSale) {
		this.dateOfSale = dateOfSale;
	}
	public Float getValue() {
		return value;
	}
	public void setValue(Float value) {
		this.value = value;
	}
	public Long getSellerId() {
		return sellerId;
	}
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}
}
