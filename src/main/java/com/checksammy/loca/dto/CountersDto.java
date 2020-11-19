package com.checksammy.loca.dto;

public class CountersDto {
	
	public Long userCount;
	public Long locationCount;
	public Long binCount;
	
	public Long getUserCount() {
		return userCount;
	}
	public void setUserCount(Long userCount) {
		this.userCount = userCount;
	}
	public Long getLocationCount() {
		return locationCount;
	}
	public void setLocationCount(Long locationCount) {
		this.locationCount = locationCount;
	}
	public Long getBinCount() {
		return binCount;
	}
	public void setBinCount(Long binCount) {
		this.binCount = binCount;
	}
	
}
