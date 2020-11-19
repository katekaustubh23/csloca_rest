package com.checksammy.loca.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.checksammy.loca.model.BinType;
import com.checksammy.loca.model.InvoiceSchedularDate;
import com.checksammy.loca.model.Job;
import com.checksammy.loca.model.JobExpenses;
import com.checksammy.loca.model.JobProductService;
import com.checksammy.loca.model.Location;
import com.checksammy.loca.model.User;

public class JobSendDto {

	private static final String LATE = "Late";

	private static final String UPCOMING = "Upcoming";

	private static final String NEXT = "New job offer";

	/* Job Driver mapping Status */
	public static final String ACCEPT = "accept";

	public static final String PENDING = "pending";

	private Long id;

	private Long userId;

	private User user;

	private Location locationId;

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

	private List<User> assignUsers;

	private List<User> assignTeam;

	/* Custom field */
	private List<JobCustomInstanceDto> jobCustomInstance;

	/* Job product and service */
	private Set<JobProductService> jobProductService = new HashSet<>();

	/* Job Internal Notes */
	private Set<JobInternalNotesDto> jobInternalNotes = new HashSet<>();

	/* For invoice save */
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

	private List<JobExpenses> expenses;

	private Boolean driverEmail;

	private Boolean teamEmail;

	private Boolean schLater;

	private Boolean is_active;

	private Integer schVisitCount;

	private Integer invoVisitCount;

	private String statePosition;

	private String assignUserReminderType;

	private Boolean teamUserReminder;

	private String teamSendNotify;

	private List<VisitSchedulerDateDto> visitSchedulerDates;

	private List<BillingInvoiceDto> billingInvoiceData;

	private List<InvoiceSchedularDate> billingInvoiceScheduleData;

	private Double rating;

	private String driverEmailNotify;

	private Boolean driverUserReminder;

	private Long serviceAsBintypeId;

	private String serviceAsBintype;

	private Long completeVisitCount;

	private List<JobProductService> productListByVisit;

	public List<JobProductService> getProductListByVisit() {
		return productListByVisit;
	}

	public void setProductListByVisit(List<JobProductService> productListByVisit) {
		this.productListByVisit = productListByVisit;
	}

	public List<InvoiceSchedularDate> getBillingInvoiceScheduleData() {
		return billingInvoiceScheduleData;
	}

	public void setBillingInvoiceScheduleData(List<InvoiceSchedularDate> billingInvoiceScheduleData) {
		this.billingInvoiceScheduleData = billingInvoiceScheduleData;
	}

	public String getStatePosition() {
		return statePosition;
	}

	public void setStatePosition(String statePosition) {
		this.statePosition = statePosition;
	}

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

	public List<JobCustomInstanceDto> getJobCustomInstance() {
		return jobCustomInstance;
	}

	public void setJobCustomInstance(List<JobCustomInstanceDto> jobCustomInstance) {
		this.jobCustomInstance = jobCustomInstance;
	}

	public Set<JobProductService> getJobProductService() {
		return jobProductService;
	}

	public void setJobProductService(Set<JobProductService> jobProductService) {
		this.jobProductService = jobProductService;
	}

	public Set<JobInternalNotesDto> getJobInternalNotes() {
		return jobInternalNotes;
	}

	public void setJobInternalNotes(Set<JobInternalNotesDto> jobInternalNotes) {
		this.jobInternalNotes = jobInternalNotes;
	}

	public List<User> getAssignUsers() {
		return assignUsers;
	}

	public void setAssignUsers(List<User> assignUsers) {
		this.assignUsers = assignUsers;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public List<User> getAssignTeam() {
		return assignTeam;
	}

	public void setAssignTeam(List<User> assignTeam) {
		this.assignTeam = assignTeam;
	}

	public List<JobExpenses> getExpenses() {
		return expenses;
	}

	public void setExpenses(List<JobExpenses> expenses) {
		this.expenses = expenses;
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

	public List<VisitSchedulerDateDto> getVisitSchedulerDates() {
		return visitSchedulerDates;
	}

	public void setVisitSchedulerDates(List<VisitSchedulerDateDto> visitSchedulerDates) {
		this.visitSchedulerDates = visitSchedulerDates;
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

	public String getServiceAsBintype() {
		return serviceAsBintype;
	}

	public void setServiceAsBintype(String serviceAsBintype) {
		this.serviceAsBintype = serviceAsBintype;
	}

	public List<BillingInvoiceDto> getBillingInvoiceData() {
		return billingInvoiceData;
	}

	public void setBillingInvoiceData(List<BillingInvoiceDto> billingInvoiceData) {
		this.billingInvoiceData = billingInvoiceData;
	}

	public Long getRequestId() {
		return requestId;
	}

	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}

	public Long getCompleteVisitCount() {
		return completeVisitCount;
	}

	public void setCompleteVisitCount(Long completeVisitCount) {
		this.completeVisitCount = completeVisitCount;
	}

	public JobSendDto getJobData(Job job, List<JobInternalNotesDto> jobInternalNotesDtos, User user,
			List<JobCustomInstanceDto> jobCustomInstanceDtos, List<User> assignUsers, List<User> asignTeam) {

		JobSendDto jobSendDto = new JobSendDto();
		jobSendDto.setAssignMemberIds(job.getAssignMemberIds());
		jobSendDto.setAssignUsers((assignUsers != null && assignUsers.size() > 0) ? assignUsers : null);
		jobSendDto.setCreatedBy(job.getCreatedBy());
		jobSendDto.setCreatedTs(job.getCreatedTs());
		jobSendDto.setFinalTotal(job.getFinalTotal());
		jobSendDto.setId(job.getId());
		jobSendDto.setInvoiceRemainder(job.getInvoiceRemainder());
		jobSendDto.setIsDeleted(job.getIsDeleted());
		jobSendDto.setJobCustomInstance(jobCustomInstanceDtos);
		jobSendDto.setJobInstruction(job.getJobInstruction());
		jobSendDto.setJobInternalNotes(new HashSet<>(jobInternalNotesDtos));
		jobSendDto.setJobNumber(job.getJobNumber());
		jobSendDto.setJobProductService(job.getJobProductService());
		jobSendDto.setJobScheduleType(job.getJobScheduleType());
		jobSendDto.setJobStatus(job.getJobStatus());
		jobSendDto.setJobTitle(job.getJobTitle());

		jobSendDto.setLocationId(job.getLocationId());
		jobSendDto.setNotesLink(job.getNotesLink());
		jobSendDto.setQuoteId(job.getQuoteId());
		jobSendDto.setRequestId(job.getRequestId());
		jobSendDto.setSchCustomDateDay(job.getSchCustomDateDay());
		jobSendDto.setSchCustomDayType(job.getSchCustomDayType());
		jobSendDto.setSchCustomFreqCount(job.getSchCustomFreqCount());
		jobSendDto.setSchCustomFrequency(job.getSchCustomFrequency());
		jobSendDto.setSchCustomWeeks(job.getSchCustomWeeks());
		jobSendDto.setSchDuration(job.getSchDuration());
		jobSendDto.setSchDurationType(job.getSchDurationType());
		jobSendDto.setSchEndDate(job.getSchEndDate());

		jobSendDto.setSchEndTime(job.getSchEndTime());
		jobSendDto.setSchStartDate(job.getSchStartDate());
		jobSendDto.setSchStartTime(job.getSchStartTime());
		jobSendDto.setSchVisitFrequencyId(job.getSchVisitFrequencyId());
		jobSendDto.setUpdatedBy(job.getUpdatedBy());
		jobSendDto.setUpdatedTs(job.getUpdatedTs());
		jobSendDto.setUserId(job.getUserId());
		jobSendDto.setUser(user);

		jobSendDto.setInvoCount(job.getInvoCount());
		jobSendDto.setInvoCustomDateDays(job.getInvoCustomDateDays());
		jobSendDto.setInvoCustomDayType(job.getInvoCustomDayType());

		jobSendDto.setInvoCustomFreq(job.getInvoCustomFreq());
		jobSendDto.setInvoCustomFreqNumber(job.getInvoCustomFreqNumber());
		jobSendDto.setInvoEndDate(job.getInvoEndDate());
		jobSendDto.setInvoStartDate(job.getInvoStartDate());
		jobSendDto.setInvoWant(job.getInvoWant());
		jobSendDto.setInvoWhen(job.getInvoWhen());
		jobSendDto.setTeamAssignMemberIds(job.getTeamAssignMemberIds());

		jobSendDto.setAssignTeam(asignTeam);

		jobSendDto.setDriverEmail(job.getDriverEmail());
		jobSendDto.setTeamEmail(job.getTeamEmail());
		jobSendDto.setSchLater(job.getSchLater());
		jobSendDto.setIs_active(job.getIs_active());

		jobSendDto.setSchVisitCount(job.getSchVisitCount() != null ? job.getSchVisitCount() : 0);
		jobSendDto.setInvoVisitCount(job.getInvoVisitCount() != null ? job.getInvoVisitCount() : 0);

		jobSendDto.setAssignUserReminderType(job.getAssignUserReminderType());
		jobSendDto.setTeamUserReminder(job.getTeamUserReminder());
		jobSendDto.setTeamSendNotify(job.getTeamSendNotify());
		jobSendDto.setRating(job.getRating() != null ? job.getRating() : 0);

		jobSendDto.setDriverEmailNotify(job.getDriverEmailNotify());
		jobSendDto.setDriverUserReminder(job.getDriverUserReminder());
		jobSendDto.setServiceAsBintypeId(job.getServiceAsBintypeId());

		Calendar currentDate = Calendar.getInstance();
		Calendar upComingNext = Calendar.getInstance();
		currentDate.getTime().setHours(0);
		currentDate.getTime().setMinutes(0);

		upComingNext.add(Calendar.DATE, 2);
		upComingNext.getTime().setHours(0);
		upComingNext.getTime().setMinutes(0);

		if (!jobSendDto.getSchStartDate().equals(null)) {
			if (Date.from(jobSendDto.getSchStartDate()).before(currentDate.getTime())) {
				jobSendDto.setStatePosition(LATE);
			} else if (Date.from(jobSendDto.getSchStartDate()).after(currentDate.getTime())) {
				jobSendDto.setStatePosition(NEXT);
			} else if (Date.from(jobSendDto.getSchStartDate()).after(currentDate.getTime())
					&& Date.from(jobSendDto.getSchStartDate()).before(upComingNext.getTime())) {
				jobSendDto.setStatePosition(UPCOMING);
			}
		}

		return jobSendDto;
	}

	public JobSendDto getJobDataOnDevice(Job job, Optional<BinType> binType,
			List<JobInternalNotesDto> jobInternalNotesDtos) {
		JobSendDto jobSendDto = new JobSendDto();
		jobSendDto.setAssignMemberIds(job.getAssignMemberIds());
		jobSendDto.setAssignUsers((assignUsers != null && assignUsers.size() > 0) ? assignUsers : null);
		jobSendDto.setCreatedBy(job.getCreatedBy());
		jobSendDto.setCreatedTs(job.getCreatedTs());
		jobSendDto.setFinalTotal(job.getFinalTotal());
		jobSendDto.setId(job.getId());
		jobSendDto.setInvoiceRemainder(job.getInvoiceRemainder());
		jobSendDto.setIsDeleted(job.getIsDeleted());
		jobSendDto.setJobInstruction(job.getJobInstruction());
		jobSendDto.setJobNumber(job.getJobNumber());
		jobSendDto.setJobProductService(job.getJobProductService());
		jobSendDto.setJobScheduleType(job.getJobScheduleType());
		jobSendDto.setJobStatus(job.getJobStatus());
		jobSendDto.setJobTitle(job.getJobTitle());

		jobSendDto.setLocationId(job.getLocationId());
		jobSendDto.setNotesLink(job.getNotesLink());
		jobSendDto.setQuoteId(job.getQuoteId());
		jobSendDto.setRequestId(job.getRequestId());
		jobSendDto.setSchCustomDateDay(job.getSchCustomDateDay());
		jobSendDto.setSchCustomDayType(job.getSchCustomDayType());
		jobSendDto.setSchCustomFreqCount(job.getSchCustomFreqCount());
		jobSendDto.setSchCustomFrequency(job.getSchCustomFrequency());
		jobSendDto.setSchCustomWeeks(job.getSchCustomWeeks());
		jobSendDto.setSchDuration(job.getSchDuration());
		jobSendDto.setSchDurationType(job.getSchDurationType());
		jobSendDto.setSchEndDate(job.getSchEndDate());

		jobSendDto.setSchEndTime(job.getSchEndTime());
		jobSendDto.setSchStartDate(job.getSchStartDate());
		jobSendDto.setSchStartTime(job.getSchStartTime());
		jobSendDto.setSchVisitFrequencyId(job.getSchVisitFrequencyId());
		jobSendDto.setUpdatedBy(job.getUpdatedBy());
		jobSendDto.setUpdatedTs(job.getUpdatedTs());
		jobSendDto.setUserId(job.getUserId());
		jobSendDto.setUser(user);

		jobSendDto.setInvoCount(job.getInvoCount());
		jobSendDto.setInvoCustomDateDays(job.getInvoCustomDateDays());
		jobSendDto.setInvoCustomDayType(job.getInvoCustomDayType());

		jobSendDto.setInvoCustomFreq(job.getInvoCustomFreq());
		jobSendDto.setInvoCustomFreqNumber(job.getInvoCustomFreqNumber());
		jobSendDto.setInvoEndDate(job.getInvoEndDate());
		jobSendDto.setInvoStartDate(job.getInvoStartDate());
		jobSendDto.setInvoWant(job.getInvoWant());
		jobSendDto.setInvoWhen(job.getInvoWhen());
		jobSendDto.setTeamAssignMemberIds(job.getTeamAssignMemberIds());

		jobSendDto.setDriverEmail(job.getDriverEmail());
		jobSendDto.setTeamEmail(job.getTeamEmail());
		jobSendDto.setSchLater(job.getSchLater());
		jobSendDto.setIs_active(job.getIs_active());

		jobSendDto.setSchVisitCount(job.getSchVisitCount() != null ? job.getSchVisitCount() : 0);
		jobSendDto.setInvoVisitCount(job.getInvoVisitCount() != null ? job.getInvoVisitCount() : 0);
		jobSendDto.setAssignUserReminderType(job.getAssignUserReminderType());
		jobSendDto.setTeamUserReminder(job.getTeamUserReminder());
		jobSendDto.setTeamSendNotify(job.getTeamSendNotify());
		jobSendDto.setRating(job.getRating() != null ? job.getRating() : 0);

		jobSendDto.setDriverEmailNotify(job.getDriverEmailNotify());
		jobSendDto.setDriverUserReminder(job.getDriverUserReminder());
		jobSendDto.setServiceAsBintypeId(job.getServiceAsBintypeId());
		if (binType.isPresent()) {
			jobSendDto.setServiceAsBintype(binType.get().getName());
		}

		if (jobInternalNotesDtos != null && jobInternalNotesDtos.size() > 0) {
			jobSendDto.setJobInternalNotes(new HashSet<JobInternalNotesDto>(jobInternalNotesDtos));
		}
		return jobSendDto;
	}

	public JobSendDto mapDataJObAndService(Job job, List<JobProductService> jobProductServicesList, User user2) {
		JobSendDto jobSendDto = new JobSendDto();
		jobSendDto.setAssignMemberIds(job.getAssignMemberIds());
		jobSendDto.setAssignUsers((assignUsers != null && assignUsers.size() > 0) ? assignUsers : null);
		jobSendDto.setCreatedBy(job.getCreatedBy());
		jobSendDto.setCreatedTs(job.getCreatedTs());
		jobSendDto.setFinalTotal(job.getFinalTotal());
		jobSendDto.setId(job.getId());
		jobSendDto.setInvoiceRemainder(job.getInvoiceRemainder());
		jobSendDto.setIsDeleted(job.getIsDeleted());
		jobSendDto.setJobInstruction(job.getJobInstruction());
		jobSendDto.setJobNumber(job.getJobNumber());
		jobSendDto.setJobProductService(job.getJobProductService());
		jobSendDto.setProductListByVisit(jobProductServicesList);
		jobSendDto.setJobScheduleType(job.getJobScheduleType());
		jobSendDto.setJobStatus(job.getJobStatus());
		jobSendDto.setJobTitle(job.getJobTitle());

		jobSendDto.setLocationId(job.getLocationId());
		jobSendDto.setNotesLink(job.getNotesLink());
		jobSendDto.setQuoteId(job.getQuoteId());
		jobSendDto.setRequestId(job.getRequestId());
		jobSendDto.setSchCustomDateDay(job.getSchCustomDateDay());
		jobSendDto.setSchCustomDayType(job.getSchCustomDayType());
		jobSendDto.setSchCustomFreqCount(job.getSchCustomFreqCount());
		jobSendDto.setSchCustomFrequency(job.getSchCustomFrequency());
		jobSendDto.setSchCustomWeeks(job.getSchCustomWeeks());
		jobSendDto.setSchDuration(job.getSchDuration());
		jobSendDto.setSchDurationType(job.getSchDurationType());
		jobSendDto.setSchEndDate(job.getSchEndDate());

		jobSendDto.setSchEndTime(job.getSchEndTime());
		jobSendDto.setSchStartDate(job.getSchStartDate());
		jobSendDto.setSchStartTime(job.getSchStartTime());
		jobSendDto.setSchVisitFrequencyId(job.getSchVisitFrequencyId());
		jobSendDto.setUpdatedBy(job.getUpdatedBy());
		jobSendDto.setUpdatedTs(job.getUpdatedTs());
		jobSendDto.setUserId(job.getUserId());
		jobSendDto.setUser(user2);

		jobSendDto.setInvoCount(job.getInvoCount());
		jobSendDto.setInvoCustomDateDays(job.getInvoCustomDateDays());
		jobSendDto.setInvoCustomDayType(job.getInvoCustomDayType());

		jobSendDto.setInvoCustomFreq(job.getInvoCustomFreq());
		jobSendDto.setInvoCustomFreqNumber(job.getInvoCustomFreqNumber());
		jobSendDto.setInvoEndDate(job.getInvoEndDate());
		jobSendDto.setInvoStartDate(job.getInvoStartDate());
		jobSendDto.setInvoWant(job.getInvoWant());
		jobSendDto.setInvoWhen(job.getInvoWhen());
		jobSendDto.setTeamAssignMemberIds(job.getTeamAssignMemberIds());

		jobSendDto.setDriverEmail(job.getDriverEmail());
		jobSendDto.setTeamEmail(job.getTeamEmail());
		jobSendDto.setSchLater(job.getSchLater());
		jobSendDto.setIs_active(job.getIs_active());

		jobSendDto.setSchVisitCount(job.getSchVisitCount() != null ? job.getSchVisitCount() : 0);
		jobSendDto.setInvoVisitCount(job.getInvoVisitCount() != null ? job.getInvoVisitCount() : 0);
		jobSendDto.setAssignUserReminderType(job.getAssignUserReminderType());
		jobSendDto.setTeamUserReminder(job.getTeamUserReminder());
		jobSendDto.setTeamSendNotify(job.getTeamSendNotify());
		jobSendDto.setRating(job.getRating() != null ? job.getRating() : 0);

		jobSendDto.setDriverEmailNotify(job.getDriverEmailNotify());
		jobSendDto.setDriverUserReminder(job.getDriverUserReminder());
		jobSendDto.setServiceAsBintypeId(job.getServiceAsBintypeId());

		return jobSendDto;
	}

}
