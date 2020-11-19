package com.checksammy.loca.model;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="job_notification_acton_history")
public class JobNotificationActionHistory implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "notification_id")
	private Long notificationId;
	
	@Column(name = "admin_id")
	private Long adminId;
	
	@JoinColumn(name = "driver_id", referencedColumnName = "id")
	@ManyToOne()
	private User driverId;
	
//	@Column(name = "driver_id")
//	private Long driverId;
	
	@Column(name = "content")
	private String content;
	
	@Column(name = "action")
	private Boolean action;
	
	@Column(name = "job_id")
	private Long jobId;
	
	@Column(name = "job_number")
	private String jobNumber;
	
	@Column(name = "msg_status")
	private Boolean msgStatus;
	
	@Column(name = "created_date")
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

//	public Long getDriverId() {
//		return driverId;
//	}
//
//	public void setDriverId(Long driverId) {
//		this.driverId = driverId;
//	}

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

	public User getDriverId() {
		return driverId;
	}

	public void setDriverId(User driverId) {
		this.driverId = driverId;
	}

	public String getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}
}
