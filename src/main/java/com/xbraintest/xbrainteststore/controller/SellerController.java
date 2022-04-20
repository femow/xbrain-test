package com.xbraintest.xbrainteststore.controller;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xbraintest.xbrainteststore.business.SellerBusiness;
import com.xbraintest.xbrainteststore.domain.Seller;

@RestController
@RequestMapping(path = "api/sellers")
public class SellerController {
	
	@Autowired
	private SellerBusiness sellerBusiness;
	
	@GetMapping
	public @ResponseBody ResponseEntity<Object> getSellers(
			@RequestParam(name = "startDate", required = false) String startFilterDate) {
		try {
			LocalDate startDate = LocalDate.parse(startFilterDate);
			return ResponseEntity.status(HttpStatus.OK).body(sellerBusiness.getAll(startDate));
		} catch (DateTimeParseException err) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("INVALID START DATE");
		}
	}
	
	@PostMapping
	public @ResponseBody ResponseEntity<Object> addSeller(@RequestBody Seller seller) {
		return ResponseEntity.status(HttpStatus.CREATED).body(sellerBusiness.addSeller(seller));
	}
	
	@PatchMapping()
	public @ResponseBody ResponseEntity<Object> updateById(@RequestBody Seller seller) {
		Seller updatedSeller = sellerBusiness.updateSeller(seller);
		if(updatedSeller != null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedSeller);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND");
	}
	
	@GetMapping(path = "{id}")
	public @ResponseBody ResponseEntity<Object> getSellerById(@PathVariable(value = "id") Long id) {
        Optional<Seller> domain = sellerBusiness.getById(id);
        if(domain.isPresent()) {
            return ResponseEntity.status(HttpStatus.FOUND).body(domain);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND");
	}
	
	@DeleteMapping(path = "{id}")
    public @ResponseBody ResponseEntity<Object> deleteById(@PathVariable(value = "id") Long id){

        Optional<Seller> domain = sellerBusiness.getById(id);
        if(domain.isPresent()) {
            sellerBusiness.deleteSeller(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("DELETE SUCCESS");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT FOUND");
    }
}
