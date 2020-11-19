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
@Table(name = "job_driver_mapping")
public class JobDriverMapping implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;

	@Column(name = "job_id")
	private Long jobId;

	@Column(name = "driver_id")
	private Long driverId;
	
	@Column(name = "created_by")
	private Instant createdBy;
	
	@Column(name = "is_mail_send")
	private Boolean isMailSend;
	
	@Column(name = "status")
	private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public Long getDriverId() {
		return driverId;
	}

	public void setDriverId(Long driverId) {
		this.driverId = driverId;
	}

	public Instant getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Instant createdBy) {
		this.createdBy = createdBy;
	}

	public Boolean getIsMailSend() {
		return isMailSend;
	}

	public void setIsMailSend(Boolean isMailSend) {
		this.isMailSend = isMailSend;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
