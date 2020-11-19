package com.checksammy.loca.dto;

import java.time.Instant;

public class FilterDataClassDto {
	
	private Instant fromDate;
	
	private Instant toDate;
	
	private Long propertyManagement;
	
	private Long binType;
	
	private Long locationId;
	
	private Long createdBy;

	public Instant getFromDate() {
		return fromDate;
	}

	public void setFromDate(Instant fromDate) {
		this.fromDate = fromDate;
	}

	public Instant getToDate() {
		return toDate;
	}

	public void setToDate(Instant toDate) {
		this.toDate = toDate;
	}

	public Long getPropertyManagement() {
		return propertyManagement;
	}

	public void setPropertyManagement(Long propertyManagement) {
		this.propertyManagement = propertyManagement;
	}

	public Long getBinType() {
		return binType;
	}

	public void setBinType(Long binType) {
		this.binType = binType;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

}
