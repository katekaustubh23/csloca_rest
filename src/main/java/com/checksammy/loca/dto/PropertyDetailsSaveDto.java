package com.checksammy.loca.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.checksammy.loca.model.CompanyCustomField;
import com.checksammy.loca.model.PropertyDetailNote;
import com.checksammy.loca.model.PropertyDetails;

public class PropertyDetailsSaveDto {

	private Long id;
	private String name;
	private String account;
	private String address;
	private String contactNo;
	private Boolean isDeleted;

	private Set<PropertyDetailNote> propertyNotes = new HashSet<>();

	private Set<CompanyCustomField> companyCustomField = new HashSet<>();

	private String priContactTitle;

	private String priContactName;

	private String priContactPhone;

	private String priContactEmail;

	private String secContactTitle;

	private String secContactName;

	private String secContactPhone;

	private String secContactEmail;

	private String otherDetailNumberOfVehicle;

	private String vehicleType;

	private List<OperatorCityDto> operatingCitys;

	private String city;

	private String state;

	private String country;

	private String zip;

	private Date contractDate;

	private String companyWebsite;
	
    private Long createdBy;    
	
    private Instant createdTs;
    
    private Long updatedBy;

    private Instant updatedTs;   

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

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public List<OperatorCityDto> getOperatingCitys() {
		return operatingCitys;
	}

	public void setOperatingCitys(List<OperatorCityDto> operatingCitys) {
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

	

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Instant getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(Instant createdTs) {
		this.createdTs = createdTs;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Instant getUpdatedTs() {
		return updatedTs;
	}

	public void setUpdatedTs(Instant updatedTs) {
		this.updatedTs = updatedTs;
	}
	
	public PropertyDetails insertDetails(PropertyDetailsSaveDto propertyDetailSave) {
		PropertyDetails propertyDetails = new PropertyDetails();
		propertyDetails.setAccount(propertyDetailSave.getAccount());
		propertyDetails.setAddress(propertyDetailSave.getAddress());
		propertyDetails.setCity(propertyDetailSave.getCity());
		propertyDetails.setCompanyCustomField(propertyDetailSave.getCompanyCustomField());
		propertyDetails.setCompanyWebsite(propertyDetailSave.getCompanyWebsite());
		propertyDetails.setContactNo(propertyDetailSave.getContactNo());
		propertyDetails.setContractDate(propertyDetailSave.getContractDate());
		
		propertyDetails.setCountry(propertyDetailSave.getCountry());
		propertyDetails.setCreatedBy(propertyDetailSave.getCreatedBy());
		propertyDetails.setCreatedTs(propertyDetailSave.getCreatedTs());
		propertyDetails.setId(propertyDetailSave.getId());
		propertyDetails.setIsDeleted(propertyDetailSave.getIsDeleted());
		
		propertyDetails.setName(propertyDetailSave.getName());
		propertyDetails.setOtherDetailNumberOfVehicle(propertyDetailSave.getOtherDetailNumberOfVehicle());
		propertyDetails.setPriContactEmail(propertyDetailSave.getPriContactEmail());
		propertyDetails.setPriContactName(propertyDetailSave.getPriContactName());
		propertyDetails.setPriContactPhone(propertyDetailSave.getPriContactPhone());
		propertyDetails.setPriContactTitle(propertyDetailSave.getPriContactTitle());
		
		propertyDetails.setPropertyNotes(propertyDetailSave.getPropertyNotes());
		propertyDetails.setSecContactEmail(propertyDetailSave.getSecContactEmail());
		propertyDetails.setSecContactName(propertyDetailSave.getSecContactName());
		propertyDetails.setSecContactPhone(propertyDetailSave.getSecContactPhone());
		propertyDetails.setSecContactTitle(propertyDetailSave.getSecContactTitle());
		propertyDetails.setState(propertyDetailSave.getState());
		propertyDetails.setUpdatedBy(propertyDetailSave.getUpdatedBy());
		propertyDetails.setUpdatedTs(propertyDetailSave.getUpdatedTs());
		propertyDetails.setVehicleType(propertyDetailSave.getVehicleType());
		propertyDetails.setZip(propertyDetailSave.getZip());
		
		List<String> operatorCityList = new ArrayList<String>();
		
		for (OperatorCityDto string : propertyDetailSave.getOperatingCitys()) {
			operatorCityList.add(string.getOperatingCity());
		}
		if(operatorCityList.size() > 0) {
			propertyDetails.setOperatingCitys(String.join(",", operatorCityList));
		}
		
		
		return propertyDetails;
	}
	
}
