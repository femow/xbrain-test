package com.xbraintest.xbrainteststore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

@Entity
@Table
public class SellerModel {

	@Id
	@GeneratedValue(generator = "sellerSequenceIdGenerator")
	@GenericGenerator(
			name = "sellerSequenceIdGenerator", 
	        strategy = "sequence",
	        parameters = @Parameter(
	                name = SequenceStyleGenerator.CONFIG_PREFER_SEQUENCE_PER_ENTITY,
	                value = "true"))
	@Column(name = "id_seller")
	Long id;
	
	@Column(name = "name_seller", nullable = false, columnDefinition = "VARCHAR(20)")
	String name;
	
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
