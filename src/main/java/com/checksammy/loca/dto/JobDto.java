package com.checksammy.loca.dto;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;

import com.checksammy.loca.model.Job;
import com.checksammy.loca.model.JobCustomInstance;
import com.checksammy.loca.model.JobInternalNotes;
import com.checksammy.loca.model.JobProductService;

public class JobDto {

	private Long id;

	private Long userId;

	private Long locationId;

	private Long quoteId;
	
	private Long requestId;

	private String jobNumber;

	private String jobTitle;

	private String jobInstruction;

	private String jobScheduleType;

	private Boolean invoiceRemainder;

	private Double finalTotal;

	private String notesLink;

	private Boolean isDeleted;

	private Long createdBy;

	private Instant createdTs;

	private Long updatedBy;

	private Instant updatedTs;

	private String assignMemberIds;

	/* Schedule Field */

	private Instant schStartDate;

	private Instant schEndDate;

	private Instant schStartTime;

	private Instant schEndTime;

	private Long schVisitFrequencyId;

	private String schDuration;

	private String schDurationType;

	private String schCustomFrequency;

	private Long schCustomFreqCount;

	private String schCustomDayType;

	private String schCustomWeeks;

	private String schCustomDateDay;

	private String jobStatus;

	/* Custom field */
	private Set<JobCustomInstance> jobCustomInstance = new HashSet<>();

	/* Job product and service */
	private Set<JobProductService> jobProductService = new HashSet<>();

	/* Job Internal Notes */
	private Set<JobInternalNotes> jobInternalNotes = new HashSet<>();

	/* Invoice data */
	private String invoWant;

	private String invoWhen;

	private Instant invoStartDate;

	private Instant invoEndDate;

	private Boolean autocreatePayInvoice;

	private Long invoCount;

	private String invoCustomFreq;

	private Long invoCustomFreqNumber;

	private String invoCustomDayType;

	private String invoCustomDateDays;

	private String teamAssignMemberIds;

	private Boolean driverEmail;

	private Boolean teamEmail;

	private Boolean schLater;

	private Boolean is_active;

	private Integer schVisitCount;

	private Integer invoVisitCount;

	private Boolean anyTime;

	private String assignUserReminderType;

	private Boolean teamUserReminder;

	private String teamSendNotify;
	
	private Double rating;
	
	private String driverEmailNotify;
	
	private Boolean driverUserReminder;
	
	private Long serviceAsBintypeId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public Long getQuoteId() {
		return quoteId;
	}

	public void setQuoteId(Long quoteId) {
		this.quoteId = quoteId;
	}

	public String getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getJobInstruction() {
		return jobInstruction;
	}

	public void setJobInstruction(String jobInstruction) {
		this.jobInstruction = jobInstruction;
	}

	public String getJobScheduleType() {
		return jobScheduleType;
	}

	public void setJobScheduleType(String jobScheduleType) {
		this.jobScheduleType = jobScheduleType;
	}

	public Boolean getInvoiceRemainder() {
		return invoiceRemainder;
	}

	public void setInvoiceRemainder(Boolean invoiceRemainder) {
		this.invoiceRemainder = invoiceRemainder;
	}

	public Double getFinalTotal() {
		return finalTotal;
	}

	public void setFinalTotal(Double finalTotal) {
		this.finalTotal = finalTotal;
	}

	public String getNotesLink() {
		return notesLink;
	}

	public void setNotesLink(String notesLink) {
		this.notesLink = notesLink;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Instant getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(Instant createdTs) {
		this.createdTs = createdTs;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Instant getUpdatedTs() {
		return updatedTs;
	}

	public void setUpdatedTs(Instant updatedTs) {
		this.updatedTs = updatedTs;
	}

	public String getAssignMemberIds() {
		return assignMemberIds;
	}

	public void setAssignMemberIds(String assignMemberIds) {
		this.assignMemberIds = assignMemberIds;
	}

	public Instant getSchStartDate() {
		return schStartDate;
	}

	public void setSchStartDate(Instant schStartDate) {
		this.schStartDate = schStartDate;
	}

	public Instant getSchEndDate() {
		return schEndDate;
	}

	public void setSchEndDate(Instant schEndDate) {
		this.schEndDate = schEndDate;
	}

	public Instant getSchStartTime() {
		return schStartTime;
	}

	public void setSchStartTime(Instant schStartTime) {
		this.schStartTime = schStartTime;
	}

	public Instant getSchEndTime() {
		return schEndTime;
	}

	public void setSchEndTime(Instant schEndTime) {
		this.schEndTime = schEndTime;
	}

	public Long getSchVisitFrequencyId() {
		return schVisitFrequencyId;
	}

	public void setSchVisitFrequencyId(Long schVisitFrequencyId) {
		this.schVisitFrequencyId = schVisitFrequencyId;
	}

	public String getSchDuration() {
		return schDuration;
	}

	public void setSchDuration(String schDuration) {
		this.schDuration = schDuration;
	}

	public String getSchDurationType() {
		return schDurationType;
	}

	public void setSchDurationType(String schDurationType) {
		this.schDurationType = schDurationType;
	}

	public String getSchCustomFrequency() {
		return schCustomFrequency;
	}

	public void setSchCustomFrequency(String schCustomFrequency) {
		this.schCustomFrequency = schCustomFrequency;
	}

	public Long getSchCustomFreqCount() {
		return schCustomFreqCount;
	}

	public void setSchCustomFreqCount(Long schCustomFreqCount) {
		this.schCustomFreqCount = schCustomFreqCount;
	}

	public String getSchCustomDayType() {
		return schCustomDayType;
	}

	public void setSchCustomDayType(String schCustomDayType) {
		this.schCustomDayType = schCustomDayType;
	}

	public String getSchCustomWeeks() {
		return schCustomWeeks;
	}

	public void setSchCustomWeeks(String schCustomWeeks) {
		this.schCustomWeeks = schCustomWeeks;
	}

	public String getSchCustomDateDay() {
		return schCustomDateDay;
	}

	public void setSchCustomDateDay(String schCustomDateDay) {
		this.schCustomDateDay = schCustomDateDay;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	public Set<JobCustomInstance> getJobCustomInstance() {
		return jobCustomInstance;
	}

	public void setJobCustomInstance(Set<JobCustomInstance> jobCustomInstance) {
		this.jobCustomInstance = jobCustomInstance;
	}

	public Set<JobProductService> getJobProductService() {
		return jobProductService;
	}

	public void setJobProductService(Set<JobProductService> jobProductService) {
		this.jobProductService = jobProductService;
	}

	public Set<JobInternalNotes> getJobInternalNotes() {
		return jobInternalNotes;
	}

	public void setJobInternalNotes(Set<JobInternalNotes> jobInternalNotes) {
		this.jobInternalNotes = jobInternalNotes;
	}

	public String getInvoWant() {
		return invoWant;
	}

	public void setInvoWant(String invoWant) {
		this.invoWant = invoWant;
	}

	public String getInvoWhen() {
		return invoWhen;
	}

	public void setInvoWhen(String invoWhen) {
		this.invoWhen = invoWhen;
	}

	public Instant getInvoStartDate() {
		return invoStartDate;
	}

	public void setInvoStartDate(Instant invoStartDate) {
		this.invoStartDate = invoStartDate;
	}

	public Instant getInvoEndDate() {
		return invoEndDate;
	}

	public void setInvoEndDate(Instant invoEndDate) {
		this.invoEndDate = invoEndDate;
	}

	public Boolean getAutocreatePayInvoice() {
		return autocreatePayInvoice;
	}

	public void setAutocreatePayInvoice(Boolean autocreatePayInvoice) {
		this.autocreatePayInvoice = autocreatePayInvoice;
	}

	public Long getInvoCount() {
		return invoCount;
	}

	public void setInvoCount(Long invoCount) {
		this.invoCount = invoCount;
	}

	public String getInvoCustomFreq() {
		return invoCustomFreq;
	}

	public void setInvoCustomFreq(String invoCustomFreq) {
		this.invoCustomFreq = invoCustomFreq;
	}

	public Long getInvoCustomFreqNumber() {
		return invoCustomFreqNumber;
	}

	public void setInvoCustomFreqNumber(Long invoCustomFreqNumber) {
		this.invoCustomFreqNumber = invoCustomFreqNumber;
	}

	public String getInvoCustomDayType() {
		return invoCustomDayType;
	}

	public void setInvoCustomDayType(String invoCustomDayType) {
		this.invoCustomDayType = invoCustomDayType;
	}

	public String getInvoCustomDateDays() {
		return invoCustomDateDays;
	}

	public void setInvoCustomDateDays(String invoCustomDateDays) {
		this.invoCustomDateDays = invoCustomDateDays;
	}

	public String getTeamAssignMemberIds() {
		return teamAssignMemberIds;
	}

	public void setTeamAssignMemberIds(String teamAssignMemberIds) {
		this.teamAssignMemberIds = teamAssignMemberIds;
	}

	public Boolean getDriverEmail() {
		return driverEmail;
	}

	public void setDriverEmail(Boolean driverEmail) {
		this.driverEmail = driverEmail;
	}

	public Boolean getTeamEmail() {
		return teamEmail;
	}

	public void setTeamEmail(Boolean teamEmail) {
		this.teamEmail = teamEmail;
	}

	public Boolean getSchLater() {
		return schLater;
	}

	public void setSchLater(Boolean schLater) {
		this.schLater = schLater;
	}

	public Boolean getIs_active() {
		return is_active;
	}

	public void setIs_active(Boolean is_active) {
		this.is_active = is_active;
	}

	public Integer getSchVisitCount() {
		return schVisitCount;
	}

	public void setSchVisitCount(Integer schVisitCount) {
		this.schVisitCount = schVisitCount;
	}

	public Integer getInvoVisitCount() {
		return invoVisitCount;
	}

	public void setInvoVisitCount(Integer invoVisitCount) {
		this.invoVisitCount = invoVisitCount;
	}

	public Boolean getAnyTime() {
		return anyTime;
	}

	public void setAnyTime(Boolean anyTime) {
		this.anyTime = anyTime;
	}

	public String getAssignUserReminderType() {
		return assignUserReminderType;
	}

	public void setAssignUserReminderType(String assignUserReminderType) {
		this.assignUserReminderType = assignUserReminderType;
	}

	public Boolean getTeamUserReminder() {
		return teamUserReminder;
	}

	public void setTeamUserReminder(Boolean teamUserReminder) {
		this.teamUserReminder = teamUserReminder;
	}

	public String getTeamSendNotify() {
		return teamSendNotify;
	}

	public void setTeamSendNotify(String teamSendNotify) {
		this.teamSendNotify = teamSendNotify;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public String getDriverEmailNotify() {
		return driverEmailNotify;
	}

	public void setDriverEmailNotify(String driverEmailNotify) {
		this.driverEmailNotify = driverEmailNotify;
	}

	public Boolean getDriverUserReminder() {
		return driverUserReminder;
	}

	public void setDriverUserReminder(Boolean driverUserReminder) {
		this.driverUserReminder = driverUserReminder;
	}

	public Long getServiceAsBintypeId() {
		return serviceAsBintypeId;
	}

	public void setServiceAsBintypeId(Long serviceAsBintypeId) {
		this.serviceAsBintypeId = serviceAsBintypeId;
	}

	public Long getRequestId() {
		return requestId;
	}

	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}

	public Job setJobData(JobDto saveJob) {
		Job job = new Job();
		job.setAssignMemberIds(saveJob.getAssignMemberIds());
		job.setCreatedBy(saveJob.getCreatedBy());
		job.setCreatedTs(saveJob.getCreatedTs());
		job.setFinalTotal(saveJob.getFinalTotal());

		job.setInvoiceRemainder(saveJob.getInvoiceRemainder());
		job.setIsDeleted(saveJob.getIsDeleted());
		job.setJobInstruction(saveJob.getJobInstruction());
		job.setJobNumber(saveJob.getJobNumber());
		job.setJobProductService(saveJob.getJobProductService());
		job.setJobScheduleType(saveJob.getJobScheduleType());
		job.setJobStatus(saveJob.getJobStatus());
		job.setJobTitle(saveJob.getJobTitle());

//		job.setLocationId(saveJob.getLocationId());
		job.setNotesLink(saveJob.getNotesLink());
		job.setQuoteId(saveJob.getQuoteId());
		job.setRequestId(saveJob.getRequestId());
		job.setSchCustomDateDay(saveJob.getSchCustomDateDay());
		job.setSchCustomDayType(saveJob.getSchCustomDayType());
		job.setSchCustomFreqCount(saveJob.getSchCustomFreqCount());
		job.setSchCustomFrequency(saveJob.getSchCustomFrequency());
		job.setSchCustomWeeks(saveJob.getSchCustomWeeks());
		job.setSchDuration(saveJob.getSchDuration());
		job.setSchDurationType(saveJob.getSchDurationType());
		job.setSchEndDate(saveJob.getSchEndDate());

		job.setSchEndTime(saveJob.getSchEndTime());
		job.setSchStartDate(saveJob.getSchStartDate());
		job.setSchStartTime(saveJob.getSchStartTime());
		job.setSchVisitFrequencyId(saveJob.getSchVisitFrequencyId());

		job.setInvoCount(saveJob.getInvoCount());
		job.setInvoCustomDateDays(saveJob.getInvoCustomDateDays());
		job.setInvoCustomDayType(saveJob.getInvoCustomDayType());

		job.setInvoCustomFreq(saveJob.getInvoCustomFreq());
		job.setInvoCustomFreqNumber(saveJob.getInvoCustomFreqNumber());
		job.setInvoEndDate(saveJob.getInvoEndDate());
		job.setInvoStartDate(saveJob.getInvoStartDate());
		job.setInvoWant(saveJob.getInvoWant());
		job.setInvoWhen(saveJob.getInvoWhen());

		job.setTeamAssignMemberIds(saveJob.getTeamAssignMemberIds());

		job.setDriverEmail(saveJob.getDriverEmail());
		job.setTeamEmail(saveJob.getTeamEmail());

		job.setSchLater(saveJob.getSchLater());
		job.setIs_active(saveJob.getIs_active());

		job.setUserId(saveJob.getUserId());
		if (saveJob.getId() != null && saveJob.getId() > 0) {
			job.setId(saveJob.getId());
			job.setUpdatedBy(saveJob.getUpdatedBy());
			job.setUpdatedTs(Instant.now());
		}
		job.setSchVisitCount(saveJob.getSchVisitCount() != null ? saveJob.getSchVisitCount() : 0);
		job.setInvoVisitCount(saveJob.getInvoVisitCount() != null ? saveJob.getInvoVisitCount() : 0);
		
		job.setAssignUserReminderType(saveJob.getAssignUserReminderType());
		job.setTeamUserReminder(saveJob.getTeamUserReminder());
		job.setTeamSendNotify(saveJob.getTeamSendNotify());
		job.setRating(saveJob.getRating() != null?saveJob.getRating():0);
		
		job.setDriverEmailNotify(saveJob.getDriverEmailNotify());
		job.setDriverUserReminder(saveJob.getDriverUserReminder());
		job.setServiceAsBintypeId(saveJob.getServiceAsBintypeId());
		return job;
	}

}
