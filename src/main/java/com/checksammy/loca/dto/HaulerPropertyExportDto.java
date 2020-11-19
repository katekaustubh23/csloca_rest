package com.checksammy.loca.dto;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.checksammy.loca.model.PropertyDetails;
import com.checksammy.loca.model.User;

public class HaulerPropertyExportDto {

	private String haulerCompany;

	private String conSingedDate;

	private String address;

	private String city;

	private String state;

	private String zip;

	private String priContName;

	private String priContTitle;
	private String priPhone;
	private String priEmail;
	private String secContName;
	private String secContTitle;
	private String secPhone;
	private String secContEmail;
	private String webSite;
	private String noOfTruck;
	private String truckSize;
	private String opCity1;
	private String opCity2;
	private String opCity3;
	private String opCity4;
	private String opCity5;
	private String opCity6;
	private String opCity7;
	private String addiNote;

	public String getHaulerCompany() {
		return haulerCompany;
	}

	public void setHaulerCompany(String haulerCompany) {
		this.haulerCompany = haulerCompany;
	}

	public String getConSingedDate() {
		return conSingedDate;
	}

	public void setConSingedDate(String conSingedDate) {
		this.conSingedDate = conSingedDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getPriContName() {
		return priContName;
	}

	public void setPriContName(String priContName) {
		this.priContName = priContName;
	}

	public String getPriContTitle() {
		return priContTitle;
	}

	public void setPriContTitle(String priContTitle) {
		this.priContTitle = priContTitle;
	}

	public String getPriPhone() {
		return priPhone;
	}

	public void setPriPhone(String priPhone) {
		this.priPhone = priPhone;
	}

	public String getPriEmail() {
		return priEmail;
	}

	public void setPriEmail(String priEmail) {
		this.priEmail = priEmail;
	}

	public String getSecContName() {
		return secContName;
	}

	public void setSecContName(String secContName) {
		this.secContName = secContName;
	}

	public String getSecContTitle() {
		return secContTitle;
	}

	public void setSecContTitle(String secContTitle) {
		this.secContTitle = secContTitle;
	}

	public String getSecPhone() {
		return secPhone;
	}

	public void setSecPhone(String secPhone) {
		this.secPhone = secPhone;
	}

	public String getSecContEmail() {
		return secContEmail;
	}

	public void setSecContEmail(String secContEmail) {
		this.secContEmail = secContEmail;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public String getNoOfTruck() {
		return noOfTruck;
	}

	public void setNoOfTruck(String noOfTruck) {
		this.noOfTruck = noOfTruck;
	}

	public String getTruckSize() {
		return truckSize;
	}

	public void setTruckSize(String truckSize) {
		this.truckSize = truckSize;
	}

	public String getOpCity1() {
		return opCity1;
	}

	public void setOpCity1(String opCity1) {
		this.opCity1 = opCity1;
	}

	public String getOpCity2() {
		return opCity2;
	}

	public void setOpCity2(String opCity2) {
		this.opCity2 = opCity2;
	}

	public String getOpCity3() {
		return opCity3;
	}

	public void setOpCity3(String opCity3) {
		this.opCity3 = opCity3;
	}

	public String getOpCity4() {
		return opCity4;
	}

	public void setOpCity4(String opCity4) {
		this.opCity4 = opCity4;
	}

	public String getOpCity5() {
		return opCity5;
	}

	public void setOpCity5(String opCity5) {
		this.opCity5 = opCity5;
	}

	public String getOpCity6() {
		return opCity6;
	}

	public void setOpCity6(String opCity6) {
		this.opCity6 = opCity6;
	}

	public String getOpCity7() {
		return opCity7;
	}

	public void setOpCity7(String opCity7) {
		this.opCity7 = opCity7;
	}

	public String getAddiNote() {
		return addiNote;
	}

	public void setAddiNote(String addiNote) {
		this.addiNote = addiNote;
	}

	public PropertyDetails insertData(HaulerPropertyExportDto haulerPropertyExportDto, User user,
			PropertyDetails propertyDetails) throws ParseException {

		if (haulerPropertyExportDto.getPriPhone() != null
				&& !haulerPropertyExportDto.getPriPhone().equalsIgnoreCase("")) {
			String numberOnly = haulerPropertyExportDto.getPriPhone().replaceAll("[^0-9]", "");
			propertyDetails.setPriContactPhone(numberOnly);
			propertyDetails.setContactNo(numberOnly);
		}
		if (haulerPropertyExportDto.getSecPhone() != null
				&& !haulerPropertyExportDto.getSecPhone().equalsIgnoreCase("")) {
			String numberOnly = haulerPropertyExportDto.getSecPhone().replaceAll("[^0-9]", "");
			propertyDetails.setSecContactPhone(numberOnly);
		}

		List<String> operatingCity = new ArrayList<>();
		propertyDetails.setAddress(haulerPropertyExportDto.getAddress());
		propertyDetails.setCity(haulerPropertyExportDto.getCity());
		propertyDetails.setCompanyWebsite(haulerPropertyExportDto.getWebSite());

		if (!haulerPropertyExportDto.getConSingedDate().equals(null)
				&& !haulerPropertyExportDto.getConSingedDate().equals("")) {
			DateFormat format = new SimpleDateFormat("dd-MMM-yy");
			propertyDetails.setContractDate(format.parse(haulerPropertyExportDto.getConSingedDate()));
		}

		propertyDetails.setCreatedBy(user.getUserId());
		propertyDetails.setCreatedTs(Instant.now());
		propertyDetails.setIsDeleted(false);

		propertyDetails.setName(haulerPropertyExportDto.getHaulerCompany());
		propertyDetails.setOtherDetailNumberOfVehicle(haulerPropertyExportDto.getNoOfTruck());
		propertyDetails.setPriContactEmail(haulerPropertyExportDto.getPriEmail());
		propertyDetails.setPriContactName(haulerPropertyExportDto.getPriContName());

		propertyDetails.setPriContactTitle(haulerPropertyExportDto.getPriContTitle());

		propertyDetails.setSecContactEmail(haulerPropertyExportDto.getSecContEmail());
		propertyDetails.setSecContactName(haulerPropertyExportDto.getSecContName());
		propertyDetails.setSecContactPhone(haulerPropertyExportDto.getSecPhone());
		propertyDetails.setSecContactTitle(haulerPropertyExportDto.getSecContTitle());
		propertyDetails.setState(haulerPropertyExportDto.getState());
		if (propertyDetails.getId() != null && propertyDetails.getId() > 0) {
			propertyDetails.setUpdatedBy(user.getUserId());
			propertyDetails.setUpdatedTs(Instant.now());
		} else {
			propertyDetails.setUpdatedBy(user.getUserId());
			propertyDetails.setUpdatedTs(Instant.now());
		}
		if (!haulerPropertyExportDto.getOpCity1().equals(null) && haulerPropertyExportDto.getOpCity1() != "") {
			operatingCity.add(haulerPropertyExportDto.getOpCity1());
		}
		if (!haulerPropertyExportDto.getOpCity2().equals(null) && haulerPropertyExportDto.getOpCity2() != "") {
			operatingCity.add(haulerPropertyExportDto.getOpCity2());
		}
		if (!haulerPropertyExportDto.getOpCity3().equals(null) && haulerPropertyExportDto.getOpCity3() != "") {
			operatingCity.add(haulerPropertyExportDto.getOpCity3());
		}
		if (!haulerPropertyExportDto.getOpCity4().equals(null) && haulerPropertyExportDto.getOpCity4() != "") {
			operatingCity.add(haulerPropertyExportDto.getOpCity4());
		}
		if (!haulerPropertyExportDto.getOpCity5().equals(null) && haulerPropertyExportDto.getOpCity5() != "") {
			operatingCity.add(haulerPropertyExportDto.getOpCity5());
		}
		if (!haulerPropertyExportDto.getOpCity6().equals(null) && haulerPropertyExportDto.getOpCity6() != "") {
			operatingCity.add(haulerPropertyExportDto.getOpCity6());
		}
		if (!haulerPropertyExportDto.getOpCity7().equals(null) && haulerPropertyExportDto.getOpCity7() != "") {
			operatingCity.add(haulerPropertyExportDto.getOpCity7());
		}
		if (operatingCity.size() > 0) {
			propertyDetails.setOperatingCitys(String.join(",", operatingCity));
		}

		propertyDetails.setVehicleType(haulerPropertyExportDto.getTruckSize());
		propertyDetails.setZip(haulerPropertyExportDto.getZip());
		
		return propertyDetails;
	}

}
