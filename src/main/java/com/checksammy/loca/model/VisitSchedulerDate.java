package com.checksammy.loca.model;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "visit_scheduler_dates")
public class VisitSchedulerDate implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;

	@Column(name = "job_id")
	private Long jobId;

	@Column(name = "sch_start_date")
	private Instant startDate;

	@Column(name = "sch_end_date")
	private Instant endDate;

	@Column(name = "sch_current_date")
	private Instant currentDate;

	@Column(name = "sch_start_time")
	private Instant startTime;

	@Column(name = "sch_end_time")
	private Instant endTime;

	@Column(name = "sch_status")
	private String status;

	@Column(name = "checked")
	private Boolean checked;
	
	@Column(name = "notify")
	private Boolean notify;
	
	@Column(name = "time_zone")
	private Long timeZone;
	
	@Column(name = "driver_reminder")
	private String driverReminder;
	
	@Column(name = "job_type")
	private String jobType;
	
	@Column(name = "driver_notify")
	private Boolean driverNotify;
	
	@Column(name = "checked_date")
	private Instant checkedDate;
	
	@Column(name = "sch_later")
	private Boolean schLater;
	
	@Column(name = "checked_by")
	private Long checkedBy;
	
	@Column(name = "check_by_name")
	private String checkedByName;
	
	@Column(name = "any_time")
	private Boolean anyTime;
	
	@Column(name = "team_members")
	private String teamMembers;
	
	@Column(name = "driver_members")
	private String driverMembers;
	
	@Column(name = "visit_title")
	private String visitTitle;
	
	@Column(name = "visit_instruction")
	private String visitInstruction;
	
	@Column(name = "assign_product_service")
	private String assignProductService;
	
	@Column(name = "paid_on_flag")
	private Boolean paidOnFlag;

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
	
}
