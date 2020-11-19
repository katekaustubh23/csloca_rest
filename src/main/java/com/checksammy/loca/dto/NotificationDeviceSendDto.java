package com.checksammy.loca.dto;

public class NotificationDeviceSendDto {

	private String to;
	
	private NotificationBody notification;
	
	private NotificationDataBody data;

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public NotificationBody getNotification() {
		return notification;
	}

	public void setNotification(NotificationBody notification) {
		this.notification = notification;
	}

	public NotificationDataBody getData() {
		return data;
	}

	public void setData(NotificationDataBody data) {
		this.data = data;
	}
	
	
}
