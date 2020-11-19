package com.checksammy.loca.dto;

import java.util.List;

public class SendReminderMailDto {

	private Long userId;
	
	private Long jobId;
	
	private String toUserName;
	
	private List<String> toUser;
	
	private List<String> ccUsers;
	
	private String subject;
	
	private String content;
	
	private Boolean sendCopyUser;
	
	private Boolean includeFeedback;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<String> getToUser() {
		return toUser;
	}

	public void setToUser(List<String> toUser) {
		this.toUser = toUser;
	}

	public List<String> getCcUsers() {
		return ccUsers;
	}

	public void setCcUsers(List<String> ccUsers) {
		this.ccUsers = ccUsers;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getSendCopyUser() {
		return sendCopyUser;
	}

	public void setSendCopyUser(Boolean sendCopyUser) {
		this.sendCopyUser = sendCopyUser;
	}

	public Boolean getIncludeFeedback() {
		return includeFeedback;
	}

	public void setIncludeFeedback(Boolean includeFeedback) {
		this.includeFeedback = includeFeedback;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public SendReminderMailDto setAllData(Long userId2, Long jobId2, String toUserName2, List<String> toUser2,
			List<String> ccUsers2, String subject2, String content2, Boolean sendCopyUser2, Boolean includeFeedback2) {
		SendReminderMailDto sendReminderMailDto = new SendReminderMailDto();
		sendReminderMailDto.setCcUsers(ccUsers2);
		sendReminderMailDto.setContent(content2);
		sendReminderMailDto.setIncludeFeedback(includeFeedback2);
		sendReminderMailDto.setJobId(jobId2);
		sendReminderMailDto.setSendCopyUser(sendCopyUser2);
		sendReminderMailDto.setSubject(subject2);
		sendReminderMailDto.setToUser(toUser2);
		sendReminderMailDto.setToUserName(toUserName2);
		sendReminderMailDto.setUserId(userId2);
		return sendReminderMailDto;
	}

}
