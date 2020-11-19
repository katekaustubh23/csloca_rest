package com.checksammy.loca.dto;

import java.util.List;

import com.checksammy.loca.model.VisitSchedulerDate;

public class VisitSchedulerDateDto {
	
	private String status;
	
	private String month;
	
	private Integer monthNumber;
	
	private Integer verifyNumber;
	
	private Integer year;
	
	private Integer completedCount;
	
//	private List<VisitSchedulerDate> visitSchedulerDate;VisitJobDateAssignUserDto
	
	private List<VisitJobDateAssignUserDto> visitSchedulerDate;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public List<VisitJobDateAssignUserDto> getVisitSchedulerDate() {
		return visitSchedulerDate;
	}

	public void setVisitSchedulerDate(List<VisitJobDateAssignUserDto> visitSchedulerDate) {
		this.visitSchedulerDate = visitSchedulerDate;
	}

	public Integer getMonthNumber() {
		return monthNumber;
	}

	public void setMonthNumber(Integer monthNumber) {
		this.monthNumber = monthNumber;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getVerifyNumber() {
		return verifyNumber;
	}

	public void setVerifyNumber(Integer verifyNumber) {
		this.verifyNumber = verifyNumber;
	}

	public Integer getCompletedCount() {
		return completedCount;
	}

	public void setCompletedCount(Integer completedCount) {
		this.completedCount = completedCount;
	}
	
}
