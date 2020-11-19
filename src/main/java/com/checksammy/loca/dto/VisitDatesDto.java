package com.checksammy.loca.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.checksammy.loca.model.RelatedUser;
import com.checksammy.loca.model.VisitJobProductAndServiceMap;
import com.checksammy.loca.model.VisitSchedulerDate;

public class VisitDatesDto {

	private Long id;
	
	private Instant startDate;
	
	private Instant currentDate;
	
	private Instant endDate;
	
	private Instant startTime;
	
	private Instant endTime;
	
	private Long jobId;
	
	private String status;
	
	private Boolean checked;
	
	private Boolean notify;
	
	private Long timeZone;
	
	private String driverReminder;
	
	private String jobType;
	
	private Boolean driverNotify;
	
	private Instant checkedDate;
	
	private Boolean schLater;
	
	private Long checkedBy;
	
	private String checkedByName;
	
	private Boolean anyTime;
	
	private String teamMembers;
	
	private String driverMembers;
	
	private String visitTitle;
	
	private String visitInstruction;
	
	private String assignProductService;
	
	private Boolean paidOnFlag;
	
	private List<RelatedUser> teamMemberUsers;

	private List<RelatedUser> driverMemberUsers;

	private String teamMemberName;

	private String driverMemberName;
	
	private List<VisitJobProductAndServiceMap> visitJobProductMap;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public Instant getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Instant currentDate) {
		this.currentDate = currentDate;
	}

	public Boolean getNotify() {
		return notify;
	}

	public void setNotify(Boolean notify) {
		this.notify = notify;
	}

	public Long getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(Long timeZone) {
		this.timeZone = timeZone;
	}

	public String getDriverReminder() {
		return driverReminder;
	}

	public void setDriverReminder(String driverReminder) {
		this.driverReminder = driverReminder;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public Boolean getDriverNotify() {
		return driverNotify;
	}

	public void setDriverNotify(Boolean driverNotify) {
		this.driverNotify = driverNotify;
	}

	public Instant getCheckedDate() {
		return checkedDate;
	}

	public void setCheckedDate(Instant checkedDate) {
		this.checkedDate = checkedDate;
	}

	public Boolean getSchLater() {
		return schLater;
	}

	public void setSchLater(Boolean schLater) {
		this.schLater = schLater;
	}

	public Long getCheckedBy() {
		return checkedBy;
	}

	public void setCheckedBy(Long checkedBy) {
		this.checkedBy = checkedBy;
	}

	public String getCheckedByName() {
		return checkedByName;
	}

	public void setCheckedByName(String checkedByName) {
		this.checkedByName = checkedByName;
	}

	public Boolean getAnyTime() {
		return anyTime;
	}

	public void setAnyTime(Boolean anyTime) {
		this.anyTime = anyTime;
	}

	public String getTeamMembers() {
		return teamMembers;
	}

	public void setTeamMembers(String teamMembers) {
		this.teamMembers = teamMembers;
	}

	public String getDriverMembers() {
		return driverMembers;
	}

	public void setDriverMembers(String driverMembers) {
		this.driverMembers = driverMembers;
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

	public String getAssignProductService() {
		return assignProductService;
	}

	public void setAssignProductService(String assignProductService) {
		this.assignProductService = assignProductService;
	}

	public Boolean getPaidOnFlag() {
		return paidOnFlag;
	}

	public void setPaidOnFlag(Boolean paidOnFlag) {
		this.paidOnFlag = paidOnFlag;
	}

	public List<VisitJobProductAndServiceMap> getVisitJobProductMap() {
		return visitJobProductMap;
	}

	public void setVisitJobProductMap(List<VisitJobProductAndServiceMap> visitJobProductMap) {
		this.visitJobProductMap = visitJobProductMap;
	}

	public List<RelatedUser> getTeamMemberUsers() {
		return teamMemberUsers;
	}

	public void setTeamMemberUsers(List<RelatedUser> teamMemberUsers) {
		this.teamMemberUsers = teamMemberUsers;
	}

	public List<RelatedUser> getDriverMemberUsers() {
		return driverMemberUsers;
	}

	public void setDriverMemberUsers(List<RelatedUser> driverMemberUsers) {
		this.driverMemberUsers = driverMemberUsers;
	}

	public String getTeamMemberName() {
		return teamMemberName;
	}

	public void setTeamMemberName(String teamMemberName) {
		this.teamMemberName = teamMemberName;
	}

	public String getDriverMemberName() {
		return driverMemberName;
	}

	public void setDriverMemberName(String driverMemberName) {
		this.driverMemberName = driverMemberName;
	}

	@Override
	public String toString() {
		return "VisitSchedularDatesDto [id=" + id + ", startDate=" + startDate + ", currentDate=" + currentDate
				+ ", endDate=" + endDate + ", startTime=" + startTime + ", endTime=" + endTime + ", jobId=" + jobId
				+ ", status=" + status + ", checked=" + checked + "]";
	}

	public VisitDatesDto mapData(VisitSchedulerDate visitSchedulerDate,
			List<VisitJobProductAndServiceMap> visitJobProMap, List<RelatedUser> driverUserList, List<RelatedUser> teamUserList) {
		String driversName = null;
		String teamsName = null;
		VisitDatesDto visitDatesDto = new VisitDatesDto();
		visitDatesDto.setAnyTime(visitSchedulerDate.getAnyTime());
//		visitDatesDto.setAssignProductService(visitJobProMap);
		visitDatesDto.setChecked(visitSchedulerDate.getChecked());
		visitDatesDto.setCheckedBy(visitSchedulerDate.getCheckedBy());
		visitDatesDto.setCheckedByName(visitSchedulerDate.getCheckedByName());
		visitDatesDto.setCheckedDate(visitSchedulerDate.getCheckedDate());
		visitDatesDto.setCurrentDate(visitSchedulerDate.getCurrentDate());
		visitDatesDto.setDriverMembers(visitSchedulerDate.getDriverMembers());
		visitDatesDto.setDriverNotify(visitSchedulerDate.getDriverNotify());
		visitDatesDto.setDriverReminder(visitSchedulerDate.getDriverReminder());
		visitDatesDto.setEndDate(visitSchedulerDate.getEndDate());
		visitDatesDto.setEndTime(visitSchedulerDate.getEndTime());
		visitDatesDto.setId(visitSchedulerDate.getId());
		visitDatesDto.setJobId(visitSchedulerDate.getJobId());
		visitDatesDto.setJobType(visitSchedulerDate.getJobType());
		visitDatesDto.setNotify(visitSchedulerDate.getNotify());
		visitDatesDto.setPaidOnFlag(visitSchedulerDate.getPaidOnFlag());
		visitDatesDto.setSchLater(visitSchedulerDate.getSchLater());
		visitDatesDto.setStartDate(visitSchedulerDate.getStartDate());
		visitDatesDto.setStartTime(visitSchedulerDate.getStartTime());
		visitDatesDto.setStatus(visitSchedulerDate.getStatus());
		visitDatesDto.setTeamMembers(visitSchedulerDate.getTeamMembers());
		visitDatesDto.setTimeZone(visitSchedulerDate.getTimeZone());
		visitDatesDto.setVisitInstruction(visitSchedulerDate.getVisitInstruction());
		visitDatesDto.setVisitJobProductMap(visitJobProMap);
		visitDatesDto.setVisitTitle(visitSchedulerDate.getVisitTitle());
		
		List<String> driverListData = new ArrayList<String>();
		for (RelatedUser relatedUser : driverUserList) {
			String userName = relatedUser.getFirstName() + " "+ relatedUser.getLastName();
			driverListData.add(userName);
		}
		
		List<String> teamListData = new ArrayList<String>();
		for (RelatedUser relatedUser : teamUserList) {
			String userName = relatedUser.getFirstName() + " "+ relatedUser.getLastName();
			teamListData.add(userName);
		}
		
		driversName = String.join(",", driverListData);
		
		teamsName = String.join(",", teamListData);
		visitDatesDto.setDriverMemberUsers(driverUserList);
		visitDatesDto.setTeamMemberUsers(teamUserList);
		visitDatesDto.setDriverMemberName(driversName);
		visitDatesDto.setTeamMemberName(teamsName);
		
		return visitDatesDto;
	}	
	
}
