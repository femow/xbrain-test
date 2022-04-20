package com.xbraintest.xbrainteststore.mapper;

import org.springframework.stereotype.Component;

import com.xbraintest.xbrainteststore.domain.Sale;
import com.xbraintest.xbrainteststore.model.SaleModel;

@Component
public class SaleMapper {
	public Sale toDomain(SaleModel model) {
		Sale domain = new Sale();
		domain.setId(model.getId());
		domain.setDateOfSale(model.getDateOfSale());
		domain.setValue(model.getValue());
		domain.setSellerId(model.getSellerId());
		return domain;
	}
	
	public SaleModel toEntity(Sale sale) {
		SaleModel saleModel = new SaleModel();
		saleModel.setId(sale.getId());
		saleModel.setDateOfSale(sale.getDateOfSale());
		saleModel.setValue(sale.getValue());
		saleModel.setSellerId(sale.getSellerId());
		return saleModel;
	}
}
