/**
 * 
 */
package com.checksammy.loca.ecube.model;

import java.util.List;

/**
 * Current status of unit
 * @author Abhishek Srivastava
 *
 */
public class Status {
	
	private Long current_fill_level;
	private Long battery_health;
	private Long temperature;
	private String requested_action;
	private Long compaction_count;
	private Long EFL;
	private Boolean LED_on;
	private Boolean media_on;
	private Long tilt_angle;
	private Boolean is_turned_on;
	private List<Errors> errors;
	
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
	
	public Long getEFL() {
		return EFL;
	}
	public void setEFL(Long eFL) {
		EFL = eFL;
	}
	public Boolean getLED_on() {
		return LED_on;
	}
	public void setLED_on(Boolean lED_on) {
		LED_on = lED_on;
	}
	public Boolean getMedia_on() {
		return media_on;
	}
	public void setMedia_on(Boolean media_on) {
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
	public List<Errors> getErrors() {
		return errors;
	}
	public void setErrors(List<Errors> errors) {
		this.errors = errors;
	}
	
	
}
