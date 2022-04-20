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
import com.xbraintest.xbrainteststore.domain.Seller;
import com.xbraintest.xbrainteststore.domain.DTO.SellerDTO;
import com.xbraintest.xbrainteststore.mapper.SellerMapper;
import com.xbraintest.xbrainteststore.model.SellerModel;
import com.xbraintest.xbrainteststore.repository.SellerRepository;

@Component
public class SellerBusiness {
	
	@Autowired
	private SellerRepository repository;
	
	@Autowired
	private SaleBusiness saleBusiness;
	
	@Autowired
	private SellerMapper mapper;
	
	@Transactional
	public List<SellerDTO> getAll(LocalDate startFilterDate) {
		List<SellerDTO> locais = new ArrayList<>();
		List<SellerModel> models = Streamable.of(repository.findAll()).toList();
		for(SellerModel model : models) {
			List<Float[]> relatedSalesValues = saleBusiness.findALlBySellerIdPerPeriod(
					model.getId(),
					startFilterDate);
			System.out.println(relatedSalesValues.get(0).length);
			SellerDTO sellerDTO = new SellerDTO();
			sellerDTO.setId(model.getId());
			sellerDTO.setName(model.getName());
			Float[] values = relatedSalesValues.get(0);
			if(values[0] != null) {
				sellerDTO.setSalesAmount(Math.round(values[0]));				
			} else {
				sellerDTO.setSalesAmount(0);				
			}
			if(values[1] != null) {
				sellerDTO.setSalesAverage(values[1]);				
			} else {
				sellerDTO.setSalesAverage(0.0F);
			}
			
			locais.add(sellerDTO);
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
