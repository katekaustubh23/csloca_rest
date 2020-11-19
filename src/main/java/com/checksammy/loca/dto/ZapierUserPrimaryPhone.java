package com.checksammy.loca.dto;

import java.time.Instant;

public class ZapierUserPrimaryPhone {

	private Integer id;

	private String number;

	private String description;

	private Boolean sms_allowed;

	private Instant created_at;
	
	private Boolean primary;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getSms_allowed() {
		return sms_allowed;
	}

	public void setSms_allowed(Boolean sms_allowed) {
		this.sms_allowed = sms_allowed;
	}

	public Instant getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Instant created_at) {
		this.created_at = created_at;
	}

	public Boolean getPrimary() {
		return primary;
	}

	public void setPrimary(Boolean primary) {
		this.primary = primary;
	}
	
	
}
