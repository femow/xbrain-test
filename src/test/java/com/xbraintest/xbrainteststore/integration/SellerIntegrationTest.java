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

import org.assertj.core.util.IterableUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xbraintest.xbrainteststore.domain.Seller;
import com.xbraintest.xbrainteststore.domain.DTO.SellerDTO;
import com.xbraintest.xbrainteststore.model.SaleModel;
import com.xbraintest.xbrainteststore.model.SellerModel;
import com.xbraintest.xbrainteststore.repository.SaleRepository;
import com.xbraintest.xbrainteststore.repository.SellerRepository;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SellerIntegrationTest {
		
	@Autowired
	private MockMvc mockMvc;
		
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private SaleRepository saleRepository;
	
	@Autowired
	private SellerRepository repository;
	
	private String url = "/api/sellers";
	
	@BeforeAll
	public void clearRepositories() {
		saleRepository.deleteAll();
		repository.deleteAll();
	}
	
	@Test
	@Order(1)
	public void getSellers_ShouldStartEmpty() throws Exception {
		MvcResult result = mockMvc.perform(get(url))
			.andExpect(status().isOk())
			.andReturn();
		
		String jsonResponse = result.getResponse().getContentAsString();
		String expectedJsonResponse = objectMapper.writeValueAsString(Collections.emptyList());
		assertThat(jsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
	}
	
	@Test
	@Order(2)
	public void addSeller_FirstSeller() throws Exception {
		MvcResult result = mockMvc.perform(
				post(url)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(new Seller("Femow"))))
			.andExpect(status().isCreated())
			.andReturn();
		
		Optional<SellerModel> sellerModel = repository.findById(1L);
		String jsonResponse = result.getResponse().getContentAsString();
		Seller addedSeller = objectMapper.readValue(jsonResponse, Seller.class);
		
		assertThat(sellerModel.isPresent()).isTrue();
		assertThat(addedSeller.getName()).isEqualToIgnoringWhitespace(sellerModel.get().getName());
	}
	
	@Test
	@Order(3)
	public void getSellers() throws Exception {
		MvcResult result = mockMvc.perform(get(url))
				.andExpect(status().isOk())
				.andReturn();
		
		Iterable<SellerModel> sellersModel = repository.findAll();
		String jsonResponse = result.getResponse().getContentAsString();
		Seller[] listSellers = objectMapper.readValue(jsonResponse, Seller[].class);
		
		assertThat(listSellers).hasSize(IterableUtil.sizeOf(sellersModel));
	}
	
	@Test
	@Order(4)
	public void getSellers_WithStartDate() throws Exception {
		MvcResult result = mockMvc.perform(get(url + "?startDate=2022-04-20"))
				.andExpect(status().isOk())
				.andReturn();
		
		Iterable<SellerModel> sellersModel = repository.findAll();
		String jsonResponse = result.getResponse().getContentAsString();
		Seller[] listSellers = objectMapper.readValue(jsonResponse, Seller[].class);
		
		assertThat(listSellers).hasSize(IterableUtil.sizeOf(sellersModel));
	}
	
	@Test
	@Order(5)
	public void getSellerById_SellerWithoutSale() throws Exception {
		MvcResult result = mockMvc.perform(get(url + "/1"))
				.andExpect(status().isFound())
				.andReturn();
		
		Optional<SellerModel> sellerModel = repository.findById(1L);
		String jsonResponse = result.getResponse().getContentAsString();
		SellerDTO responseSeller = objectMapper.readValue(jsonResponse, SellerDTO.class);
		
		assertThat(sellerModel.isPresent()).isTrue();
		assertThat(responseSeller.getSalesAmount()).isEqualTo(0);
		assertThat(responseSeller.getSalesAverage()).isEqualTo(0F);
		assertThat(responseSeller.getName()).isEqualToIgnoringWhitespace(sellerModel.get().getName());
	}
	
	@Test
	@Order(6)
	public void getSellers_SellerWithSale() throws Exception {
		SaleModel sale = new SaleModel();
		sale.setSellerId(1L);
		sale.setValue(10F);
		sale.setDateOfSale(LocalDate.of(2022, 4, 25));
		saleRepository.save(sale);
		
		
		MvcResult result = mockMvc.perform(get(url))
				.andExpect(status().isOk())
				.andReturn();
		
		Iterable<SellerModel> sellersModel = repository.findAll();
		String jsonResponse = result.getResponse().getContentAsString();
		Seller[] listSellers = objectMapper.readValue(jsonResponse, Seller[].class);
		
		assertThat(listSellers).hasSize(IterableUtil.sizeOf(sellersModel));
	}
	
	@Test
	@Order(7)
	public void getSellerById() throws Exception {
		MvcResult result = mockMvc.perform(get(url + "/1"))
				.andExpect(status().isFound())
				.andReturn();
		
		Optional<SellerModel> sellerModel = repository.findById(1L);
		String jsonResponse = result.getResponse().getContentAsString();
		Seller responseSeller = objectMapper.readValue(jsonResponse, Seller.class);
		
		assertThat(sellerModel.isPresent()).isTrue();
		assertThat(responseSeller.getName()).isEqualToIgnoringWhitespace(sellerModel.get().getName());
	}
	
	@Test
	@Order(8)
	public void getSellerById_InvalidId() throws Exception {
		mockMvc.perform(get(url + "/2"))
				.andExpect(status().isNotFound());
		
		Optional<SellerModel> sellerModel = repository.findById(2L);
		assertThat(sellerModel.isEmpty()).isTrue();
	}
	
	@Test
	@Order(9)
	public void updateSeller_InvalidId() throws Exception {
		Seller expectedSeller = new Seller();
		expectedSeller.setId(2L);
		expectedSeller.setName("FemowUpdated");

		mockMvc.perform(
				patch(url)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(expectedSeller)))
			.andExpect(status().isNotFound());
	}
	
	
	@Test
	@Order(10)
	public void updateSeller() throws Exception {
		Seller expectedSeller = new Seller();
		expectedSeller.setId(1L);
		expectedSeller.setName("FemowUpdated");

		MvcResult result = mockMvc.perform(
				patch(url)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(expectedSeller)))
			.andExpect(status().isAccepted())
			.andReturn();
				
		Optional<SellerModel> sellerModel = repository.findById(1L);
		String jsonResponse = result.getResponse().getContentAsString();
		Seller changedSeller = objectMapper.readValue(jsonResponse, Seller.class);
		
		assertThat(sellerModel.isPresent()).isTrue();
		assertThat(changedSeller.getName()).isEqualTo("FemowUpdated");
		assertThat(changedSeller.getName()).isEqualToIgnoringWhitespace(sellerModel.get().getName());
	}
	
	@Test
	@Order(11)
	public void deleteById_InvalidId() throws Exception {
		mockMvc.perform(delete(url + "/2"))
			.andExpect(status().isNotFound());
		Optional<SellerModel> sellerModel = repository.findById(2L);
		assertThat(sellerModel.isEmpty()).isTrue();
	}
	
	@Test
	@Order(12)
	public void deleteById() throws Exception {
		mockMvc.perform(delete(url + "/1"))
			.andExpect(status().isOk());
		Optional<SellerModel> sellerModel = repository.findById(1L);
		assertThat(sellerModel.isEmpty()).isTrue();
	}
	
}
