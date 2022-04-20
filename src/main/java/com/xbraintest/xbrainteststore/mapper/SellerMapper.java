package com.xbraintest.xbrainteststore.mapper;

import org.springframework.stereotype.Component;

import com.xbraintest.xbrainteststore.domain.Seller;
import com.xbraintest.xbrainteststore.model.SellerModel;

@Component
public class SellerMapper {
	public Seller toDomain(SellerModel model) {
		Seller domain = new Seller();
		domain.setId(model.getId());
		domain.setName(model.getName());
		return domain;
	}
	
	public SellerModel toEntity(Seller seller) {
		SellerModel model = new SellerModel();
		model.setId(seller.getId());
		model.setName(seller.getName());
		return model;
	}
}
