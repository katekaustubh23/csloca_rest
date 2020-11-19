package com.checksammy.loca.dto;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.Column;

import com.checksammy.loca.model.BinType;
import com.checksammy.loca.model.JobForScheduler;
import com.checksammy.loca.model.Location;
import com.checksammy.loca.model.VisitSchedulerDate;

public class JobForSchedulerDto {

	private Long id;

	private Long userId;

	private Location locationId;

	private Long quoteId;

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
	
	private Boolean schLater;
	
	private Boolean is_active;

	/* FOR invoice custom field */
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
	
	private Integer schVisitCount;
	
	private Integer invoVisitCount;
	
	private String assignUserReminderType;
	
	private Boolean teamUserReminder;
	
	private String teamSendNotify;
	
	private List<VisitSchedulerDate> visitSchedulerDates;
	
	private Long serviceAsBintypeId;
	
	private String serviceAsBintype;
	
	/* Job Internal Notes */
	private Set<JobInternalNotesDto> jobInternalNotes = new HashSet<>();

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

	public Location getLocationId() {
		return locationId;
	}

	public void setLocationId(Location locationId) {
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

	public List<VisitSchedulerDate> getVisitSchedulerDates() {
		return visitSchedulerDates;
	}

	public void setVisitSchedulerDates(List<VisitSchedulerDate> visitSchedulerDates) {
		this.visitSchedulerDates = visitSchedulerDates;
	}

	public Long getServiceAsBintypeId() {
		return serviceAsBintypeId;
	}

	public void setServiceAsBintypeId(Long serviceAsBintypeId) {
		this.serviceAsBintypeId = serviceAsBintypeId;
	}

	public String getServiceAsBintype() {
		return serviceAsBintype;
	}

	public void setServiceAsBintype(String serviceAsBintype) {
		this.serviceAsBintype = serviceAsBintype;
	}

	public Set<JobInternalNotesDto> getJobInternalNotes() {
		return jobInternalNotes;
	}

	public void setJobInternalNotes(Set<JobInternalNotesDto> jobInternalNotes) {
		this.jobInternalNotes = jobInternalNotes;
	}

	public JobForSchedulerDto setSchedulerIntoData(JobForScheduler jobForScheduler,
			List<VisitSchedulerDate> visitSchedulerDates2, Optional<BinType> binType, List<JobInternalNotesDto> jobInternalNotesDtos) {
		JobForSchedulerDto jobForSchedulerDto = new JobForSchedulerDto();
		jobForSchedulerDto.setAssignMemberIds(jobForScheduler.getAssignMemberIds());
		jobForSchedulerDto.setCreatedBy(jobForScheduler.getCreatedBy());
		jobForSchedulerDto.setCreatedTs(jobForScheduler.getCreatedTs());
		jobForSchedulerDto.setFinalTotal(jobForScheduler.getFinalTotal());
		jobForSchedulerDto.setId(jobForScheduler.getId());
		jobForSchedulerDto.setInvoiceRemainder(jobForScheduler.getInvoiceRemainder());
		jobForSchedulerDto.setIsDeleted(jobForScheduler.getIsDeleted());
		jobForSchedulerDto.setJobInstruction(jobForScheduler.getJobInstruction());
		jobForSchedulerDto.setJobNumber(jobForScheduler.getJobNumber());
		jobForSchedulerDto.setJobScheduleType(jobForScheduler.getJobScheduleType());
		jobForSchedulerDto.setJobStatus(jobForScheduler.getJobStatus());
		jobForSchedulerDto.setJobTitle(jobForScheduler.getJobTitle());

		jobForSchedulerDto.setLocationId(jobForScheduler.getLocationId());
		jobForSchedulerDto.setNotesLink(jobForScheduler.getNotesLink());
		jobForSchedulerDto.setQuoteId(jobForScheduler.getQuoteId());
		jobForSchedulerDto.setSchCustomDateDay(jobForScheduler.getSchCustomDateDay());
		jobForSchedulerDto.setSchCustomDayType(jobForScheduler.getSchCustomDayType());
		jobForSchedulerDto.setSchCustomFreqCount(jobForScheduler.getSchCustomFreqCount());
		jobForSchedulerDto.setSchCustomFrequency(jobForScheduler.getSchCustomFrequency());
		jobForSchedulerDto.setSchCustomWeeks(jobForScheduler.getSchCustomWeeks());
		jobForSchedulerDto.setSchDuration(jobForScheduler.getSchDuration());
		jobForSchedulerDto.setSchDurationType(jobForScheduler.getSchDurationType());
		jobForSchedulerDto.setSchEndDate(jobForScheduler.getSchEndDate());

		jobForSchedulerDto.setSchEndTime(jobForScheduler.getSchEndTime());
		jobForSchedulerDto.setSchStartDate(jobForScheduler.getSchStartDate());
		jobForSchedulerDto.setSchStartTime(jobForScheduler.getSchStartTime());
		jobForSchedulerDto.setSchVisitFrequencyId(jobForScheduler.getSchVisitFrequencyId());
		jobForSchedulerDto.setUpdatedBy(jobForScheduler.getUpdatedBy());
		jobForSchedulerDto.setUpdatedTs(jobForScheduler.getUpdatedTs());
		jobForSchedulerDto.setUserId(jobForScheduler.getUserId());

		jobForSchedulerDto.setInvoCount(jobForScheduler.getInvoCount());
		jobForSchedulerDto.setInvoCustomDateDays(jobForScheduler.getInvoCustomDateDays());
		jobForSchedulerDto.setInvoCustomDayType(jobForScheduler.getInvoCustomDayType());

		jobForSchedulerDto.setInvoCustomFreq(jobForScheduler.getInvoCustomFreq());
		jobForSchedulerDto.setInvoCustomFreqNumber(jobForScheduler.getInvoCustomFreqNumber());
		jobForSchedulerDto.setInvoEndDate(jobForScheduler.getInvoEndDate());
		jobForSchedulerDto.setInvoStartDate(jobForScheduler.getInvoStartDate());
		jobForSchedulerDto.setInvoWant(jobForScheduler.getInvoWant());
		jobForSchedulerDto.setInvoWhen(jobForScheduler.getInvoWhen());
		jobForSchedulerDto.setTeamAssignMemberIds(jobForScheduler.getTeamAssignMemberIds());


		jobForSchedulerDto.setDriverEmail(jobForScheduler.getDriverEmail());
		jobForSchedulerDto.setTeamEmail(jobForScheduler.getTeamEmail());
		jobForSchedulerDto.setSchLater(jobForScheduler.getSchLater());
		jobForSchedulerDto.setIs_active(jobForScheduler.getIs_active());

		jobForSchedulerDto.setSchVisitCount(jobForScheduler.getSchVisitCount() != null ? jobForScheduler.getSchVisitCount() : 0);
		jobForSchedulerDto.setInvoVisitCount(jobForScheduler.getInvoVisitCount() != null ? jobForScheduler.getInvoVisitCount() : 0);
		jobForSchedulerDto.setAssignUserReminderType(jobForScheduler.getAssignUserReminderType());
		jobForSchedulerDto.setTeamUserReminder(jobForScheduler.getTeamUserReminder());
		jobForSchedulerDto.setTeamSendNotify(jobForScheduler.getTeamSendNotify());
		jobForSchedulerDto.setVisitSchedulerDates(visitSchedulerDates2);
		jobForSchedulerDto.setServiceAsBintypeId(jobForScheduler.getServiceAsBintypeId());
		jobForSchedulerDto.setServiceAsBintype(binType.isPresent()?binType.get().getName(): null);
		
		jobForSchedulerDto.setJobInternalNotes(new HashSet<JobInternalNotesDto>(jobInternalNotesDtos));
		return jobForSchedulerDto;
	}
	
}
