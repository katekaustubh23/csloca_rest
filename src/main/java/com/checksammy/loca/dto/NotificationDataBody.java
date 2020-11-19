package com.checksammy.loca.dto;

import java.time.Instant;

public class NotificationDataBody {

	private Long jobId;
	
	private Long driverId;
	
	private String jobNumber;
	
	private Instant startDate;
	
	private Integer durationNumber;
	
	private String durationType;
	
	private Boolean action;

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

	public String getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}

	public Instant getStartDate() {
		return startDate;
	}

	public void setStartDate(Instant startDate) {
		this.startDate = startDate;
	}

	public Integer getDurationNumber() {
		return durationNumber;
	}

	public void setDurationNumber(Integer durationNumber) {
		this.durationNumber = durationNumber;
	}

	public String getDurationType() {
		return durationType;
	}

	public void setDurationType(String durationType) {
		this.durationType = durationType;
	}

	public Boolean getAction() {
		return action;
	}

	public void setAction(Boolean action) {
		this.action = action;
	}
	
}
