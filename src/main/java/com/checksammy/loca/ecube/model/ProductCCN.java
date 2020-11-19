/**
 * 
 */
package com.checksammy.loca.ecube.model;

/**
 * This is the model class for Units
 * @author Abhishek Srivastava
 *
 */
public class ProductCCN {
	
	private String serial;
	private String product_type;
	private String latitude;
	private String longitude;
	private String timezone;
	private String address;
	private Long wastebasket_size;
	private String description;
	private Status status;	
	private Dates dates;
	
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public String getProduct_type() {
		return product_type;
	}
	public void setProduct_type(String product_type) {
		this.product_type = product_type;
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
	public String getTimezone() {
		return timezone;
	}
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public Dates getDates() {
		return dates;
	}
	public void setDates(Dates dates) {
		this.dates = dates;
	}
	public Long getWastebasket_size() {
		return wastebasket_size;
	}
	public void setWastebasket_size(Long wastebasket_size) {
		this.wastebasket_size = wastebasket_size;
	}

}