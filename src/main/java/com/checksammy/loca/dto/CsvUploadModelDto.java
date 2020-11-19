package com.checksammy.loca.dto;

import java.time.Instant;

import com.checksammy.loca.model.Location;

public class CsvUploadModelDto {
	
	private String cassSiteNumber;
	
	private String brand;
	
	private String storeNumber;
	
	private String centerName;
	
	private String locationAddress;
	
	private String locationCity;
	
	private String locationState_Prov;
	
	private String locationZip;
	
	private String locationCountry;

	public String getCassSiteNumber() {
		return cassSiteNumber;
	}

	public void setCassSiteNumber(String cassSiteNumber) {
		this.cassSiteNumber = cassSiteNumber;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getStoreNumber() {
		return storeNumber;
	}

	public void setStoreNumber(String storeNumber) {
		this.storeNumber = storeNumber;
	}

	public String getCenterName() {
		return centerName;
	}

	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}

	public String getLocationAddress() {
		return locationAddress;
	}

	public void setLocationAddress(String locationAddress) {
		this.locationAddress = locationAddress;
	}

	public String getLocationCity() {
		return locationCity;
	}

	public void setLocationCity(String locationCity) {
		this.locationCity = locationCity;
	}

	public String getLocationState_Prov() {
		return locationState_Prov;
	}

	public void setLocationState_Prov(String locationState_Prov) {
		this.locationState_Prov = locationState_Prov;
	}

	public String getLocationZip() {
		return locationZip;
	}

	public void setLocationZip(String locationZip) {
		this.locationZip = locationZip;
	}

	public String getLocationCountry() {
		return locationCountry;
	}

	public void setLocationCountry(String locationCountry) {
		this.locationCountry = locationCountry;
	}

	public Location convertToLocationModel(CsvUploadModelDto csvUploadModelDto, Long userId) {
		Location location = new Location();
		location.setPropertyName(csvUploadModelDto.getCenterName());
		location.setStreetName(csvUploadModelDto.getLocationAddress());
		location.setCity(csvUploadModelDto.getLocationCity());
		location.setPinCode(csvUploadModelDto.getLocationZip());
		location.setState(csvUploadModelDto.getLocationState_Prov());
		location.setCountry(csvUploadModelDto.getLocationCountry());
		try {
			location.setNumberOfUnits(Integer.parseInt(csvUploadModelDto.getCassSiteNumber()));
		} catch (Exception e) {
			location.setNumberOfUnits(0);
		}
		location.setNumberOfUnits(Integer.parseInt(csvUploadModelDto.getCassSiteNumber()));
		location.setCreatedBy(userId);
		location.setCreatedTs(Instant.now());
		location.setUpdatedBy(userId);
		location.setUpdatedTs(Instant.now());
		location.setIsDeleted(false);
		return location;
	}

	
}
