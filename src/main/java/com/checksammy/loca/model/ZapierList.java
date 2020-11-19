package com.checksammy.loca.model;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "zapier_sample_list")
public class ZapierList implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "key")
	private String key;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "created_at")
	private String created_at;
	
	/*New changes*/
	@Column(name = "updated_at")
	private String updated_at;
	
	@Column(name = "qty")
	private String qty;
	
	@Column(name = "unit_cost")
	private String unit_cost;
	
	@Column(name = "cost")
	private Boolean cost;
	
	@Column(name = "taxable")
	private Instant taxable;

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

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getUnit_cost() {
		return unit_cost;
	}

	void setUnit_cost(String unit_cost) {
		this.unit_cost = unit_cost;
	}

	public Boolean getCost() {
		return cost;
	}

	public void setCost(Boolean cost) {
		this.cost = cost;
	}

	public Instant getTaxable() {
		return taxable;
	}

	public void setTaxable(Instant taxable) {
		this.taxable = taxable;
	}

}
