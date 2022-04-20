package com.xbraintest.xbrainteststore.business;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Component;

import com.xbraintest.xbrainteststore.domain.Sale;
import com.xbraintest.xbrainteststore.mapper.SaleMapper;
import com.xbraintest.xbrainteststore.model.SaleModel;
import com.xbraintest.xbrainteststore.repository.SaleRepository;

@Component
public class SaleBusiness {
	@Autowired
	private SaleRepository repository;
	
	@Autowired
	private SaleMapper mapper;
	
	@Transactional
	public List<Sale> getAll() {
		List<Sale> locais = new ArrayList<>();
		List<SaleModel> models = Streamable.of(repository.findAll()).toList();
		for(SaleModel model : models) {
			locais.add(mapper.toDomain(model));
		}
		return locais;
	}
	
	@Transactional
	public Optional<Sale> getById(Long id) {
		Optional<SaleModel> saleModel = repository.findById(id);
		if(saleModel.isPresent()) {
			return Optional.of(mapper.toDomain(saleModel.get()));
		}
		return Optional.ofNullable(null);
	}
	
	@Transactional
	public List<Float[]> findALlBySellerIdPerPeriod(Long sellerId, LocalDate startFilterDate) {
		List<Float[]> values = null;
		if(startFilterDate != null) {
			values = repository.findALlBySellerIdPerPeriod(sellerId, startFilterDate);
		} else {
			values = repository.findALlBySellerIdPerPeriod(sellerId);
		}
		return values;
	}
	
	@Transactional
	public Sale addSale(Sale sale){
		return mapper.toDomain(repository.save(mapper.toEntity(sale)));
	}
	
	@Transactional
	public Sale updateSale(Sale sale) {
		Optional<SaleModel> saleModelOptional = repository.findById(sale.getId());
		if(saleModelOptional.isPresent()) {
			SaleModel saleModel = saleModelOptional.get();
			saleModel.setValue(sale.getValue());
			return mapper.toDomain(repository.save(saleModel));
		}
		return null;
	}
	
	@Transactional
	public void deleteSale(Long id) {
		repository.deleteById(id);
	}
}
