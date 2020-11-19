package com.checksammy.loca.dto;

import java.util.List;

public class GoodsCollectionReportWithFromAndTodate {
	
	private String fromDate;
	private String toDate;
	private List<GoodsCollectionReportDto> collectionReport;
	
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public List<GoodsCollectionReportDto> getCollectionReport() {
		return collectionReport;
	}
	public void setCollectionReport(List<GoodsCollectionReportDto> collectionReport) {
		this.collectionReport = collectionReport;
	}
	

}
