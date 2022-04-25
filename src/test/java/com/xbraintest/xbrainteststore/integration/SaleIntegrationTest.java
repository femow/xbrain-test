package com.xbraintest.xbrainteststore.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xbraintest.xbrainteststore.domain.Sale;
import com.xbraintest.xbrainteststore.model.SaleModel;
import com.xbraintest.xbrainteststore.repository.SaleRepository;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SaleIntegrationTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private SaleRepository repository;

	private String url = "/api/sales";

	
	@Test
	@Order(1)
	public void getAll_ShouldStartEmpty() throws Exception {
		MvcResult result = mockMvc.perform(get(url))
			.andExpect(status().isOk())
			.andReturn();
		String jsonResponse = result.getResponse().getContentAsString();
		String expectedJsonResponse = objectMapper.writeValueAsString(Collections.emptyList());
		assertThat(jsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
	}
	
	@Test
	@Order(2)
	public void addSale_FirstSale() throws Exception {
		Sale newSale = new Sale();
		newSale.setDateOfSale(LocalDate.of(2022, 4, 25));
		newSale.setSellerId(1L);
		newSale.setValue(10F);
		
		MvcResult result = mockMvc.perform(
				post(url)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(newSale)))
			.andExpect(status().isCreated())
			.andReturn();
		
		Optional<SaleModel> sellerModel = repository.findById(1L);
		String jsonResponse = result.getResponse().getContentAsString();
		Sale addedSeller = objectMapper.readValue(jsonResponse, Sale.class);
		
		assertThat(sellerModel.isPresent()).isTrue();
		assertThat(addedSeller.getValue()).isEqualTo(sellerModel.get().getValue());
	}
	
	@Test
	@Order(3)
	public void getAll() throws Exception {
		MvcResult result = mockMvc.perform(get(url))
			.andExpect(status().isOk())
			.andReturn();
		String jsonResponse = result.getResponse().getContentAsString();
		String expectedJsonResponse = objectMapper.writeValueAsString(Collections.emptyList());
		assertThat(jsonResponse).isNotEqualToIgnoringWhitespace(expectedJsonResponse);
	}
	
	@Test
	@Order(4)
	public void getById() throws Exception {
		MvcResult result = mockMvc.perform(get(url + "/1"))
			.andExpect(status().isFound())
			.andReturn();
		Optional<SaleModel> sellerModel = repository.findById(1L);
		String jsonResponse = result.getResponse().getContentAsString();
		Sale addedSeller = objectMapper.readValue(jsonResponse, Sale.class);
		
		assertThat(sellerModel.isPresent()).isTrue();
		assertThat(addedSeller.getValue()).isEqualTo(sellerModel.get().getValue());
	}
	
	@Test
	@Order(5)
	public void getById_InvalidId() throws Exception {
		mockMvc.perform(get(url + "/2"))
			.andExpect(status().isNotFound());
		Optional<SaleModel> sellerModel = repository.findById(2L);
		assertThat(sellerModel.isEmpty()).isTrue();
	}
	
	@Test
	@Order(6)
	public void updateById_InvalidId() throws Exception {
		Sale updateSale = new Sale();
		updateSale.setId(2L);
		updateSale.setValue(11F);
		
	 	mockMvc.perform(patch(url)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updateSale)))
			.andExpect(status().isNotFound());
	}
	
	@Test
	@Order(7)
	public void updateById() throws Exception {
		Sale updateSale = new Sale();
		updateSale.setId(1L);
		updateSale.setValue(11F);
		
	 	MvcResult result = mockMvc.perform(patch(url)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updateSale)))
			.andExpect(status().isAccepted())
			.andReturn();
	 	
		Optional<SaleModel> sellerModel = repository.findById(1L);
		String jsonResponse = result.getResponse().getContentAsString();
		Sale addedSeller = objectMapper.readValue(jsonResponse, Sale.class);
		
		assertThat(sellerModel.isPresent()).isTrue();
		assertThat(sellerModel.get().getValue()).isEqualTo(11F);
		assertThat(addedSeller.getValue()).isEqualTo(sellerModel.get().getValue());
	}
	
	@Test
	@Order(8)
	public void deleteSale() throws Exception {
		mockMvc.perform(delete(url + "/1"))
			.andExpect(status().isOk());
		
		Optional<SaleModel> sellerModel = repository.findById(1L);
		assertThat(sellerModel.isEmpty()).isTrue();
	}
}



















