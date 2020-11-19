package com.checksammy.loca.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.checksammy.loca.dto.PropertyDetailsSaveDto;

/**
 * 
 * @author kartik.thakre
 *
 */
@Entity
@Table(name="property_details")
@Where(clause = "is_deleted=0")
public class PropertyDetails extends AuditModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;
	@Column(name = "name")
	private String name;
	@Column(name = "account")
	private String account;
	@Column(name = "address")
	private String address;
	@Column(name = "contact_no")
	private String contactNo;
	@Column(name = "is_deleted")
	private Boolean isDeleted;
	
	@OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "propertyDetailId")
//	@Where(clause = "is_deleted=0")
	@OrderBy("id ASC")
	private Set<PropertyDetailNote> propertyNotes = new HashSet<>();
	
	@OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "companyId")
	@Where(clause = "is_deleted=0")
	@OrderBy("id ASC")
	private Set<CompanyCustomField> companyCustomField = new HashSet<>();
	
	@Column(name = "pri_contact_title")
	private String priContactTitle;
	
	@Column(name = "pri_contact_name")
	private String priContactName;
	
	@Column(name = "pri_contact_phone")
	private String priContactPhone;
	
	@Column(name = "pri_contact_email")
	private String priContactEmail;
	
	@Column(name = "sec_contact_title")
	private String secContactTitle;
	
	@Column(name = "sec_contact_name")
	private String secContactName;
	
	@Column(name = "sec_contact_phone")
	private String secContactPhone;
	
	@Column(name = "sec_contact_email")
	private String secContactEmail;
	
	@Column(name = "other_detail_number_of_vehicle")
	private String otherDetailNumberOfVehicle;
	
	@Column(name = "vehicle_type")
	private String vehicleType;
	
	@Column(name = "operating_citys")
	private String operatingCitys;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "state")
	private String state;
	
	@Column(name = "country")
	private String country;
	
	@Column(name = "zip")
	private String zip;
	
	@Column(name = "contract_date")
	private Date contractDate;
	
	@Column(name = "company_website")
	private String companyWebsite;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public Boolean getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public Set<PropertyDetailNote> getPropertyNotes() {
		return propertyNotes;
	}
	public void setPropertyNotes(Set<PropertyDetailNote> propertyNotes) {
		this.propertyNotes = propertyNotes;
	}
	public Set<CompanyCustomField> getCompanyCustomField() {
		return companyCustomField;
	}
	public void setCompanyCustomField(Set<CompanyCustomField> companyCustomField) {
		this.companyCustomField = companyCustomField;
	}
	public String getPriContactTitle() {
		return priContactTitle;
	}
	public void setPriContactTitle(String priContactTitle) {
		this.priContactTitle = priContactTitle;
	}
	public String getPriContactName() {
		return priContactName;
	}
	public void setPriContactName(String priContactName) {
		this.priContactName = priContactName;
	}
	public String getPriContactPhone() {
		return priContactPhone;
	}
	public void setPriContactPhone(String priContactPhone) {
		this.priContactPhone = priContactPhone;
	}
	public String getPriContactEmail() {
		return priContactEmail;
	}
	public void setPriContactEmail(String priContactEmail) {
		this.priContactEmail = priContactEmail;
	}
	public String getSecContactTitle() {
		return secContactTitle;
	}
	public void setSecContactTitle(String secContactTitle) {
		this.secContactTitle = secContactTitle;
	}
	public String getSecContactName() {
		return secContactName;
	}
	public void setSecContactName(String secContactName) {
		this.secContactName = secContactName;
	}
	public String getSecContactPhone() {
		return secContactPhone;
	}
	public void setSecContactPhone(String secContactPhone) {
		this.secContactPhone = secContactPhone;
	}
	public String getSecContactEmail() {
		return secContactEmail;
	}
	public void setSecContactEmail(String secContactEmail) {
		this.secContactEmail = secContactEmail;
	}
	public String getOtherDetailNumberOfVehicle() {
		return otherDetailNumberOfVehicle;
	}
	public void setOtherDetailNumberOfVehicle(String otherDetailNumberOfVehicle) {
		this.otherDetailNumberOfVehicle = otherDetailNumberOfVehicle;
	}
	public String getOperatingCitys() {
		return operatingCitys;
	}
	public void setOperatingCitys(String operatingCitys) {
		this.operatingCitys = operatingCitys;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	public Date getContractDate() {
		return contractDate;
	}
	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
	}
	public String getCompanyWebsite() {
		return companyWebsite;
	}
	public void setCompanyWebsite(String companyWebsite) {
		this.companyWebsite = companyWebsite;
	}
	public PropertyDetails insertDetails(PropertyDetailsSaveDto propertyDetailSave) {
		return null;
	}
}
