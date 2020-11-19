package com.checksammy.loca.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.checksammy.loca.model.RelatedUser;
import com.checksammy.loca.model.VisitSchedulerDate;

public class VisitJobDateAssignUserDto {

	private Long id;

	private Long jobId;

	private Instant startDate;

	private Instant endDate;

	private Instant currentDate;

	private Instant startTime;

	private Instant endTime;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Instant getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Instant currentDate) {
		this.currentDate = currentDate;
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

	public VisitJobDateAssignUserDto mappingDataWithVisit(VisitSchedulerDate visitSchedulerDate,
			List<RelatedUser> driverUserList, List<RelatedUser> teamUserList) {
		VisitJobDateAssignUserDto visitJobDateAssignUserDto = new VisitJobDateAssignUserDto();
		String driversName = null;
		String teamsName = null;

		Calendar makeEndTime = new GregorianCalendar();
		if (visitSchedulerDate.getCurrentDate() != null) {
			makeEndTime.setTime(Date.from(visitSchedulerDate.getCurrentDate()));
			if(visitSchedulerDate.getAnyTime() != null && visitSchedulerDate.getAnyTime()) {
				makeEndTime.set(Calendar.HOUR_OF_DAY, Date.from(visitSchedulerDate.getEndTime()).getHours());
				makeEndTime.set(Calendar.MINUTE, Date.from(visitSchedulerDate.getEndTime()).getMinutes());
			}else {
				makeEndTime.set(Calendar.HOUR_OF_DAY, 0);
				makeEndTime.set(Calendar.MINUTE, 0);
			}
		}

		visitJobDateAssignUserDto.setAnyTime(visitSchedulerDate.getAnyTime());
//		visitDatesDto.setAssignProductService(visitJobProMap);
		visitJobDateAssignUserDto.setChecked(visitSchedulerDate.getChecked());
		visitJobDateAssignUserDto.setCheckedBy(visitSchedulerDate.getCheckedBy());
		visitJobDateAssignUserDto.setCheckedByName(visitSchedulerDate.getCheckedByName());
		visitJobDateAssignUserDto.setCheckedDate(visitSchedulerDate.getCheckedDate());
		visitJobDateAssignUserDto.setCurrentDate(visitSchedulerDate.getCurrentDate());
		visitJobDateAssignUserDto.setDriverMembers(visitSchedulerDate.getDriverMembers());
		visitJobDateAssignUserDto.setDriverNotify(visitSchedulerDate.getDriverNotify());
		visitJobDateAssignUserDto.setDriverReminder(visitSchedulerDate.getDriverReminder());
		visitJobDateAssignUserDto.setEndDate(visitSchedulerDate.getEndDate());
		visitJobDateAssignUserDto.setEndTime(makeEndTime.toInstant());
		visitJobDateAssignUserDto.setId(visitSchedulerDate.getId());
		visitJobDateAssignUserDto.setJobId(visitSchedulerDate.getJobId());
		visitJobDateAssignUserDto.setJobType(visitSchedulerDate.getJobType());
		visitJobDateAssignUserDto.setNotify(visitSchedulerDate.getNotify());
		visitJobDateAssignUserDto.setPaidOnFlag(visitSchedulerDate.getPaidOnFlag());
		visitJobDateAssignUserDto.setSchLater(visitSchedulerDate.getSchLater());
		visitJobDateAssignUserDto.setStartDate(visitSchedulerDate.getStartDate());
		visitJobDateAssignUserDto.setStartTime(visitSchedulerDate.getStartTime());
		visitJobDateAssignUserDto.setStatus(visitSchedulerDate.getStatus());
		visitJobDateAssignUserDto.setTeamMembers(visitSchedulerDate.getTeamMembers());
		visitJobDateAssignUserDto.setTimeZone(visitSchedulerDate.getTimeZone());
		visitJobDateAssignUserDto.setVisitInstruction(visitSchedulerDate.getVisitInstruction());
		visitJobDateAssignUserDto.setVisitTitle(visitSchedulerDate.getVisitTitle());

		List<String> driverListData = new ArrayList<String>();
		for (RelatedUser relatedUser : driverUserList) {
			String userName = relatedUser.getFirstName() + " " + relatedUser.getLastName();
			driverListData.add(userName);
		}

		List<String> teamListData = new ArrayList<String>();
		for (RelatedUser relatedUser : teamUserList) {
			String userName = relatedUser.getFirstName() + " " + relatedUser.getLastName();
			teamListData.add(userName);
		}

		driversName = String.join(",", driverListData);

		teamsName = String.join(",", teamListData);
		visitJobDateAssignUserDto.setDriverMemberUsers(driverUserList);
		visitJobDateAssignUserDto.setTeamMemberUsers(teamUserList);
		visitJobDateAssignUserDto.setDriverMemberName(driversName);
		visitJobDateAssignUserDto.setTeamMemberName(teamsName);

		return visitJobDateAssignUserDto;
	}

}
