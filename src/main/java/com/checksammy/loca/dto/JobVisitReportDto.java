package com.checksammy.loca.dto;

import java.time.Instant;

public class JobVisitReportDto {

	private Instant date;					//1
	
	private Instant time;					//2
	
	private String clinetName;				//3
	
	private String street;					//4
	
	private String city;					//5
	
	private String state;					//6
	
	private String zipCode;					//7
	
	private String assignTo;				//8
	
	private String assignMemberId;			//
	
	private String jobDetails;				//9
	
	private String lineItems;				//10
	
	private String duration;				//11
	
	private Double noOfJobs;				//12
	
	private Double visitBased;				//13
	
	private String phone;					//14
	
	private String clientEmail;				//15
	
	private String open;					//16
	

	public String getAssignMemberId() {
		return assignMemberId;
	}

	public void setAssignMemberId(String assignMemberId) {
		this.assignMemberId = assignMemberId;
	}

	public Instant getDate() {
		return date;
	}

	public void setDate(Instant date) {
		this.date = date;
	}

	public Instant getTime() {
		return time;
	}

	public void setTime(Instant time) {
		this.time = time;
	}

	public String getClinetName() {
		return clinetName;
	}

	public void setClinetName(String clinetName) {
		this.clinetName = clinetName;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getAssignTo() {
		return assignTo;
	}

	public void setAssignTo(String assignTo) {
		this.assignTo = assignTo;
	}

	public String getJobDetails() {
		return jobDetails;
	}

	public void setJobDetails(String jobDetails) {
		this.jobDetails = jobDetails;
	}

	public String getLineItems() {
		return lineItems;
	}

	public void setLineItems(String lineItems) {
		this.lineItems = lineItems;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public Double getNoOfJobs() {
		return noOfJobs;
	}

	public void setNoOfJobs(Double noOfJobs) {
		this.noOfJobs = noOfJobs;
	}

	public Double getVisitBased() {
		return visitBased;
	}

	public void setVisitBased(Double visitBased) {
		this.visitBased = visitBased;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getClientEmail() {
		return clientEmail;
	}

	public void setClientEmail(String clientEmail) {
		this.clientEmail = clientEmail;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}
	
	
	

}
