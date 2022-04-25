package com.xbraintest.xbrainteststore.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xbraintest.xbrainteststore.business.SellerBusiness;
import com.xbraintest.xbrainteststore.domain.Seller;
import com.xbraintest.xbrainteststore.domain.DTO.SellerDTO;

@WebMvcTest(SellerController.class)
public class sellerControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private SellerBusiness business;
	
	private String url = "/api/sellers";
	
	@Test
	public void getSellers() throws Exception {
		List<SellerDTO> businessGetAllMockReturn = new ArrayList<>();
		SellerDTO mockSellerDTO = new SellerDTO();
		mockSellerDTO.setId(1L);
		mockSellerDTO.setName("Femow");
		mockSellerDTO.setSalesAmount(1);
		mockSellerDTO.setSalesAverage(1F);
		businessGetAllMockReturn.add(mockSellerDTO);
		when(business.getAll(LocalDate.of(2020, 4, 24))).thenReturn(businessGetAllMockReturn);
		
		MvcResult result = mockMvc.perform(get(url + "?startDate=2020-04-24"))
			.andExpect(status().isOk())
			.andReturn();
		
		String jsonResponse = result.getResponse().getContentAsString();
		String expectedJsonResponse = objectMapper.writeValueAsString(businessGetAllMockReturn);
		assertThat(jsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
	}
	
	@Test
	public void getSellers_WithoutStartDateFilter() throws Exception {
		List<SellerDTO> businessGetAllMockReturn = new ArrayList<>();
		SellerDTO mockSellerDTO = new SellerDTO();
		mockSellerDTO.setId(1L);
		mockSellerDTO.setName("Femow");
		mockSellerDTO.setSalesAmount(1);
		mockSellerDTO.setSalesAverage(1F);
		businessGetAllMockReturn.add(mockSellerDTO);
		
		when(business.getAll(null)).thenReturn(businessGetAllMockReturn);
		
		MvcResult result = mockMvc.perform(get(url))
			.andExpect(status().isOk())
			.andReturn();
		
		String jsonResponse = result.getResponse().getContentAsString();
		String expectedJsonResponse = objectMapper.writeValueAsString(businessGetAllMockReturn);
		assertThat(jsonResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
	}
	
	@Test
	public void getSellers_InvalidDate() throws Exception {
		List<SellerDTO> businessGetAllMockReturn = new ArrayList<>();
		when(business.getAll(null)).thenReturn(businessGetAllMockReturn);
		
		mockMvc.perform(get(url + "?startDate=20-04-2022")).andExpect(status().isNotAcceptable());
	}
	
	@Test
	public void getSellerById() throws Exception {
		Seller expectedSeller = new Seller("Femow");
		expectedSeller.setId(1L);
		Optional<Seller> optionalExpectedSeller = Optional.of(expectedSeller);	
		when(business.getById(1L)).thenReturn(optionalExpectedSeller);
		mockMvc.perform(get(url + "/1"))
			.andExpect(status().isFound());
	}
	
	@Test
	public void getSellerById_InvalidId() throws Exception {
		Optional<Seller> optionalExpectedSeller = Optional.empty();	
		when(business.getById(1L)).thenReturn(optionalExpectedSeller);
		mockMvc.perform(get(url + "/1"))
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void addSeller() throws Exception {
		Seller expectedSeller = new Seller();
		expectedSeller.setId(1L);
		expectedSeller.setName("Femow");
		
		when(business.addSeller(any(Seller.class))).thenReturn(expectedSeller);
		
		MvcResult result = mockMvc.perform(
				post(url)
				.contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(new Seller("Femow"))))
			.andExpect(status().isCreated())
			.andReturn();
		
		String resultResponse = result.getResponse().getContentAsString();
		String expectedJsonResponse = objectMapper.writeValueAsString(expectedSeller);
		assertThat(resultResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
	}
	
	@Test
	public void updatedById() throws Exception {
		Seller expectedSeller = new Seller();
		expectedSeller.setId(1L);
		expectedSeller.setName("FemowUpdated");
		
		Seller newSeller = new Seller("Femow");
		newSeller.setId(1L);
		
		when(business.updateSeller(any(Seller.class))).thenReturn(expectedSeller);

		MvcResult result = mockMvc.perform(
				patch(url)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(newSeller)))
			.andExpect(status().isAccepted())
			.andReturn();
		
		String resultResponse = result.getResponse().getContentAsString();
		String expectedJsonResponse = objectMapper.writeValueAsString(expectedSeller);
		assertThat(resultResponse).isEqualToIgnoringWhitespace(expectedJsonResponse);
	}

	@Test
	public void updatedById_InvalidId() throws Exception {	
		when(business.updateSeller(any(Seller.class))).thenReturn(null);
		mockMvc.perform(
				patch(url)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(new Seller("Femow"))))
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void deleteById() throws Exception {
		Seller seller = new Seller("Femow");
		seller.setId(1L);
		when(business.getById(1L)).thenReturn(Optional.of(seller));
		mockMvc.perform(delete(url + "/1"))
			.andExpect(status().isOk());
	}
	
	@Test
	public void deleteById_InvalidId() throws Exception {
		when(business.getById(1L)).thenReturn(Optional.empty());
		mockMvc.perform(delete(url + "/1"))
			.andExpect(status().isNotFound());
	}
}
















