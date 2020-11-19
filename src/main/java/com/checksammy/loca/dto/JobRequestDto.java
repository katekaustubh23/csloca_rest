package com.checksammy.loca.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.checksammy.loca.model.CsServicesRequest;
import com.checksammy.loca.model.JobRequest;
import com.checksammy.loca.model.JobRequestNotes;
import com.checksammy.loca.model.Location;
import com.checksammy.loca.model.User;

public class JobRequestDto {

	private Long id;
	
	private User userId;
	
	private String title;
	
	private String junkAt;
	
	private String junkLocation;
	
	private String junkOn;
	
	private Date reqPickupDate;
	
	private Date reqPickupTime;
	
	private String bulkItemNotes;
	
	private String csServices;
	
	private String attachments;
	
	private String streetAddress;
	
	private String addressLine;
	
	private String city;
	
	private String state;
	
	private String status;
	
	private String country;
	
	private String postalCode;
	
	private String notes;
	
	private Integer createdBy;
	
	private Instant createdDate;
	
	private Boolean isDeleted;
	
	private List<JobRequestNotes> jobRequestNotes;
	
	private List<JobRequestCustomFieldDto> requestCustomField;
	
	private Set<CsServicesRequest> csServiceList = new HashSet<>();
	
	private Long serviceAsBintypeId;
	
	private String serviceAsBintype;
	
	private Location locationId;
	
	private String workStatusId;

	public Set<CsServicesRequest> getCsServiceList() {
		return csServiceList;
	}

	public void setCsServiceList(Set<CsServicesRequest> csServiceList) {
		this.csServiceList = csServiceList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public String getJunkAt() {
		return junkAt;
	}

	public void setJunkAt(String junkAt) {
		this.junkAt = junkAt;
	}

	public String getJunkLocation() {
		return junkLocation;
	}

	public void setJunkLocation(String junkLocation) {
		this.junkLocation = junkLocation;
	}

	public String getJunkOn() {
		return junkOn;
	}

	public void setJunkOn(String junkOn) {
		this.junkOn = junkOn;
	}

	public Date getReqPickupDate() {
		return reqPickupDate;
	}

	public void setReqPickupDate(Date reqPickupDate) {
		this.reqPickupDate = reqPickupDate;
	}

	public Date getReqPickupTime() {
		return reqPickupTime;
	}

	public void setReqPickupTime(Date reqPickupTime) {
		this.reqPickupTime = reqPickupTime;
	}

	public String getBulkItemNotes() {
		return bulkItemNotes;
	}

	public void setBulkItemNotes(String bulkItemNotes) {
		this.bulkItemNotes = bulkItemNotes;
	}

	public String getCsServices() {
		return csServices;
	}

	public void setCsServices(String csServices) {
		this.csServices = csServices;
	}

	public String getAttachments() {
		return attachments;
	}

	public void setAttachments(String attachments) {
		this.attachments = attachments;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getAddressLine() {
		return addressLine;
	}

	public void setAddressLine(String addressLine) {
		this.addressLine = addressLine;
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Instant getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Instant createdDate) {
		this.createdDate = createdDate;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<JobRequestNotes> getJobRequestNotes() {
		return jobRequestNotes;
	}

	public void setJobRequestNotes(List<JobRequestNotes> jobRequestNotes) {
		this.jobRequestNotes = jobRequestNotes;
	}

	public List<JobRequestCustomFieldDto> getRequestCustomField() {
		return requestCustomField;
	}

	public void setRequestCustomField(List<JobRequestCustomFieldDto> requestCustomField) {
		this.requestCustomField = requestCustomField;
	}

	public Long getServiceAsBintypeId() {
		return serviceAsBintypeId;
	}

	public void setServiceAsBintypeId(Long serviceAsBintypeId) {
		this.serviceAsBintypeId = serviceAsBintypeId;
	}

	public String getServiceAsBintype() {
		return serviceAsBintype;
	}

	public void setServiceAsBintype(String serviceAsBintype) {
		this.serviceAsBintype = serviceAsBintype;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Location getLocationId() {
		return locationId;
	}

	public void setLocationId(Location locationId) {
		this.locationId = locationId;
	}

	public String getWorkStatusId() {
		return workStatusId;
	}

	public void setWorkStatusId(String workStatusId) {
		this.workStatusId = workStatusId;
	}

	public JobRequestDto getMappingField(JobRequest jobRequest, User user, List<JobRequestCustomFieldDto> jobRequestCustomFieldDtos) {
		JobRequestDto jobRequestDto = new JobRequestDto();
		jobRequestDto.setId(jobRequest.getId());
		jobRequestDto.setAddressLine(jobRequest.getAddressLine());
		jobRequestDto.setAttachments(jobRequest.getAttachments());
		jobRequestDto.setBulkItemNotes(jobRequest.getBulkItemNotes());
		jobRequestDto.setCity(jobRequest.getCity());
		jobRequestDto.setCountry(jobRequest.getCountry());
		jobRequestDto.setCreatedBy(jobRequest.getCreatedBy());
		jobRequestDto.setCreatedDate(jobRequest.getCreatedDate());
		jobRequestDto.setCsServices(jobRequest.getCsServices());
		jobRequestDto.setJunkAt(jobRequest.getJunkAt());
		jobRequestDto.setJunkLocation(jobRequest.getJunkLocation());
		jobRequestDto.setJunkOn(jobRequest.getJunkOn());
		jobRequestDto.setNotes(jobRequest.getBulkItemNotes());
		jobRequestDto.setPostalCode(jobRequest.getPostalCode());
		jobRequestDto.setReqPickupDate(jobRequest.getReqPickupDate());
		jobRequestDto.setReqPickupTime(jobRequest.getReqPickupTime());
		jobRequestDto.setState(jobRequest.getState());
		jobRequestDto.setStreetAddress(jobRequest.getStreetAddress());
		jobRequestDto.setIsDeleted(jobRequest.getIsDeleted());
		jobRequestDto.setTitle(jobRequest.getTitle());
		jobRequestDto.setJobRequestNotes((jobRequest.getJobRequestNotes().size() > 0)?new ArrayList<JobRequestNotes>(jobRequest.getJobRequestNotes()):null);
		jobRequestDto.setRequestCustomField((jobRequestCustomFieldDtos.size() > 0)?jobRequestCustomFieldDtos:null);
		if(user != null) {
			jobRequestDto.setUserId(user);			
		}
		jobRequestDto.setCsServiceList(jobRequest.getCsServiceList());
		jobRequestDto.setServiceAsBintypeId(jobRequest.getServiceAsBintypeId());
		jobRequestDto.setStatus(jobRequest.getStatus());
		jobRequestDto.setLocationId(jobRequest.getLocationId());
		return jobRequestDto;
	}
	
	
}
