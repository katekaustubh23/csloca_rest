package com.checksammy.loca.dto;

import java.time.Instant;


public class InvoiceSchedulerDateDto {
	
	private Integer id;
	
	private Integer jobId;
	
	private Instant invStartDate;
	
	private Instant invEndDate;
	
	private Instant invStartTime;

	private Instant invSndTime;

	private String invStatus;

	private Boolean checked;
	
	private Long timeZone;
	
	private String driverReminder;
		
	private String jobType;
		
	private Instant checkedDate;
		
	private Boolean invSchLater;
		
	private Long checkedBy;
	
	private String checkedByName;

	public int getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getJobId() {
		return jobId;
	}

	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}

	public Instant getInvStartDate() {
		return invStartDate;
	}

	public void setInvStartDate(Instant invStartDate) {
		this.invStartDate = invStartDate;
	}

	public Instant getInvEndDate() {
		return invEndDate;
	}

	public void setInvEndDate(Instant invEndDate) {
		this.invEndDate = invEndDate;
	}

	public Instant getInvStartTime() {
		return invStartTime;
	}

	public void setInvStartTime(Instant invStartTime) {
		this.invStartTime = invStartTime;
	}

	public Instant getInvSndTime() {
		return invSndTime;
	}

	public void setInvSndTime(Instant invSndTime) {
		this.invSndTime = invSndTime;
	}

	public String getInvStatus() {
		return invStatus;
	}

	public void setInvStatus(String invStatus) {
		this.invStatus = invStatus;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Long getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(Long timeZone) {
		this.timeZone = timeZone;
	}

	public String getDriverReminder() {
		return driverReminder;
	}

	public void setDriverReminder(String driverReminder) {
		this.driverReminder = driverReminder;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public Instant getCheckedDate() {
		return checkedDate;
	}

	public void setCheckedDate(Instant checkedDate) {
		this.checkedDate = checkedDate;
	}

	public Boolean getInvSchLater() {
		return invSchLater;
	}

	public void setInvSchLater(Boolean invSchLater) {
		this.invSchLater = invSchLater;
	}

	public Long getCheckedBy() {
		return checkedBy;
	}

	public void setCheckedBy(Long checkedBy) {
		this.checkedBy = checkedBy;
	}

	public String getCheckedByName() {
		return checkedByName;
	}

	public void setCheckedByName(String checkedByName) {
		this.checkedByName = checkedByName;
	}
	
	
	
	

}
