package com.checksammy.loca.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

@Entity
@Table(name = "job")
@Where(clause = "is_deleted=0")
public class Job implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;

	@Column(name = "user_id")
	private Long userId;

	@JoinColumn(name = "location_id", referencedColumnName = "id")
	@ManyToOne()
	private Location locationId;

	@Column(name = "quote_id")
	private Long quoteId;
	
	@Column(name = "request_id")
	private Long requestId;

	@Column(name = "job_number")
	private String jobNumber;

	@Column(name = "job_title")
	private String jobTitle;

	@Column(name = "job_instruction")
	private String jobInstruction;

	@Column(name = "job_schedule_type")
	private String jobScheduleType;

	@Column(name = "invoice_remainder")
	private Boolean invoiceRemainder;

	@Column(name = "final_total")
	private Double finalTotal;

	@Column(name = "notes_link")
	private String notesLink;

	@Column(name = "is_deleted")
	private Boolean isDeleted;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "created_ts")
	private Instant createdTs;

	@Column(name = "updated_by")
	private Long updatedBy;

	@Column(name = "updated_ts")
	private Instant updatedTs;

	@Column(name = "assign_member_ids")
	private String assignMemberIds;

	/* Schedule Field */

	@Column(name = "sch_start_date")
	private Instant schStartDate;

	@Column(name = "sch_end_date")
	private Instant schEndDate;

	@Column(name = "sch_start_time")
	private Instant schStartTime;

	@Column(name = "sch_end_time")
	private Instant schEndTime;

	@Column(name = "sch_visit_frequency_id")
	private Long schVisitFrequencyId;

	@Column(name = "sch_duration")
	private String schDuration;

	@Column(name = "sch_duration_type")
	private String schDurationType;

	@Column(name = "sch_custom_frequency")
	private String schCustomFrequency;

	@Column(name = "sch_custom_freq_count")
	private Long schCustomFreqCount;

	@Column(name = "sch_custom_day_type")
	private String schCustomDayType;

	@Column(name = "sch_custom_weeks")
	private String schCustomWeeks;

	@Column(name = "sch_custom_date_day")
	private String schCustomDateDay;

	@Column(name = "job_status")
	private String jobStatus;
	
	@Column(name = "sch_later")
	private Boolean schLater;
	
	@Column(name = "is_active")
	private Boolean is_active;

	/* Custom field */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "jobId")
	@Where(clause = "is_deleted=0")
	@OrderBy("id ASC")
	private Set<JobCustomInstance> jobCustomInstance = new HashSet<>();

	/* Job product and service */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "jobId")
	@Where(clause = "is_deleted=0")
	@OrderBy("id ASC")
	private Set<JobProductService> jobProductService = new HashSet<>();

	/* Job Internal Notes */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "jobId")
//	@Where(clause = "is_deleted=0")
	@OrderBy("id ASC")
	private Set<JobInternalNotes> jobInternalNotes = new HashSet<>();

	/* FOR invoice custom field */
	@Column(name = "invo_want")
	private String invoWant;

	@Column(name = "invo_when")
	private String invoWhen;

	@Column(name = "invo_start_date")
	private Instant invoStartDate;

	@Column(name = "invo_end_date")
	private Instant invoEndDate;

	@Column(name = "autocreate_pay_invoice")
	private Boolean autocreatePayInvoice;

	@Column(name = "invo_count")
	private Long invoCount;

	@Column(name = "invo_custom_freq")
	private String invoCustomFreq;

	@Column(name = "invo_custom_freq_number")
	private Long invoCustomFreqNumber;

	@Column(name = "invo_custom_day_type")
	private String invoCustomDayType;

	@Column(name = "invo_custom_date_days")
	private String invoCustomDateDays;

	@Column(name = "team_assign_member_ids")
	private String teamAssignMemberIds;

	@Column(name = "driver_email")
	private Boolean driverEmail;

	@Column(name = "team_email")
	private Boolean teamEmail;
	
	@Column(name = "sch_visit_count")
	private Integer schVisitCount;
	
	@Column(name = "invo_visit_count")
	private Integer invoVisitCount;
	
	@Column(name = "assign_user_reminder_type")
	private String assignUserReminderType;
	
	@Column(name = "team_user_reminder")
	private Boolean teamUserReminder;
	
	@Column(name = "team_send_notify")
	private String teamSendNotify;
	
	@Column(name = "rating")
	private Double rating;
	
	@Column(name = "driver_email_notify")
	private String driverEmailNotify;
	
	@Column(name = "driver_user_reminder")
	private Boolean driverUserReminder;
	
	@Column(name = "service_as_bintype_id")
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

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getAssignMemberIds() {
		return assignMemberIds;
	}

	public void setAssignMemberIds(String assignMemberIds) {
		this.assignMemberIds = assignMemberIds;
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

}
