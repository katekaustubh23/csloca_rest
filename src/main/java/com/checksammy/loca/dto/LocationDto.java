package com.checksammy.loca.dto;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;

import com.checksammy.loca.model.Location;
import com.checksammy.loca.model.LocationManager;
import com.checksammy.loca.model.LocationNote;

public class LocationDto {

	private Long id;
	
	private String propertyName;
	
	private String streetNumber;
	
	private String streetName;
	
	private String city;
	
	private String pinCode;
	
	private String state;
	
	private String country;
	
	private String spocName;
	
	private String spocTitle;
	
	private String spocPhone;
	
	private String spocEmailId;
	
	private String fobNumber;
	
	private String sensorSerialNumber;
	
	private String latitude;
	
	private String longitude;
	
	private Integer numberOfUnits;
	
	private Instant binInstallTS;
	
	private Integer numberOfBins;
	
	private String wasteHauling;
	
	private String recyclingCompany;
	
	private String organicsCompany;
	
	private String buildingServices;
	
	private Boolean isDeleted;
	
//	new columns
	
	private LocationManager locationManager;
	
//	 update new fields
	
	private String website;
	
	private Integer locationAreaId;
	
	private String buildingName;
	
	private String noOfCollArea;
	
	private String areaLocked;
	
	private String accessCode;
	
	private String fanceArea;
	
	private String collectionArea;
	
	private String areaOpenUnderground;
	
	private String heightRestriction;
	
	private String collectionType;
	
	private String additionalDetail;
	
	private String venueCode;
	
	private String pictures;

	private Set<LocationNote> locationNotes = new HashSet<>();
	
	private List<LocationCustomFieldDto> locationCustomField;
	
	private String propertyType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getSpocTitle() {
		return spocTitle;
	}

	public void setSpocTitle(String spocTitle) {
		this.spocTitle = spocTitle;
	}

	public String getSpocPhone() {
		return spocPhone;
	}

	public void setSpocPhone(String spocPhone) {
		this.spocPhone = spocPhone;
	}

	public String getSpocEmailId() {
		return spocEmailId;
	}

	public void setSpocEmailId(String spocEmailId) {
		this.spocEmailId = spocEmailId;
	}

	public String getFobNumber() {
		return fobNumber;
	}

	public void setFobNumber(String fobNumber) {
		this.fobNumber = fobNumber;
	}

	public String getSensorSerialNumber() {
		return sensorSerialNumber;
	}

	public void setSensorSerialNumber(String sensorSerialNumber) {
		this.sensorSerialNumber = sensorSerialNumber;
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

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
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

	public List<LocationCustomFieldDto> getLocationCustomField() {
		return locationCustomField;
	}

	public void setLocationCustomField(List<LocationCustomFieldDto> locationCustomField) {
		this.locationCustomField = locationCustomField;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public LocationDto addLocationData(Location location, List<LocationCustomFieldDto> locationCustomFieldDtos) {
		LocationDto locationDto = new LocationDto();
		locationDto.setAccessCode(location.getAccessCode());
		locationDto.setAdditionalDetail(location.getAdditionalDetail());
		locationDto.setAreaLocked(location.getAreaLocked());
		locationDto.setAreaOpenUnderground(location.getAreaOpenUnderground());
		locationDto.setBinInstallTS(location.getBinInstallTS() != null? location.getBinInstallTS(): null);
		locationDto.setBuildingName(location.getBuildingName());
		locationDto.setBuildingServices(location.getBuildingServices());
		locationDto.setCity(location.getCity());
		locationDto.setCollectionArea(location.getCollectionArea());
		locationDto.setCollectionType(location.getCollectionType());
		locationDto.setCountry(location.getCountry());
		locationDto.setFanceArea(location.getFanceArea());
		locationDto.setFobNumber(location.getFobNumber());
		locationDto.setHeightRestriction(location.getHeightRestriction());
		locationDto.setId(location.getId());
		locationDto.setIsDeleted(location.getIsDeleted());
		locationDto.setLatitude(location.getLatitude());
		locationDto.setLocationAreaId(location.getLocationAreaId() != null?location.getLocationAreaId(): 0);
		locationDto.setLocationManager(location.getLocationManager());
		locationDto.setLocationNotes(location.getLocationNotes());
		locationDto.setLongitude(location.getLongitude());
		locationDto.setNoOfCollArea(location.getNoOfCollArea());
		locationDto.setNumberOfBins(location.getNumberOfBins());
		locationDto.setNumberOfUnits(location.getNumberOfUnits());
		locationDto.setOrganicsCompany(location.getOrganicsCompany());
		locationDto.setPictures(location.getPictures());
		locationDto.setPinCode(location.getPinCode());
		locationDto.setPropertyName(location.getPropertyName());
		locationDto.setRecyclingCompany(location.getRecyclingCompany());
		locationDto.setSensorSerialNumber(location.getSensorSerialNumber());
		locationDto.setSpocEmailId(location.getSpocEmailId());
		locationDto.setSpocName(location.getSpocName());
		locationDto.setSpocPhone(location.getSpocPhone());
		locationDto.setSpocTitle(location.getSpocTitle());
		locationDto.setState(location.getState());
		locationDto.setStreetName(location.getStreetName());
		locationDto.setStreetNumber(location.getStreetNumber());
		locationDto.setVenueCode(location.getVenueCode());
		locationDto.setWasteHauling(location.getWasteHauling());
		locationDto.setWebsite(location.getWebsite());
		locationDto.setLocationCustomField(locationCustomFieldDtos);
		locationDto.setPropertyType(location.getPropertyType());
		return locationDto;
	}
	
	
}
