package com.checksammy.loca.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.checksammy.loca.dto.SaveRequestDto;

@Entity
@Table(name = "job_request")
@Where(clause = "is_deleted=0")
public class JobRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;

	@JoinColumn(name = "user_id", referencedColumnName = "id")
	@ManyToOne()
	private User userId;

	@Column(name = "title")
	private String title;

	@Column(name = "junk_at")
	private String junkAt;

	@Column(name = "junk_location")
	private String junkLocation;

	@Column(name = "junk_on")
	private String junkOn;

	@Column(name = "req_pickup_date")
	private Date reqPickupDate;

	@Column(name = "req_pickup_time")
	private Date reqPickupTime;

	@Column(name = "bulk_item_notes")
	private String bulkItemNotes;
	
	@Column(name = "request_number")
	private String requestNumber;

	@Column(name = "cs_services")
	private String csServices;

	@Column(name = "attachments")
	private String attachments;

	@Column(name = "street_address")
	private String streetAddress;

	@Column(name = "address_line")
	private String addressLine;

	@Column(name = "city")
	private String city;

	@Column(name = "state")
	private String state;

	@Column(name = "country")
	private String country;

	@Column(name = "postal_code")
	private String postalCode;

	@Column(name = "notes")
	private String notes;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "work_status_id")
	private String workStatusId;

	@Column(name = "created_by")
	private Integer createdBy;

	@Column(name = "created_date")
	private Instant createdDate;

	@Column(name = "is_deleted")
	private Boolean isDeleted;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "jobRequestId")
//	@Where(clause = "is_deleted=0")
	@OrderBy("id ASC")
	private Set<JobRequestNotes> jobRequestNotes = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "requestId")
	@Where(clause = "is_deleted=0")
	@OrderBy("id ASC")
	private Set<JobRequestCustomField> requestCustomField = new HashSet<>();
	
	/* Custom field */
	@OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "requestId")
	@OrderBy("id ASC")
	private Set<CsServicesRequest> csServiceList = new HashSet<>();
	
	@Column(name = "service_as_bintype_id")
	private Long serviceAsBintypeId;
	
	@JoinColumn(name = "location_id", referencedColumnName = "id")
	@ManyToOne()
	private Location locationId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

//	public Long getUserId() {
//		return userId;
//	}
//
//	public void setUserId(Long userId) {
//		this.userId = userId;
//	}

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

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public Set<JobRequestNotes> getJobRequestNotes() {
		return jobRequestNotes;
	}

	public void setJobRequestNotes(Set<JobRequestNotes> jobRequestNotes) {
		this.jobRequestNotes = jobRequestNotes;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<JobRequestCustomField> getRequestCustomField() {
		return requestCustomField;
	}

	public void setRequestCustomField(Set<JobRequestCustomField> requestCustomField) {
		this.requestCustomField = requestCustomField;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getWorkStatusId() {
		return workStatusId;
	}

	public void setWorkStatusId(String workStatusId) {
		this.workStatusId = workStatusId;
	}

	public String getRequestNumber() {
		return requestNumber;
	}

	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}

	public Set<CsServicesRequest> getCsServiceList() {
		return csServiceList;
	}

	public void setCsServiceList(Set<CsServicesRequest> csServiceList) {
		this.csServiceList = csServiceList;
	}

	public Long getServiceAsBintypeId() {
		return serviceAsBintypeId;
	}

	public void setServiceAsBintypeId(Long serviceAsBintypeId) {
		this.serviceAsBintypeId = serviceAsBintypeId;
	}

	public Location getLocationId() {
		return locationId;
	}

	public void setLocationId(Location locationId) {
		this.locationId = locationId;
	}

	public JobRequest insertBindingData(JobRequest jobRequest) {
		JobRequest jobRequest2 = new JobRequest();
		jobRequest2.setAddressLine(jobRequest.getAddressLine());
		jobRequest2.setAttachments(jobRequest.getAttachments());
		jobRequest2.setBulkItemNotes(jobRequest.getBulkItemNotes());
		jobRequest2.setCity(jobRequest.getCity());
		jobRequest2.setCountry(jobRequest.getCountry());
		jobRequest2.setCreatedBy(
				(jobRequest.getCreatedBy() != null && jobRequest.getCreatedBy() > 0) ? jobRequest.getCreatedBy()
						: null);
		jobRequest2.setCsServices(jobRequest.getCsServices());
		jobRequest2.setId((jobRequest.getId() != null && jobRequest.getId() > 0) ? jobRequest.getId() : null);

		jobRequest2.setIsDeleted(jobRequest.getIsDeleted());
		jobRequest2.setJobRequestNotes(jobRequest.getJobRequestNotes());
		jobRequest2.setJunkAt(jobRequest.getJunkAt());
		jobRequest2.setJunkLocation(jobRequest.getJunkLocation());
		jobRequest2.setJunkOn(jobRequest.getJunkOn());
		jobRequest2.setNotes(jobRequest.getNotes());
		jobRequest2.setPostalCode(jobRequest.getPostalCode());
		jobRequest2.setReqPickupDate(jobRequest.getReqPickupDate());
		jobRequest2.setReqPickupTime(jobRequest.getReqPickupTime());
		jobRequest2.setState(jobRequest.getState());
		jobRequest2.setStreetAddress(jobRequest.getStreetAddress());
		jobRequest2.setCreatedBy(jobRequest.getCreatedBy());
		jobRequest2.setCreatedDate(Instant.now());
		jobRequest2.setTitle(jobRequest.getTitle());
		jobRequest2.setStatus(jobRequest.getStatus());
		jobRequest2.setWorkStatusId(jobRequest.getWorkStatusId());
		jobRequest2.setRequestCustomField(jobRequest.getRequestCustomField());
		jobRequest2.setCsServiceList(jobRequest.getCsServiceList());
		jobRequest2.setServiceAsBintypeId(jobRequest.getServiceAsBintypeId());
		return jobRequest2;
	}

	public JobRequest insertBindingData2(SaveRequestDto jobRequest) {
		JobRequest jobRequest2 = new JobRequest();
		jobRequest2.setAddressLine(jobRequest.getAddressLine());
		jobRequest2.setAttachments(jobRequest.getAttachments());
		jobRequest2.setBulkItemNotes(jobRequest.getBulkItemNotes());
		jobRequest2.setCity(jobRequest.getCity());
		jobRequest2.setCountry(jobRequest.getCountry());
		jobRequest2.setCreatedBy(
				(jobRequest.getCreatedBy() != null && jobRequest.getCreatedBy() > 0) ? jobRequest.getCreatedBy()
						: null);
		jobRequest2.setCsServices(jobRequest.getCsServices());
		jobRequest2.setId((jobRequest.getId() != null && jobRequest.getId() > 0) ? jobRequest.getId() : null);

		jobRequest2.setIsDeleted(jobRequest.getIsDeleted());
		jobRequest2.setJobRequestNotes(jobRequest.getJobRequestNotes());
		jobRequest2.setJunkAt(jobRequest.getJunkAt());
		jobRequest2.setJunkLocation(jobRequest.getJunkLocation());
		jobRequest2.setJunkOn(jobRequest.getJunkOn());
		jobRequest2.setNotes(jobRequest.getNotes());
		jobRequest2.setPostalCode(jobRequest.getPostalCode());
		jobRequest2.setReqPickupDate(jobRequest.getReqPickupDate());
		jobRequest2.setReqPickupTime(jobRequest.getReqPickupTime());
		jobRequest2.setState(jobRequest.getState());
		jobRequest2.setStreetAddress(jobRequest.getStreetAddress());
		jobRequest2.setCreatedBy(jobRequest.getCreatedBy());
		jobRequest2.setCreatedDate(Instant.now());
		jobRequest2.setTitle(jobRequest.getTitle());
		jobRequest2.setStatus(jobRequest.getStatus());
		jobRequest2.setWorkStatusId(jobRequest.getWorkStatusId());
		jobRequest2.setRequestCustomField(jobRequest.getRequestCustomField());
		jobRequest2.setCsServiceList(jobRequest.getCsServiceList());
		jobRequest2.setServiceAsBintypeId(jobRequest.getServiceAsBintypeId());
		return jobRequest2;
	}

}
