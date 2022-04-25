package com.xbraintest.xbrainteststore.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.xbraintest.xbrainteststore.business.SaleBusiness;
import com.xbraintest.xbrainteststore.domain.Sale;
import com.xbraintest.xbrainteststore.domain.Seller;


@WebMvcTest(SaleController.class)
public class saleControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private SaleBusiness business;
	
	private String url = "/api/sales";
	
	@Test
	public void getSales() throws Exception {
		List<Sale> sales = new ArrayList<Sale>();
		Sale sale = new Sale();
		sale.setId(1L);
		sale.setSellerId(1L);
		sale.setValue(10F);
		sales.add(sale);
		
		when(business.getAll()).thenReturn(sales);
		MvcResult result = mockMvc.perform(get(url))
			.andExpect(status().isOk())
			.andReturn();
		
		String resultString = result.getResponse().getContentAsString();
		String expectedSalesString = objectMapper.writeValueAsString(sales);
		assertThat(resultString).isEqualToIgnoringWhitespace(expectedSalesString);
	}
	
	@Test
	public void getById() throws Exception {
		when(business.getById(1L)).thenReturn(Optional.of(new Sale()));
		mockMvc.perform(get(url + "/1"))
			.andExpect(status().isFound());
	}
	
	@Test
	public void getById_InvalidId() throws Exception {
		when(business.getById(1L)).thenReturn(Optional.empty());
		mockMvc.perform(get(url + "/1"))
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void addSale() throws Exception {
		Sale expectedSale = new Sale();
		expectedSale.setId(1L);
		expectedSale.setSellerId(1L);
		expectedSale.setValue(10F);
		when(business.addSale(any(Sale.class))).thenReturn(expectedSale);
		MvcResult result = mockMvc.perform(
				post(url)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(new Sale())))
			.andExpect(status().isCreated())
			.andReturn();
		
		String resultString = result.getResponse().getContentAsString();
		String expectedSaleString = objectMapper.writeValueAsString(expectedSale);
		assertThat(resultString).isEqualToIgnoringWhitespace(expectedSaleString);
	}
	
	@Test
	public void updateById() throws Exception {
		Sale expectedSale = new Sale();
		expectedSale.setId(1L);
		expectedSale.setSellerId(1L);
		expectedSale.setValue(10F);
		
		when(business.updateSale(any(Sale.class))).thenReturn(expectedSale);
		MvcResult result = mockMvc.perform(
				patch(url)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(new Sale())))
			.andExpect(status().isAccepted())
			.andReturn();
		
		String resultString = result.getResponse().getContentAsString();
		String expectedSaleString = objectMapper.writeValueAsString(expectedSale);
		assertThat(resultString).isEqualToIgnoringWhitespace(expectedSaleString);
	}
	
	@Test
	public void updateById_InvalidId() throws Exception {
		when(business.updateSale(any(Sale.class))).thenReturn(null);
		mockMvc.perform(
				patch(url)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(new Sale())))
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void deleteById() throws Exception {
		when(business.getById(1L)).thenReturn(Optional.of(new Sale()));
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


















