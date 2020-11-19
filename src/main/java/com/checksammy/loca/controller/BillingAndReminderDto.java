package com.checksammy.loca.controller;

import java.time.Instant;
import java.util.List;

import com.checksammy.loca.dto.BillingInvoiceDto;

public class BillingAndReminderDto {
	
	private Instant nextReminder;
	
	private List<BillingInvoiceDto> billingInoviceList;
	
	private List<BillingInvoiceDto> reminderList;

	public Instant getNextReminder() {
		return nextReminder;
	}

	public void setNextReminder(Instant nextReminder) {
		this.nextReminder = nextReminder;
	}

	public List<BillingInvoiceDto> getBillingInoviceList() {
		return billingInoviceList;
	}

	public void setBillingInoviceList(List<BillingInvoiceDto> billingInoviceList) {
		this.billingInoviceList = billingInoviceList;
	}

	public List<BillingInvoiceDto> getReminderList() {
		return reminderList;
	}

	public void setReminderList(List<BillingInvoiceDto> reminderList) {
		this.reminderList = reminderList;
	}

}
