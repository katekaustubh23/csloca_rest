package com.checksammy.loca.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

@Entity
@Table(name = "vweb_getallbinhistory")
public class BinHistoryViewWeb implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id")
	private Long id;
	
	@Column(name="bin_location_id")
	private Long binLocationId;
	
	@Column(name="location_id")
	private Long locationId;
	
	@Column(name="property_name")
	private String propertyName;
	
	@Column(name="bin_type_id")
	private Long binTypeId;
	
	@Column(name="bin_type_name")
	private String binTypeName;
	
	@Column(name="content_value")
	private Long totalContentValue;
	
	@Column(name="content_value_scale")
	private String contentValueScale;
	
	@Column(name="bin_weight")
	private String binWeight;
	
	@Column(name="truck_fill_weight")
	private String truckFillWeight;
	
	@Column(name="unit")
	private String unit;
	
	@Column(name="attachments")
	private String attachments;
	
	@Column(name="street_number")
	private String streetNumber;
	
	@Column(name="street_name")
	private String streetName;
	
	@Column(name="city")
	private String city;
	
	@Column(name="pincode")
	private String pincode;
	
	@Column(name="state")
	private String state;
	
	@Column(name="country")
	private String country;
	
	@Column(name="spoc_name")
	private String spocName;
	
	@Column(name="spoc_phone")
	private String spocPhone;
	
	@Column(name="created_by")
	private Long driverId;
	
	@Column(name="user_firstname")
	private String userFirstname;
	
	@Column(name="company_id")
	private Long companyId;
	
	@Column(name="company_name")
	private String companyName;
	
	@Column(name="user_lastname")
	private String userLastname;
	
	@Column(name="created_ts")
	private Instant createdTS;
	
	@Column(name="last_modified_ts")
	private Instant lastModifiedTS;
	
	@OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "binLocationId")
//	@Where(clause = "is_deleted=0")
	@OrderBy("id ASC")
	private Set<BinLocationNotes> binLocationNotes = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBinLocationId() {
		return binLocationId;
	}

	public void setBinLocationId(Long binLocationId) {
		this.binLocationId = binLocationId;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public Long getBinTypeId() {
		return binTypeId;
	}

	public void setBinTypeId(Long binTypeId) {
		this.binTypeId = binTypeId;
	}

	public String getBinTypeName() {
		return binTypeName;
	}

	public void setBinTypeName(String binTypeName) {
		this.binTypeName = binTypeName;
	}

	public Long getTotalContentValue() {
		return totalContentValue;
	}

	public void setTotalContentValue(Long totalContentValue) {
		this.totalContentValue = totalContentValue;
	}

	public String getContentValueScale() {
		return contentValueScale;
	}

	public void setContentValueScale(String contentValueScale) {
		this.contentValueScale = contentValueScale;
	}

	public String getBinWeight() {
		return binWeight;
	}

	public void setBinWeight(String binWeight) {
		this.binWeight = binWeight;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getAttachments() {
		return attachments;
	}

	public void setAttachments(String attachments) {
		this.attachments = attachments;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
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

	public String getSpocName() {
		return spocName;
	}

	public void setSpocName(String spocName) {
		this.spocName = spocName;
	}

	public String getSpocPhone() {
		return spocPhone;
	}

	public void setSpocPhone(String spocPhone) {
		this.spocPhone = spocPhone;
	}

	public Long getDriverId() {
		return driverId;
	}

	public void setDriverId(Long driverId) {
		this.driverId = driverId;
	}

	public String getUserFirstname() {
		return userFirstname;
	}

	public void setUserFirstname(String userFirstname) {
		this.userFirstname = userFirstname;
	}

	public String getUserLastname() {
		return userLastname;
	}

	public void setUserLastname(String userLastname) {
		this.userLastname = userLastname;
	}

	public Instant getCreatedTS() {
		return createdTS;
	}

	public void setCreatedTS(Instant createdTS) {
		this.createdTS = createdTS;
	}

	public Instant getLastModifiedTS() {
		return lastModifiedTS;
	}

	public void setLastModifiedTS(Instant lastModifiedTS) {
		this.lastModifiedTS = lastModifiedTS;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getTruckFillWeight() {
		return truckFillWeight;
	}

	public void setTruckFillWeight(String truckFillWeight) {
		this.truckFillWeight = truckFillWeight;
	}

	public Set<BinLocationNotes> getBinLocationNotes() {
		return binLocationNotes;
	}

	public void setBinLocationNotes(Set<BinLocationNotes> binLocationNotes) {
		this.binLocationNotes = binLocationNotes;
	}
	
}
