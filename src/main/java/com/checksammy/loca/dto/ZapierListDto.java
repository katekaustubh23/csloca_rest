package com.checksammy.loca.dto;

public class ZapierListDto {
	
	private Integer id;
	
	private String key;

	private String name;

	private String description;

	private String created_at;
	
	private String updated_at;
	
	private Integer qty;
	
	private Integer unit_cost;
	
	private Integer cost;
	
	private Boolean taxble;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

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

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Integer getUnit_cost() {
		return unit_cost;
	}

	public void setUnit_cost(Integer unit_cost) {
		this.unit_cost = unit_cost;
	}

	public Integer getCost() {
		return cost;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
	}
	
	public Boolean getTaxble() {
		return taxble;
	}

	public void setTaxble(Boolean taxble) {
		this.taxble = taxble;
	}


}
