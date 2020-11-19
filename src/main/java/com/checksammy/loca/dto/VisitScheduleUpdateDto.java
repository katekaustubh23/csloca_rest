package com.checksammy.loca.dto;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.checksammy.loca.model.JobProductService;

public class VisitScheduleUpdateDto {

	private Long visitId;
	
	private Long jobId;
	
	private String jobType;
	
	private String visitTitle;
	
	private String visitInstruction;
	
	private Instant startDate;
	
	private Instant endDate;
	
	private Boolean ifAny;
	
	private Boolean anySchedule;
	
	private String assignTeam;
	
	private String assignDriver;
	
	private Boolean teamEmailFlag;
	
	private Boolean driverEmailFlag;
	
	private Instant endTime;
	
	private Instant startTime;
	
	private Boolean futureVisit;
	
	private Set<JobProductService> jobProductService = new HashSet<>();

	
	public Boolean getAnySchedule() {
		return anySchedule;
	}

	public void setAnySchedule(Boolean anySchedule) {
		this.anySchedule = anySchedule;
	}

	public Long getVisitId() {
		return visitId;
	}

	public void setVisitId(Long visitId) {
		this.visitId = visitId;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public Instant getStartDate() {
		return startDate;
	}

	public void setStartDate(Instant startDate) {
		this.startDate = startDate;
	}

	public Instant getEndDate() {
		return endDate;
	}

	public void setEndDate(Instant endDate) {
		this.endDate = endDate;
	}

	public Boolean getIfAny() {
		return ifAny;
	}

	public void setIfAny(Boolean ifAny) {
		this.ifAny = ifAny;
	}

	public Instant getStartTime() {
		return startTime;
	}

	public void setStartTime(Instant startTime) {
		this.startTime = startTime;
	}

	public Instant getEndTime() {
		return endTime;
	}

	public void setEndTime(Instant endTime) {
		this.endTime = endTime;
	}

	public Set<JobProductService> getJobProductService() {
		return jobProductService;
	}

	public void setJobProductService(Set<JobProductService> jobProductService) {
		this.jobProductService = jobProductService;
	}

	public String getVisitTitle() {
		return visitTitle;
	}

	public void setVisitTitle(String visitTitle) {
		this.visitTitle = visitTitle;
	}

	public String getVisitInstruction() {
		return visitInstruction;
	}

	public void setVisitInstruction(String visitInstruction) {
		this.visitInstruction = visitInstruction;
	}

	public String getAssignTeam() {
		return assignTeam;
	}

	public void setAssignTeam(String assignTeam) {
		this.assignTeam = assignTeam;
	}

	public String getAssignDriver() {
		return assignDriver;
	}

	public void setAssignDriver(String assignDriver) {
		this.assignDriver = assignDriver;
	}

	public Boolean getTeamEmailFlag() {
		return teamEmailFlag;
	}

	public void setTeamEmailFlag(Boolean teamEmailFlag) {
		this.teamEmailFlag = teamEmailFlag;
	}

	public Boolean getDriverEmailFlag() {
		return driverEmailFlag;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public void setDriverEmailFlag(Boolean driverEmailFlag) {
		this.driverEmailFlag = driverEmailFlag;
	}

	public Boolean getFutureVisit() {
		return futureVisit;
	}

	public void setFutureVisit(Boolean futureVisit) {
		this.futureVisit = futureVisit;
	}

	@Override
	public String toString() {
		return "VisitScheduleUpdateDto [visitId=" + visitId + ", jobId=" + jobId + ", visitTitle=" + visitTitle
				+ ", visitInstruction=" + visitInstruction + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", ifAny=" + ifAny + ", startTime=" + startTime + ", endTime=" + endTime + ", jobProductService="
				+ jobProductService + "]";
	}	
	
	
	
}
