package com.xbraintest.xbrainteststore.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table
public class SaleModel {

	@Id
	@GeneratedValue(generator = "saleSequenceIdGenerator")
	@GenericGenerator(
			name = "saleSequenceIdGenerator", 
	        strategy = "sequence",
	        parameters = @Parameter(
	                name = SequenceStyleGenerator.CONFIG_PREFER_SEQUENCE_PER_ENTITY,
	                value = "true"))
	@Column(name = "id_sale")
	Long id;
	
	@Column(name = "date_of_sale", nullable = false, columnDefinition = "DATE")
	LocalDate dateOfSale;
	
	@Column(name = "value", nullable = false, columnDefinition = "FLOAT")
	Float value;
	
	@Column(name = "seller_id", nullable = false, columnDefinition = "INT")
	Long sellerId;

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
