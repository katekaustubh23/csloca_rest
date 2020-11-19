package com.checksammy.loca.dto;

public class JobVisitReportFilterDto {
	
	private String visitWithin;
	
	private Boolean visitComplete;
	
	private String assignTo;
	
	private String lineItem;
	
	private String custStartDate;
	
	private String custEndDate;

	private int[] columnArray;
	
	private String toEmail;
	
	private String userName;
	
	private String type;  // for transaction type
	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getToEmail() {
		return toEmail;
	}

	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}

	public int[] getColumnArray() {
		return columnArray;
	}

	public void setColumnArray(int[] columnArray) {
		this.columnArray = columnArray;
	}

	public String getVisitWithin() {
		return visitWithin;
	}

	public void setVisitWithin(String visitWithin) {
		this.visitWithin = visitWithin;
	}

	public Boolean getVisitComplete() {
		return visitComplete;
	}

	public void setVisitComplete(Boolean visitComplete) {
		this.visitComplete = visitComplete;
	}

	public String getAssignTo() {
		return assignTo;
	}

	public void setAssignTo(String assignTo) {
		this.assignTo = assignTo;
	}

	public String getLineItem() {
		return lineItem;
	}

	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}

	public String getCustStartDate() {
		return custStartDate;
	}

	public void setCustStartDate(String custStartDate) {
		this.custStartDate = custStartDate;
	}

	public String getCustEndDate() {
		return custEndDate;
	}

	public void setCustEndDate(String custEndDate) {
		this.custEndDate = custEndDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}	

}
