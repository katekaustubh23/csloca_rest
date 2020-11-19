package com.checksammy.loca.dto;

public class ZapierProductServiceDto {
	
	private String name;
	
	private String description;
	
	private String created_at;
	
	private String updated_at;
	
	private Double qty;
	
	private Double unit_cost;
	
	private Double cost;
	
	private Boolean taxable;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}

	public Double getQty() {
		return qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

	public Double getUnit_cost() {
		return unit_cost;
	}

	public void setUnit_cost(Double unit_cost) {
		this.unit_cost = unit_cost;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Boolean getTaxable() {
		return taxable;
	}

	public void setTaxable(Boolean taxable) {
		this.taxable = taxable;
	}

}
