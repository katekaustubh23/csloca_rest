package com.checksammy.loca.ecube.model;

public class Errors {
	
	private String serial;
	private Long error_code;
	private String error_occurred_date;
	private String error_resolved_date;
	private String last_updated_date;
	private Boolean is_resolved;
	
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public Long getError_code() {
		return error_code;
	}
	public void setError_code(Long error_code) {
		this.error_code = error_code;
	}
	public String getError_occurred_date() {
		return error_occurred_date;
	}
	public void setError_occurred_date(String error_occurred_date) {
		this.error_occurred_date = error_occurred_date;
	}
	public String getError_resolved_date() {
		return error_resolved_date;
	}
	public void setError_resolved_date(String error_resolved_date) {
		this.error_resolved_date = error_resolved_date;
	}
	
	public String getLast_updated_date() {
		return last_updated_date;
	}
	public void setLast_updated_date(String last_updated_date) {
		this.last_updated_date = last_updated_date;
	}
	public Boolean getIs_resolved() {
		return is_resolved;
	}
	public void setIs_resolved(Boolean is_resolved) {
		this.is_resolved = is_resolved;
	}
	
	
}
