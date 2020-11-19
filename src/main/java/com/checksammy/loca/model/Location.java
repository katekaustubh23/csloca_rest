package com.checksammy.loca.model;

import java.time.Instant;
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

/**
 * 
 * @author kartik.thakre
 *
 */
@Entity
@Table(name="location")
@Where(clause="is_deleted=0")
public class Location extends AuditModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "property_name")
	private String propertyName;
	
	@Column(name = "street_number")
	private String streetNumber;
	
	@Column(name = "street_name")
	private String streetName;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "pincode")
	private String pinCode;
	
	@Column(name = "state")
	private String state;
	
	@Column(name = "country")
	private String country;
	
	@Column(name="spoc_name")
	private String spocName;
	
	@Column(name="spoc_title")
	private String spocTitle;
	
	@Column(name="spoc_phone")
	private String spocPhone;
	
	@Column(name="spoc_email")
	private String spocEmailId;
	
	@Column(name="fob_number")
	private String fobNumber;
	
	@Column(name="sensor_serial_no")
	private String sensorSerialNumber;
	
	@Column(name="latitude")
	private String latitude;
	
	@Column(name="longitude")
	private String longitude;
	
	@Column(name="no_of_units")
	private Integer numberOfUnits;
	
	@Column(name="bin_install_date")
	private Instant binInstallTS;
	
	@Column(name="no_of_bins")
	private Integer numberOfBins;
	
	@Column(name="waste_hauling")
	private String wasteHauling;
	
	@Column(name="recycling_company")
	private String recyclingCompany;
	
	@Column(name="organics_company")
	private String organicsCompany;
	
	@Column(name="building_services")
	private String buildingServices;
	
	@Column(name="is_deleted")
	private Boolean isDeleted;
	
//	new columns
	
	@JoinColumn(name = "location_manager_id", referencedColumnName = "id")
	@ManyToOne()
	private LocationManager locationManager;
	
//	 update new fields
	
	@Column(name="website")
	private String website;
	
	@Column(name="location_area_id")
	private Integer locationAreaId;
	
	@Column(name="building_name")
	private String buildingName;
	
	@Column(name="no_of_coll_area")
	private String noOfCollArea;
	
	@Column(name="area_locked")
	private String areaLocked;
	
	@Column(name="access_code")
	private String accessCode;
	
	@Column(name="fance_area")
	private String fanceArea;
	
	@Column(name="collection_area")
	private String collectionArea;
	
	@Column(name="area_open_underground")
	private String areaOpenUnderground;
	
	@Column(name="height_restriction")
	private String heightRestriction;
	
	@Column(name="collection_type")
	private String collectionType;
	
	@Column(name="additional_detail")
	private String additionalDetail;
	
	@Column(name="venue_code")
	private String venueCode;
	
	@Column(name="pictures")
	private String pictures;
	
	@OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "locationId")
//	@Where(clause = "is_deleted=0")
	@OrderBy("id ASC")
	private Set<LocationNote> locationNotes = new HashSet<>();
	
	@OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "locationId")
	@Where(clause = "is_deleted=0")
	@OrderBy("id ASC")
	private Set<LocationCustomField> locationCustomField = new HashSet<>();
	
	@Column(name="property_type")
	private String propertyType;
	
	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
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

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
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

	public String getFobNumber() {
		return fobNumber;
	}

	public void setFobNumber(String fobNumber) {
		this.fobNumber = fobNumber;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getSpocTitle() {
		return spocTitle;
	}

	public void setSpocTitle(String spocTitle) {
		this.spocTitle = spocTitle;
	}

	public String getSpocEmailId() {
		return spocEmailId;
	}

	public void setSpocEmailId(String spocEmailId) {
		this.spocEmailId = spocEmailId;
	}

	public String getSensorSerialNumber() {
		return sensorSerialNumber;
	}

	public void setSensorSerialNumber(String sensorSerialNumber) {
		this.sensorSerialNumber = sensorSerialNumber;
	}

	public Integer getNumberOfUnits() {
		return numberOfUnits;
	}

	public void setNumberOfUnits(Integer numberOfUnits) {
		this.numberOfUnits = numberOfUnits;
	}

	public Instant getBinInstallTS() {
		return binInstallTS;
	}

	public void setBinInstallTS(Instant binInstallTS) {
		this.binInstallTS = binInstallTS;
	}

	public Integer getNumberOfBins() {
		return numberOfBins;
	}

	public void setNumberOfBins(Integer numberOfBins) {
		this.numberOfBins = numberOfBins;
	}

	public String getWasteHauling() {
		return wasteHauling;
	}

	public void setWasteHauling(String wasteHauling) {
		this.wasteHauling = wasteHauling;
	}

	public String getRecyclingCompany() {
		return recyclingCompany;
	}

	public void setRecyclingCompany(String recyclingCompany) {
		this.recyclingCompany = recyclingCompany;
	}

	public String getOrganicsCompany() {
		return organicsCompany;
	}

	public void setOrganicsCompany(String organicsCompany) {
		this.organicsCompany = organicsCompany;
	}

	public String getBuildingServices() {
		return buildingServices;
	}

	public void setBuildingServices(String buildingServices) {
		this.buildingServices = buildingServices;
	}

	public LocationManager getLocationManager() {
		return locationManager;
	}

	public void setLocationManager(LocationManager locationManager) {
		this.locationManager = locationManager;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public Integer getLocationAreaId() {
		return locationAreaId;
	}

	public void setLocationAreaId(Integer locationAreaId) {
		this.locationAreaId = locationAreaId;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public String getNoOfCollArea() {
		return noOfCollArea;
	}

	public void setNoOfCollArea(String noOfCollArea) {
		this.noOfCollArea = noOfCollArea;
	}

	public String getAreaLocked() {
		return areaLocked;
	}

	public void setAreaLocked(String areaLocked) {
		this.areaLocked = areaLocked;
	}

	public String getAccessCode() {
		return accessCode;
	}

	public void setAccessCode(String accessCode) {
		this.accessCode = accessCode;
	}

	public String getFanceArea() {
		return fanceArea;
	}

	public void setFanceArea(String fanceArea) {
		this.fanceArea = fanceArea;
	}

	public String getCollectionArea() {
		return collectionArea;
	}

	public void setCollectionArea(String collectionArea) {
		this.collectionArea = collectionArea;
	}

	public String getAreaOpenUnderground() {
		return areaOpenUnderground;
	}

	public void setAreaOpenUnderground(String areaOpenUnderground) {
		this.areaOpenUnderground = areaOpenUnderground;
	}

	public String getHeightRestriction() {
		return heightRestriction;
	}

	public void setHeightRestriction(String heightRestriction) {
		this.heightRestriction = heightRestriction;
	}

	public String getCollectionType() {
		return collectionType;
	}

	public void setCollectionType(String collectionType) {
		this.collectionType = collectionType;
	}

	public String getAdditionalDetail() {
		return additionalDetail;
	}

	public void setAdditionalDetail(String additionalDetail) {
		this.additionalDetail = additionalDetail;
	}

	public String getVenueCode() {
		return venueCode;
	}

	public void setVenueCode(String venueCode) {
		this.venueCode = venueCode;
	}

	public String getPictures() {
		return pictures;
	}

	public void setPictures(String pictures) {
		this.pictures = pictures;
	}

	public Set<LocationNote> getLocationNotes() {
		return locationNotes;
	}

	public void setLocationNotes(Set<LocationNote> locationNotes) {
		this.locationNotes = locationNotes;
	}

	public Set<LocationCustomField> getLocationCustomField() {
		return locationCustomField;
	}

	public void setLocationCustomField(Set<LocationCustomField> locationCustomField) {
		this.locationCustomField = locationCustomField;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

}
