package com.checksammy.loca.model;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="invoice_transaction")
public class InvoiceTransaction implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "job_id")
	private Long jobId;
	
	@Column(name = "invoice_id")
	private Long invoiceId;
	
	@Column(name = "invoice_amount")
	private Double invoiceAmount;
	
	@Column(name = "paid_amount")
	private Double paidAmount;
	
	@Column(name = "avl_balance")
	private Double avlBalance;
	
	@Column(name = "invoice_date")
	private Instant invoiceDate;
	
	@Column(name = "pay_date")
	private Instant payDate;
	
	@Column(name = "created_by")
	private Long createdBy;
	
	@Column(name = "created_ts")
	private Instant createdTs;
	
	/* Tokens */
	@Column(name = "trans_token")
	private String transToken;
	
	@Column(name = "customer_id")
	private String customerId;
	
	@Column(name = "save_card_tag")
	private Boolean saveCardTag;
	
	@Column(name = "charge_id")
	private String chargeId;
	
	@Column(name = "payment_from")
	private String paymentFrom;

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

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Double getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(Double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public Double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(Double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public Double getAvlBalance() {
		return avlBalance;
	}

	public void setAvlBalance(Double avlBalance) {
		this.avlBalance = avlBalance;
	}

	public Instant getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Instant invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public Instant getPayDate() {
		return payDate;
	}

	public void setPayDate(Instant payDate) {
		this.payDate = payDate;
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

	public String getTransToken() {
		return transToken;
	}

	public void setTransToken(String transToken) {
		this.transToken = transToken;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Boolean getSaveCardTag() {
		return saveCardTag;
	}

	public void setSaveCardTag(Boolean saveCardTag) {
		this.saveCardTag = saveCardTag;
	}

	public String getChargeId() {
		return chargeId;
	}

	public void setChargeId(String chargeId) {
		this.chargeId = chargeId;
	}

	public String getPaymentFrom() {
		return paymentFrom;
	}

	public void setPaymentFrom(String paymentFrom) {
		this.paymentFrom = paymentFrom;
	}
	
}
