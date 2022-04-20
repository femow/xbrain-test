package com.xbraintest.xbrainteststore.domain.DTO;

public class SellerDTO {

	private Long id;
	private String name;
	private Integer salesAmount;
	private Float salesAverage;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSalesAmount() {
		return salesAmount;
	}
	public void setSalesAmount(Integer salesAmount) {
		this.salesAmount = salesAmount;
	}
	public Float getSalesAverage() {
		return salesAverage;
	}
	public void setSalesAverage(Float salesAverage) {
		this.salesAverage = salesAverage;
	}
}
