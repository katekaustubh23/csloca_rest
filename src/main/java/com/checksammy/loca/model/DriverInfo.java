package com.checksammy.loca.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="driver_info")
public class DriverInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "pr_contact_title")
	private String prContactTitle;
	@Column(name = "pr_contact_name")
	private String prContactName;
	@Column(name = "pr_contact_phone")
	private String prContactPhone;
	@Column(name = "pr_contact_email")
	private String prContactEmail;
	
	@Column(name = "sc_contact_title")
	private String scContactTitle;
	@Column(name = "sc_contact_name")
	private String scContactName;
	@Column(name = "sc_contact_last")
	private String scContactLast;
	@Column(name = "sc_contact_phone")
	private String scContactPhone;
	@Column(name = "sc_contact_email")
	private String scContactEmail;
	
	@Column(name = "company_name")
	private String companyName;
	@Column(name = "company_sign_date")
	private Date companySignDate;
	@Column(name = "company_website")
	private String companyWebsite;
	@Column(name = "company_no_of_vehicles")
	private String companyNoOfVehicles;
	@Column(name = "company_operating_city")
	private String companyOperatingCity;
	
	@Column(name = "company_truck_yard")
	private String companyTruckYard;
	
	@Column(name = "dr_location_str_name")
	private String drLocationStrName;
	@Column(name = "dr_location_str_number")
	private String drLocationStrNumber;
	@Column(name = "dr_location_city")
	private String drLocationCity;
	@Column(name = "dr_location_state")
	private String drLocationState;
	@Column(name = "dr_location_country")
	private String drLocationCountry;
	@Column(name = "dr_location_zipcode")
	private String drLocationZipcode;
	@Column(name = "dr_additional_note")
	private String drAdditionalNote;
	@Column(name = "dr_rating")
	private String drRating;
	
	@Column(name = "latitude")
	private String latitude;
	
	@Column(name = "longitude")
	private String longitude;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getPrContactTitle() {
		return prContactTitle;
	}
	public void setPrContactTitle(String prContactTitle) {
		this.prContactTitle = prContactTitle;
	}
	public String getPrContactName() {
		return prContactName;
	}
	public void setPrContactName(String prContactName) {
		this.prContactName = prContactName;
	}
	public String getPrContactPhone() {
		return prContactPhone;
	}
	public void setPrContactPhone(String prContactPhone) {
		this.prContactPhone = prContactPhone;
	}
	public String getPrContactEmail() {
		return prContactEmail;
	}
	public void setPrContactEmail(String prContactEmail) {
		this.prContactEmail = prContactEmail;
	}
	public String getScContactTitle() {
		return scContactTitle;
	}
	public void setScContactTitle(String scContactTitle) {
		this.scContactTitle = scContactTitle;
	}
	public String getScContactName() {
		return scContactName;
	}
	public void setScContactName(String scContactName) {
		this.scContactName = scContactName;
	}
	public String getScContactPhone() {
		return scContactPhone;
	}
	public void setScContactPhone(String scContactPhone) {
		this.scContactPhone = scContactPhone;
	}
	public String getScContactEmail() {
		return scContactEmail;
	}
	public void setScContactEmail(String scContactEmail) {
		this.scContactEmail = scContactEmail;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Date getCompanySignDate() {
		return companySignDate;
	}
	public void setCompanySignDate(Date companySignDate) {
		this.companySignDate = companySignDate;
	}
	public String getCompanyWebsite() {
		return companyWebsite;
	}
	public void setCompanyWebsite(String companyWebsite) {
		this.companyWebsite = companyWebsite;
	}
	public String getCompanyNoOfVehicles() {
		return companyNoOfVehicles;
	}
	public void setCompanyNoOfVehicles(String companyNoOfVehicles) {
		this.companyNoOfVehicles = companyNoOfVehicles;
	}
	public String getCompanyOperatingCity() {
		return companyOperatingCity;
	}
	public void setCompanyOperatingCity(String companyOperatingCity) {
		this.companyOperatingCity = companyOperatingCity;
	}
	public String getDrLocationStrName() {
		return drLocationStrName;
	}
	public void setDrLocationStrName(String drLocationStrName) {
		this.drLocationStrName = drLocationStrName;
	}
	public String getDrLocationStrNumber() {
		return drLocationStrNumber;
	}
	public void setDrLocationStrNumber(String drLocationStrNumber) {
		this.drLocationStrNumber = drLocationStrNumber;
	}
	public String getDrLocationCity() {
		return drLocationCity;
	}
	public void setDrLocationCity(String drLocationCity) {
		this.drLocationCity = drLocationCity;
	}
	public String getDrLocationState() {
		return drLocationState;
	}
	public void setDrLocationState(String drLocationState) {
		this.drLocationState = drLocationState;
	}
	public String getDrLocationCountry() {
		return drLocationCountry;
	}
	public void setDrLocationCountry(String drLocationCountry) {
		this.drLocationCountry = drLocationCountry;
	}
	public String getDrLocationZipcode() {
		return drLocationZipcode;
	}
	public void setDrLocationZipcode(String drLocationZipcode) {
		this.drLocationZipcode = drLocationZipcode;
	}
	public String getDrAdditionalNote() {
		return drAdditionalNote;
	}
	public void setDrAdditionalNote(String drAdditionalNote) {
		this.drAdditionalNote = drAdditionalNote;
	}
	public String getDrRating() {
		return drRating;
	}
	public void setDrRating(String drRating) {
		this.drRating = drRating;
	}
	public String getCompanyTruckYard() {
		return companyTruckYard;
	}
	public void setCompanyTruckYard(String companyTruckYard) {
		this.companyTruckYard = companyTruckYard;
	}
	public String getScContactLast() {
		return scContactLast;
	}
	public void setScContactLast(String scContactLast) {
		this.scContactLast = scContactLast;
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
}
