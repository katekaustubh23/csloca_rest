package com.checksammy.loca.dto;

import java.time.Instant;

public class ZapierUserDto {
	
	private Integer id;

	private String first_name;
	
	private String last_name;
	
	private String company_name;
	
	private Boolean is_company;
	
	private Instant created_at;
	
	private Instant updated_at;
	
	private String primary_phone;

	private String primary_email;
	
//	private ZapierUserPrimaryPhone primary_phone;
//
//	private ZapierPrimaryEmail primary_email;
//	
//	private Boolean has_sms;
//	
//	private Object billing_address;
//	
//	private String tags;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public Boolean getIs_company() {
		return is_company;
	}

	public void setIs_company(Boolean is_company) {
		this.is_company = is_company;
	}

	public Instant getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Instant created_at) {
		this.created_at = created_at;
	}

	public Instant getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Instant updated_at) {
		this.updated_at = updated_at;
	}

//	public Object getPrimary_phone() {
//		return primary_phone;
//	}
//
//	public void setPrimary_phone(Object primary_phone) {
//		this.primary_phone = primary_phone;
//	}
//
	public String getPrimary_email() {
		return primary_email;
	}

	public void setPrimary_email(String primary_email) {
		this.primary_email = primary_email;
	}
//
//	public Boolean getHas_sms() {
//		return has_sms;
//	}
//
//	public void setHas_sms(Boolean has_sms) {
//		this.has_sms = has_sms;
//	}
//
//	public Object getBilling_address() {
//		return billing_address;
//	}
//
//	public void setBilling_address(Object billing_address) {
//		this.billing_address = billing_address;
//	}
//
//	public String getTags() {
//		return tags;
//	}
//
//	public void setTags(String tags) {
//		this.tags = tags;
//	}

	public String getPrimary_phone() {
		return primary_phone;
	}

	public void setPrimary_phone(String primary_phone) {
		this.primary_phone = primary_phone;
	}
	
}
