/**
 * 
 */
package com.checksammy.loca.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Abhishek Srivastava
 *
 */
@Entity
@Table(name="ccn_products_details")
public class CCNProductsDetails implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;
	
	@Column(name="serial")
	private String serial;
	
	@Column(name="product_type")
	private String product_type;
	
	@Column(name="latitude")
	private String latitude;

	@Column(name="longitude")
	private String longitude;

	@Column(name="timezone")
	private String timezone;
	
	@Column(name="address")
	private String address;
	
	@Column(name="wastebasket_size")
	private Long wastebasket_size;
	
	@Column(name="description")
	private String description;
	
	@Column(name="current_fill_level")
	private Long current_fill_level;
	
	@Column(name="battery_health")
	private Long battery_health;
	
	@Column(name="temperature")
	private Long temperature;
	
	@Column(name="requested_action")
	private String requested_action;
	
	@Column(name="compaction_count")
	private Long compaction_count;
	
	@Column(name="efl")
	private String EFL;
	
	@Column(name="led_on")
	private String LED_on;
	
	@Column(name="media_on")
	private String media_on;
	
	@Column(name="tilt_angle")
	private Long tilt_angle;
	
	@Column(name="is_turned_on")
	private Boolean is_turned_on;
	
	@Column(name="last_collection_date")
	private String last_collection_date;
	
	@Column(name="last_gps_detected_date")
	private String last_gps_detected_date;
	
	@Column(name="last_booted_date")
	private String last_booted_date;
	
	@Column(name="registered_date")
	private String registered_date;
	
	@Column(name="last_report_date")
	private String last_report_date;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Long getWastebasket_size() {
		return wastebasket_size;
	}

	public void setWastebasket_size(Long wastebasket_size) {
		this.wastebasket_size = wastebasket_size;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getCurrent_fill_level() {
		return current_fill_level;
	}

	public void setCurrent_fill_level(Long current_fill_level) {
		this.current_fill_level = current_fill_level;
	}

	public Long getBattery_health() {
		return battery_health;
	}

	public void setBattery_health(Long battery_health) {
		this.battery_health = battery_health;
	}

	public Long getTemperature() {
		return temperature;
	}

	public void setTemperature(Long temperature) {
		this.temperature = temperature;
	}

	public String getRequested_action() {
		return requested_action;
	}

	public void setRequested_action(String requested_action) {
		this.requested_action = requested_action;
	}

	public Long getCompaction_count() {
		return compaction_count;
	}

	public void setCompaction_count(Long compaction_count) {
		this.compaction_count = compaction_count;
	}

	public String getEFL() {
		return EFL;
	}

	public void setEFL(String eFL) {
		EFL = eFL;
	}

	public String getLED_on() {
		return LED_on;
	}

	public void setLED_on(String lED_on) {
		LED_on = lED_on;
	}

	public String getMedia_on() {
		return media_on;
	}

	public void setMedia_on(String media_on) {
		this.media_on = media_on;
	}

	public Long getTilt_angle() {
		return tilt_angle;
	}

	public void setTilt_angle(Long tilt_angle) {
		this.tilt_angle = tilt_angle;
	}

	public Boolean getIs_turned_on() {
		return is_turned_on;
	}

	public void setIs_turned_on(Boolean is_turned_on) {
		this.is_turned_on = is_turned_on;
	}

	public String getLast_collection_date() {
		return last_collection_date;
	}

	public void setLast_collection_date(String last_collection_date) {
		this.last_collection_date = last_collection_date;
	}

	public String getLast_gps_detected_date() {
		return last_gps_detected_date;
	}

	public void setLast_gps_detected_date(String last_gps_detected_date) {
		this.last_gps_detected_date = last_gps_detected_date;
	}

	public String getLast_booted_date() {
		return last_booted_date;
	}

	public void setLast_booted_date(String last_booted_date) {
		this.last_booted_date = last_booted_date;
	}

	public String getRegistered_date() {
		return registered_date;
	}

	public void setRegistered_date(String registered_date) {
		this.registered_date = registered_date;
	}

	public String getLast_report_date() {
		return last_report_date;
	}

	public void setLast_report_date(String last_report_date) {
		this.last_report_date = last_report_date;
	}
		
}
