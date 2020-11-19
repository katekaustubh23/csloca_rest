package com.checksammy.loca.dto;

import java.util.List;

import com.checksammy.loca.model.User;

public class InvoiceDetailDto {

	private User user;
	
	private List<JobSendDto> sendData;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<JobSendDto> getSendData() {
		return sendData;
	}

	public void setSendData(List<JobSendDto> sendData) {
		this.sendData = sendData;
	}
	
}
