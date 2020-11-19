package com.checksammy.loca.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

@Entity
@Table(name="quotes_product_service")
@Where(clause = "is_deleted=0")
public class QuotesProductAndService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "product_service_id")
	private Long productServiceId;
	
	@Column(name = "quotes_id")
	private Long quotesId;
	
	@Column(name = "quantity")
	private Integer quantity;
	
	@Column(name = "unit_cost")
	private Double unitCost;
	
	@Column(name = "total_cost")
	private Double totalCost;

	@Column(name = "notes")
	private String notes;
	
	@Column(name = "picture")
	private String picture;
	
	@Column(name = "is_optional")
	private Boolean isOptional;
	
	@Column(name = "is_deleted")
	private Boolean isDeleted;

	@Column(name = "is_recommended")
	private Boolean isRecommended;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProductServiceId() {
		return productServiceId;
	}

	public void setProductServiceId(Long productServiceId) {
		this.productServiceId = productServiceId;
	}

	public Long getQuotesId() {
		return quotesId;
	}

	public void setQuotesId(Long quotesId) {
		this.quotesId = quotesId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(Double unitCost) {
		this.unitCost = unitCost;
	}

	public Double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Boolean getIsOptional() {
		return isOptional;
	}

	public void setIsOptional(Boolean isOptional) {
		this.isOptional = isOptional;
	}

	public Boolean getIsRecommended() {
		return isRecommended;
	}

	public void setIsRecommended(Boolean isRecommended) {
		this.isRecommended = isRecommended;
	}
	
}
