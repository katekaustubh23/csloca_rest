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
@Table(name="property_detail_notes")
public class PropertyDetailNote implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "property_detail_id")
	private Long propertyDetailId;
	
	@Column(name = "notes")
	private String notes;
	
	@Column(name = "created_user_details")
	private String createdUserDetails;
	
	@Column(name = "created_by")
	private Long createdBy;
	
	@Column(name = "created_ts")
	private Instant createdTs;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPropertyDetailId() {
		return propertyDetailId;
	}

	public void setPropertyDetailId(Long propertyDetailId) {
		this.propertyDetailId = propertyDetailId;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Instant getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(Instant createdTs) {
		this.createdTs = createdTs;
	}

	public String getCreatedUserDetails() {
		return createdUserDetails;
	}

	public void setCreatedUserDetails(String createdUserDetails) {
		this.createdUserDetails = createdUserDetails;
	}
	
}
