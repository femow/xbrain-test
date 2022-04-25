package com.xbraintest.xbrainteststore.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.xbraintest.xbrainteststore.model.SaleModel;


public interface SaleRepository extends CrudRepository<SaleModel, Long> {
	
	@Query("SELECT COUNT(id), AVG(value) FROM SaleModel "
			+ "WHERE seller_id = :sellerId "
			+ "AND date_of_sale >= :startFilterDate")
	public List<Float[]>
	findAllBySellerIdPerPeriod(
			@Param("sellerId") Long sellerId,
			@Param("startFilterDate") LocalDate startFilterDate);
	
	@Query("SELECT COUNT(id), AVG(value) FROM SaleModel "
			+ "WHERE seller_id = :sellerId ")
	public List<Float[]>
	findAllBySellerIdPerPeriod(
			@Param("sellerId") Long sellerId);
}
