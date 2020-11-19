package com.checksammy.loca.dto;

import java.util.List;

public class CollectionWithFromToTodate {
	private String fromDate;
	private String toDate;
	private List<BinExportReportDto> collectionReport;
	
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
	public List<BinExportReportDto> getCollectionReport() {
		return collectionReport;
	}
	public void setCollectionReport(List<BinExportReportDto> collectionReport) {
		this.collectionReport = collectionReport;
	}
	
	
}
