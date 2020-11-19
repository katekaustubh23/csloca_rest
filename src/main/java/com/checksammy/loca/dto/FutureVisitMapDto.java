package com.checksammy.loca.dto;

public class FutureVisitMapDto {
	
	private Long visitId;
	
	private Boolean timeOfDay;
	
	private Boolean visitFreq;
	
	private Boolean assignTeam;
	
	private Boolean assignDriver;
	
	private Boolean lineItem;

	public Long getVisitId() {
		return visitId;
	}

	public void setVisitId(Long visitId) {
		this.visitId = visitId;
	}

	public Boolean getTimeOfDay() {
		return timeOfDay;
	}

	public void setTimeOfDay(Boolean timeOfDay) {
		this.timeOfDay = timeOfDay;
	}

	public Boolean getVisitFreq() {
		return visitFreq;
	}

	public void setVisitFreq(Boolean visitFreq) {
		this.visitFreq = visitFreq;
	}

	public Boolean getAssignTeam() {
		return assignTeam;
	}

	public void setAssignTeam(Boolean assignTeam) {
		this.assignTeam = assignTeam;
	}

	public Boolean getAssignDriver() {
		return assignDriver;
	}

	public void setAssignDriver(Boolean assignDriver) {
		this.assignDriver = assignDriver;
	}

	public Boolean getLineItem() {
		return lineItem;
	}

	public void setLineItem(Boolean lineItem) {
		this.lineItem = lineItem;
	}

}
