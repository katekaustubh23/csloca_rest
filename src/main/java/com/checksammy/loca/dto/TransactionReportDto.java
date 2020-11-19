package com.checksammy.loca.dto;

import java.time.Instant;
import java.util.Optional;

import com.checksammy.loca.model.Invoice;
import com.checksammy.loca.model.Quote;
import com.checksammy.loca.model.User;

public class TransactionReportDto {

	private Instant date; // 1

	private Instant time; // 2

	private String clientName; // 3

	private String type; // 4 status

	private String paidWith; // 5 card or cash

	private String paidThrough; // 6 portal or client side portal

	private String total; // 7

	private String fee; // 8

	private String note; // 9

	private String cardEnding; // 10

	private String cardType; // 11

	private String invoice; // 12 invoice number a

	private String quote; // 13 quote Number if job attach to quote

	private String payout; // 14

	private String open; // 15

	public Instant getDate() {
		return date;
	}

	public void setDate(Instant date) {
		this.date = date;
	}

	public Instant getTime() {
		return time;
	}

	public void setTime(Instant time) {
		this.time = time;
	}

	public String getClinetName() {
		return clientName;
	}

	public void setClinetName(String clientName) {
		this.clientName = clientName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPaidWith() {
		return paidWith;
	}

	public void setPaidWith(String paidWith) {
		this.paidWith = paidWith;
	}

	public String getPaidThrough() {
		return paidThrough;
	}

	public void setPaidThrough(String paidThrough) {
		this.paidThrough = paidThrough;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getCardEnding() {
		return cardEnding;
	}

	public void setCardEnding(String cardEnding) {
		this.cardEnding = cardEnding;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getInvoice() {
		return invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

	public String getQuote() {
		return quote;
	}

	public void setQuote(String quote) {
		this.quote = quote;
	}

	public String getPayout() {
		return payout;
	}

	public void setPayout(String payout) {
		this.payout = payout;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public TransactionReportDto mapData(Invoice invoiceData, JobVisitReportFilterDto filterDto, User user,
			Optional<Quote> quote2) {
		TransactionReportDto trasDto = new TransactionReportDto();
		trasDto.setCardEnding(cardEnding);
		trasDto.setCardType(cardType);
		trasDto.setClinetName(user.getFirstName() + " " + user.getLastName());
		trasDto.setDate(invoiceData.getLastPayDate());
		trasDto.setFee(fee);

		trasDto.setInvoice(invoiceData.getInvoiceNumber());
		trasDto.setNote(note);
		trasDto.setOpen(open);
		if (invoiceData.getPaymentFrom() != null) {
			trasDto.setPaidThrough(invoiceData.getPaymentFrom().equalsIgnoreCase("client_portal") ? "Client Hub"
					: "CheckSammy Online");
		} else {
			trasDto.setPaidThrough(null);
		}

		trasDto.setPaidWith("Credit Card");
		trasDto.setPayout(payout);
		trasDto.setQuote(quote2 != null ? quote2.get().getQuoteNumber() : null);
		trasDto.setTime(invoiceData.getLastPayDate());
		trasDto.setTotal(invoiceData.getFinalTotal().toString());
		trasDto.setType("Payment");

		return trasDto;
	}

}
