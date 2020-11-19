package com.checksammy.loca.dto;

import java.util.Date;

public class FilterModelDto {

	private Date fromDate;
	private Date toDate;
	private String binType;
	private Long userId;
	private Long locationId;
	
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public String getBinType() {
		return binType;
	}
	public void setBinType(String binType) {
		this.binType = binType;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getLocationId() {
		return locationId;
	}
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}
	@Override
	public String toString() {
		return "FilterModelDto [fromDate=" + fromDate + ", toDate=" + toDate + ", binType=" + binType + ", userId="
				+ userId + ", locationId=" + locationId + "]";
	}
}
