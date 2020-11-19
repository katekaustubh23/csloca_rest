package com.checksammy.loca.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "invoice_scheduler_dates")
public class InvoiceSchedularDate implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;

	@Column(name = "job_id")
	private Long jobId;

	@Column(name = "inv_sch_start_date")
	private Instant invStartDate;

	@Column(name = "inv_sch_end_date")
	private Instant invEndDate;

	@Column(name = "inv_sch_start_time")
	private Instant invStartTime;

	@Column(name = "inv_sch_end_time")
	private Instant invEndTime;

	@Column(name = "inv_sch_status")
	private String invStatus;

	@Column(name = "checked")
	private Boolean checked;
	
	@Column(name = "timezone")
	private Long timeZone;
	
	@Column(name = "driver_reminder")
	private String driverReminder;
	
	@Column(name = "job_type")
	private String jobType;
	
	@Column(name = "checked_date")
	private Instant checkedDate;
	
	@Column(name = "inv_sch_later")
	private Boolean invSchLater;
	
	@Column(name = "checked_by")
	private Long checkedBy;
	
	@Column(name = "checked_by_name")
	private String checkedByName;
	
	@Column(name = "inv_date")
	private Date invoiceDate;
	
	@Column(name = "periodic_flag")
	private String periodicFlag;
	
	@Column(name = "team_member")
	private String teamMember;
	
	@Column(name = "driver_member")
	private String driverMember;
	
	@Column(name = "team_notify")
	private Boolean teamNotify;
	
	@Column(name = "driver_notify")
	private Boolean driverNotify;

	@Column(name = "details")
	private String details;
	
	@Column(name = "any_time")
	private Boolean anyTime;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "team_notify_min")
	private Integer teamNotifyMin;
	
	@Column(name = "driver_notify_min")
	private Integer driverNotifyMin;

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
	
}
