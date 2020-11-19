package com.checksammy.loca.dto;

import java.time.Instant;

import com.checksammy.loca.model.JobNotificationActionHistory;

public class JobNotificationActionHistoryDto {
	private Long id;
	
	private Long notificationId;
	
	private Long adminId;
	
	private Long driverId;
	
	private String content;
	
	private Boolean action;
	
	private Long jobId;
	
	private Boolean msgStatus;
	
	private Instant createdDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(Long notificationId) {
		this.notificationId = notificationId;
	}

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

	public Long getDriverId() {
		return driverId;
	}

	public void setDriverId(Long driverId) {
		this.driverId = driverId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getAction() {
		return action;
	}

	public void setAction(Boolean action) {
		this.action = action;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public Boolean getMsgStatus() {
		return msgStatus;
	}

	public void setMsgStatus(Boolean msgStatus) {
		this.msgStatus = msgStatus;
	}

	public Instant getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Instant createdDate) {
		this.createdDate = createdDate;
	}

	public JobNotificationActionHistory setNotificationData(
			JobNotificationActionHistoryDto jobNotificationActionHistoryDto) {
		JobNotificationActionHistory jobNotificationActionHistory = new JobNotificationActionHistory();
		jobNotificationActionHistory.setAction(jobNotificationActionHistoryDto.getAction());
		jobNotificationActionHistory.setContent(jobNotificationActionHistoryDto.getContent());
		jobNotificationActionHistory.setCreatedDate(Instant.now());
		jobNotificationActionHistory.setJobId(jobNotificationActionHistoryDto.getJobId());
		jobNotificationActionHistory.setMsgStatus(true);
		jobNotificationActionHistory.setNotificationId(jobNotificationActionHistoryDto.getNotificationId());
		return jobNotificationActionHistory;
	}
	
}
