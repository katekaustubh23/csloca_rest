package com.checksammy.loca.dto;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import com.checksammy.loca.model.InvoiceSchedularDate;
import com.checksammy.loca.model.User;

public class InvoiceSchedularDateDto {


	private Long id;

	private Long jobId;

	private Instant invStartDate;

	private Instant invEndDate;

	private Instant invStartTime;

	private Instant invEndTime;

	private String invStatus;

	private Boolean checked;
	
	private Long timeZone;
	
	private String driverReminder;
	
	private String jobType;
	
	private Instant checkedDate;
	
	private Boolean invSchLater;
	
	private Long checkedBy;
	
	private String checkedByName;
	
	private Date invoiceDate;
	
	private String periodicFlag;
	
	private String teamMember;
	
	private String driverMember;
	
	private Boolean teamNotify;
	
	private Boolean driverNotify;

	private String details;
	
	private Boolean anyTime;
	
	private String title;
	
	private Integer teamNotifyMin;
	
	private Integer driverNotifyMin;
	
	private List<User> driverList;
	
	private List<User> teamList;

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

	public Instant getInvStartDate() {
		return invStartDate;
	}

	public void setInvStartDate(Instant invStartDate) {
		this.invStartDate = invStartDate;
	}

	public Instant getInvEndDate() {
		return invEndDate;
	}

	public void setInvEndDate(Instant invEndDate) {
		this.invEndDate = invEndDate;
	}

	public Instant getInvStartTime() {
		return invStartTime;
	}

	public void setInvStartTime(Instant invStartTime) {
		this.invStartTime = invStartTime;
	}

	public Instant getInvEndTime() {
		return invEndTime;
	}

	public void setInvEndTime(Instant invEndTime) {
		this.invEndTime = invEndTime;
	}

	public String getInvStatus() {
		return invStatus;
	}

	public void setInvStatus(String invStatus) {
		this.invStatus = invStatus;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
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

	public Instant getCheckedDate() {
		return checkedDate;
	}

	public void setCheckedDate(Instant checkedDate) {
		this.checkedDate = checkedDate;
	}

	public Boolean getInvSchLater() {
		return invSchLater;
	}

	public void setInvSchLater(Boolean invSchLater) {
		this.invSchLater = invSchLater;
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

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getPeriodicFlag() {
		return periodicFlag;
	}

	public void setPeriodicFlag(String periodicFlag) {
		this.periodicFlag = periodicFlag;
	}

	public String getTeamMember() {
		return teamMember;
	}

	public void setTeamMember(String teamMember) {
		this.teamMember = teamMember;
	}

	public String getDriverMember() {
		return driverMember;
	}

	public void setDriverMember(String driverMember) {
		this.driverMember = driverMember;
	}

	public Boolean getTeamNotify() {
		return teamNotify;
	}

	public void setTeamNotify(Boolean teamNotify) {
		this.teamNotify = teamNotify;
	}

	public Boolean getDriverNotify() {
		return driverNotify;
	}

	public void setDriverNotify(Boolean driverNotify) {
		this.driverNotify = driverNotify;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Boolean getAnyTime() {
		return anyTime;
	}

	public void setAnyTime(Boolean anyTime) {
		this.anyTime = anyTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getTeamNotifyMin() {
		return teamNotifyMin;
	}

	public void setTeamNotifyMin(Integer teamNotifyMin) {
		this.teamNotifyMin = teamNotifyMin;
	}

	public Integer getDriverNotifyMin() {
		return driverNotifyMin;
	}

	public void setDriverNotifyMin(Integer driverNotifyMin) {
		this.driverNotifyMin = driverNotifyMin;
	}

	public List<User> getDriverList() {
		return driverList;
	}

	public void setDriverList(List<User> driverList) {
		this.driverList = driverList;
	}

	public List<User> getTeamList() {
		return teamList;
	}

	public void setTeamList(List<User> teamList) {
		this.teamList = teamList;
	}

	public InvoiceSchedularDateDto mapData(InvoiceSchedularDate invoiceSchedularDate, List<User> driverList2,
			List<User> teamList2) {
		InvoiceSchedularDateDto invoiceSchedularDateDto = new InvoiceSchedularDateDto();
		invoiceSchedularDateDto.setAnyTime(invoiceSchedularDate.getAnyTime());
		invoiceSchedularDateDto.setChecked(invoiceSchedularDate.getChecked());
		invoiceSchedularDateDto.setCheckedBy(invoiceSchedularDate.getCheckedBy());
		invoiceSchedularDateDto.setCheckedDate(invoiceSchedularDate.getCheckedDate());
		invoiceSchedularDateDto.setDetails(invoiceSchedularDate.getDetails());
		invoiceSchedularDateDto.setDriverList(driverList2);
		invoiceSchedularDateDto.setDriverMember(invoiceSchedularDate.getDriverMember());
		invoiceSchedularDateDto.setDriverNotify(invoiceSchedularDate.getDriverNotify());
		invoiceSchedularDateDto.setDriverNotifyMin(invoiceSchedularDate.getDriverNotifyMin());
		invoiceSchedularDateDto.setDriverReminder(invoiceSchedularDate.getDriverReminder());
		invoiceSchedularDateDto.setId(invoiceSchedularDate.getId());
		invoiceSchedularDateDto.setInvEndDate(invoiceSchedularDate.getInvEndDate());
		invoiceSchedularDateDto.setInvEndTime(invoiceSchedularDate.getInvEndTime());
		invoiceSchedularDateDto.setInvoiceDate(invoiceSchedularDate.getInvoiceDate());
		invoiceSchedularDateDto.setInvSchLater(invoiceSchedularDate.getInvSchLater());
		invoiceSchedularDateDto.setInvStartDate(invoiceSchedularDate.getInvStartDate());
		invoiceSchedularDateDto.setInvStartTime(invoiceSchedularDate.getInvStartTime());
		invoiceSchedularDateDto.setInvStatus(invoiceSchedularDate.getInvStatus());
		invoiceSchedularDateDto.setJobId(invoiceSchedularDate.getJobId());
		invoiceSchedularDateDto.setJobType(invoiceSchedularDate.getJobType());
		invoiceSchedularDateDto.setPeriodicFlag(invoiceSchedularDate.getPeriodicFlag());
		invoiceSchedularDateDto.setTeamList(teamList2);
		invoiceSchedularDateDto.setTeamMember(invoiceSchedularDate.getTeamMember());
		invoiceSchedularDateDto.setTeamNotify(invoiceSchedularDate.getTeamNotify());
		invoiceSchedularDateDto.setTeamNotifyMin(invoiceSchedularDate.getTeamNotifyMin());
		invoiceSchedularDateDto.setTimeZone(invoiceSchedularDate.getTimeZone());
		invoiceSchedularDateDto.setTitle(invoiceSchedularDate.getTitle());
		return invoiceSchedularDateDto;
	}
}
