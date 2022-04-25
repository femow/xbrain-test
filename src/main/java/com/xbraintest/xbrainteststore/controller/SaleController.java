package com.xbraintest.xbrainteststore.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xbraintest.xbrainteststore.business.SaleBusiness;
import com.xbraintest.xbrainteststore.domain.Sale;

@RestController
@RequestMapping(path = "api/sales")
public class SaleController {
	
	@Autowired
	private SaleBusiness saleBusiness;
	
	@GetMapping
	public @ResponseBody ResponseEntity<Object> getSales() {
		return ResponseEntity.status(HttpStatus.OK).body(saleBusiness.getAll());
	}
	
	@GetMapping(path = "{id}")
	public @ResponseBody ResponseEntity<Object> getById(@PathVariable(value = "id") Long id) {
		Optional<Sale> domain = saleBusiness.getById(id);
		if(domain.isPresent()){
			return ResponseEntity.status(HttpStatus.FOUND).body(domain);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND");
	}
	
	@PostMapping
	public @ResponseBody ResponseEntity<Object> addSale(@RequestBody Sale sale) {
		return ResponseEntity.status(HttpStatus.CREATED).body(saleBusiness.addSale(sale));
	}
	
	@PatchMapping()
	public @ResponseBody ResponseEntity<Object> updateById(@RequestBody Sale sale) {
		Sale updatedSale = saleBusiness.updateSale(sale);
		if(updatedSale != null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedSale);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND");
	}
	
	@DeleteMapping(path = "{id}")
    public @ResponseBody ResponseEntity<Object> deleteById(@PathVariable(value = "id") Long id){

        Optional<Sale> domain = saleBusiness.getById(id);
        if(domain.isPresent()) {
            saleBusiness.deleteSale(id);
            return ResponseEntity.status(HttpStatus.OK).body("DELETE SUCCESS");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND");
    }
}
