package com.checksammy.loca.dto;

import java.util.List;

import com.checksammy.loca.model.Invoice;
import com.checksammy.loca.model.InvoiceSchedularDate;

public class BillingInvoiceDto {

	private String status;
	
	private String month;
	
	private Integer monthNumber;
	
	private Integer year;
	
	private List<Invoice> invoiceList;
	
	private List<InvoiceSchedularDate> invoiceScheduleList;
	
	

	public List<InvoiceSchedularDate> getInvoiceScheduleList() {
		return invoiceScheduleList;
	}

	public void setInvoiceScheduleList(List<InvoiceSchedularDate> invoiceScheduleList) {
		this.invoiceScheduleList = invoiceScheduleList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Integer getMonthNumber() {
		return monthNumber;
	}

	public void setMonthNumber(Integer monthNumber) {
		this.monthNumber = monthNumber;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public List<Invoice> getInvoiceList() {
		return invoiceList;
	}

	public void setInvoiceList(List<Invoice> invoiceList) {
		this.invoiceList = invoiceList;
	}
	
}
