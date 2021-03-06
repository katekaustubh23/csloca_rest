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
@Table(name="job_request_notes")
public class JobRequestNotes implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "job_request_id")
	private Long jobRequestId;
	
	@Column(name = "notes")
	private String notes;
	
	@Column(name = "created_user_details")
	private String createdUserDetails;
	
	@Column(name = "created_by")
	private Long createdBy;
	
	@Column(name = "created_ts")
	private Instant createdTs;
	
	@Column(name = "internal_link")
	private String internalLink;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getJobRequestId() {
		return jobRequestId;
	}

	public void setJobRequestId(Long jobRequestId) {
		this.jobRequestId = jobRequestId;
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

	public String getInternalLink() {
		return internalLink;
	}

	public void setInternalLink(String internalLink) {
		this.internalLink = internalLink;
	}
	
	
}
