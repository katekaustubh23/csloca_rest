/**
 * 
 */
package com.checksammy.loca.ecube.model;

import java.util.List;

import com.checksammy.loca.model.CCNProductsDetails;

/**
 * @author Abhishek Srivastava
 *
 */
public class ProductCCNs {

	public List<ProductCCN> products;

	public List<ProductCCN> getProducts() {
		return products;
	}

	public void setProducts(List<ProductCCN> products) {
		this.products = products;
	}

	public CCNProductsDetails getCCNProductDataFetch(ProductCCN productCCN) {
		CCNProductsDetails ccnProductsDetails = new CCNProductsDetails();
		ccnProductsDetails.setSerial(productCCN.getSerial());
		ccnProductsDetails.setProduct_type(productCCN.getProduct_type());
		ccnProductsDetails.setLatitude(productCCN.getLatitude());
		ccnProductsDetails.setLongitude(productCCN.getLongitude());
		ccnProductsDetails.setTimezone(productCCN.getTimezone());
		ccnProductsDetails.setAddress(productCCN.getAddress());
		ccnProductsDetails
				.setWastebasket_size(productCCN.getWastebasket_size() != null ? productCCN.getWastebasket_size() : 0L);
		ccnProductsDetails.setDescription(productCCN.getDescription());
		ccnProductsDetails.setCurrent_fill_level(
				productCCN.getStatus().getCurrent_fill_level() != null ? productCCN.getStatus().getCurrent_fill_level()
						: 0);
		ccnProductsDetails.setBattery_health(productCCN.getStatus().getBattery_health());
		ccnProductsDetails.setTemperature(
				productCCN.getStatus().getTemperature() != null ? productCCN.getStatus().getTemperature() : 0);
		ccnProductsDetails.setRequested_action(productCCN.getStatus().getRequested_action());
		ccnProductsDetails.setCompaction_count(
				productCCN.getStatus().getCompaction_count() != null ? productCCN.getStatus().getCompaction_count()
						: 0);
//		ccnProductsDetails.setEFL(eFL);
//		ccnProductsDetails.setLED_on(lED_on);
//		ccnProductsDetails.setMedia_on();
//		ccnProductsDetails.setTilt_angle(tilt_angle);
		ccnProductsDetails.setLast_collection_date(productCCN.getDates().getLast_collection_date());

		return ccnProductsDetails;
	}

	public ProductCCN addSaveData(CCNProductsDetails ccnProductsDetails) {
		Status status = new Status();
		ProductCCN productCCN = new ProductCCN();
		Dates dates = new Dates();
		status.setCurrent_fill_level(ccnProductsDetails.getCurrent_fill_level());
		status.setBattery_health(ccnProductsDetails.getBattery_health());
		status.setTemperature(ccnProductsDetails.getTemperature());
		status.setRequested_action(ccnProductsDetails.getRequested_action());
		status.setCompaction_count(ccnProductsDetails.getCompaction_count());

		dates.setLast_collection_date(ccnProductsDetails.getLast_collection_date());

		productCCN.setSerial(ccnProductsDetails.getSerial());
		productCCN.setProduct_type(ccnProductsDetails.getProduct_type());
		productCCN.setLatitude(ccnProductsDetails.getLatitude());
		productCCN.setLongitude(ccnProductsDetails.getLongitude());
		productCCN.setTimezone(ccnProductsDetails.getTimezone());
		productCCN.setAddress(ccnProductsDetails.getAddress());
		productCCN.setWastebasket_size(
				ccnProductsDetails.getWastebasket_size() != null ? ccnProductsDetails.getWastebasket_size() : 0L);
		productCCN.setDescription(ccnProductsDetails.getDescription());

		productCCN.setStatus(status);
		productCCN.setDates(dates);

		return productCCN;
	}

}