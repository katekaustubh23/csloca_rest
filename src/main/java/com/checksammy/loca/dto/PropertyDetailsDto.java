package com.checksammy.loca.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.checksammy.loca.model.PropertyDetailNote;
import com.checksammy.loca.model.PropertyDetails;

public class PropertyDetailsDto {

	private Long id;

	private String name;

	private String account;

	private String address;

	private String contactNo;

	private Boolean isDeleted;
	
	private Long createdBy;

	private Instant createdTs;
	
	private Long updatedBy;

	private Instant updatedTs;

	private Set<PropertyDetailNote> propertyNotes = new HashSet<>();

	private List<CompanyCustomFieldDto> companyCustomField;
	
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

	public List<CompanyCustomFieldDto> getCompanyCustomField() {
		return companyCustomField;
	}

	public void setCompanyCustomField(List<CompanyCustomFieldDto> companyCustomField) {
		this.companyCustomField = companyCustomField;
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

	public PropertyDetailsDto addNewData(PropertyDetails propertyDetails2,
			List<CompanyCustomFieldDto> companyCustomFieldDtos) {
		PropertyDetailsDto propertyDetailsDto = new PropertyDetailsDto();
		propertyDetailsDto.setAccount(propertyDetails2.getAccount());
		propertyDetailsDto.setAddress(propertyDetails2.getAddress());
		propertyDetailsDto.setCompanyCustomField(companyCustomFieldDtos);
		propertyDetailsDto.setContactNo(propertyDetails2.getContactNo());
		propertyDetailsDto.setId(propertyDetails2.getId());
		propertyDetailsDto.setIsDeleted(propertyDetails2.getIsDeleted());
		propertyDetailsDto.setName(propertyDetails2.getName());
		propertyDetailsDto.setPropertyNotes(propertyDetails2.getPropertyNotes());
		propertyDetailsDto.setCreatedBy(propertyDetails2.getCreatedBy());
		propertyDetailsDto.setCreatedTs(propertyDetails2.getCreatedTs());
		
		propertyDetailsDto.setPriContactTitle(propertyDetails2.getPriContactTitle());
		propertyDetailsDto.setPriContactName(propertyDetails2.getPriContactName());
		propertyDetailsDto.setPriContactPhone(propertyDetails2.getPriContactPhone());
		propertyDetailsDto.setPriContactEmail(propertyDetails2.getPriContactEmail());
		
		propertyDetailsDto.setSecContactTitle(propertyDetails2.getSecContactTitle());
		propertyDetailsDto.setSecContactName(propertyDetails2.getSecContactName());
		propertyDetailsDto.setSecContactPhone(propertyDetails2.getSecContactPhone());
		propertyDetailsDto.setSecContactEmail(propertyDetails2.getSecContactEmail());
		
		propertyDetailsDto.setOtherDetailNumberOfVehicle(propertyDetails2.getOtherDetailNumberOfVehicle());
//		propertyDetailsDto.setOperatingCitys(propertyDetails2.getOperatingCitys());
		propertyDetailsDto.setCity(propertyDetails2.getCity());
		propertyDetailsDto.setCountry(propertyDetails2.getCountry());
		propertyDetailsDto.setState(propertyDetails2.getState());
		propertyDetailsDto.setZip(propertyDetails2.getZip());
		propertyDetailsDto.setVehicleType(propertyDetails2.getVehicleType());
		propertyDetailsDto.setContractDate(propertyDetails2.getContractDate());
		propertyDetailsDto.setCompanyWebsite(propertyDetails2.getCompanyWebsite());
		
		if(propertyDetails2.getOperatingCitys() != null && propertyDetails2.getOperatingCitys() != "") {
			String[] elements = propertyDetails2.getOperatingCitys().split(",");
			List<OperatorCityDto> setOpertorCity = new ArrayList<OperatorCityDto>();
			for (String string : elements) {
				OperatorCityDto currentCity = new OperatorCityDto();
				currentCity.setOperatingCity(string);
				setOpertorCity.add(currentCity);
			}
			propertyDetailsDto.setOperatingCitys(setOpertorCity);
		}
		
		return propertyDetailsDto;
	}	
	
}
