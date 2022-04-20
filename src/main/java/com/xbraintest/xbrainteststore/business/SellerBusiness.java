package com.xbraintest.xbrainteststore.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Component;

import com.xbraintest.xbrainteststore.domain.Seller;
import com.xbraintest.xbrainteststore.mapper.SellerMapper;
import com.xbraintest.xbrainteststore.model.SellerModel;
import com.xbraintest.xbrainteststore.repository.SellerRepository;

@Component
public class SellerBusiness {
	
	@Autowired
	private SellerRepository repository;
	
	@Autowired
	private SellerMapper mapper;
	
	@Transactional
	public List<Seller> getAll() {
		List<Seller> locais = new ArrayList<>();
		List<SellerModel> models = Streamable.of(repository.findAll()).toList();
		for(SellerModel model : models) {
			locais.add(mapper.toDomain(model));
		}
		return locais;
	}
	
	@Transactional
	public Optional<Seller> getById(Long id) {
		Optional<SellerModel> sellerModel = repository.findById(id);
		if(sellerModel.isPresent()) {
			return Optional.of(mapper.toDomain(sellerModel.get()));
		}
		return Optional.ofNullable(null);
	}
	
	@Transactional
	public Seller addSeller(Seller seller){
		return mapper.toDomain(repository.save(mapper.toEntity(seller)));
	}
	
	@Transactional
	public Seller updateSeller(Seller seller) {
		Optional<SellerModel> sellerModelOptional = repository.findById(seller.getId());
		if(sellerModelOptional.isPresent()) {
			SellerModel sellerModel = sellerModelOptional.get();
			sellerModel.setName(seller.getName());
			return mapper.toDomain(repository.save(sellerModel));
		}
		
		return null;
	}
	
	@Transactional
	public void deleteSeller(Long id) {
		repository.deleteById(id);
	}
	
}
