package com.checksammy.loca.dto;

import java.util.List;

/**
*
* @author Jeevan Kulkarni
*/
public class EmailDto {
	
	public String subject;
	public String editorHtml;
	public List<EmailList> sendToEmails;
	public List<EmailList> ccEmails;
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getEditorHtml() {
		return editorHtml;
	}
	public void setEditorHtml(String editorHtml) {
		this.editorHtml = editorHtml;
	}
	public List<EmailList> getSendToEmails() {
		return sendToEmails;
	}
	public void setSendToEmails(List<EmailList> sendToEmails) {
		this.sendToEmails = sendToEmails;
	}
	public List<EmailList> getCcEmails() {
		return ccEmails;
	}
	public void setCcEmails(List<EmailList> ccEmails) {
		this.ccEmails = ccEmails;
	}
	
	@Override
	public String toString() {
		return "CateringEmailDto [subject=" + subject + ", editorHtml=" + editorHtml + ", sendToEmails=" + sendToEmails
				+ ", ccEmails=" + ccEmails + "]";
	}

}
