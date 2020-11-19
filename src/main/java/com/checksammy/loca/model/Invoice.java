package com.checksammy.loca.model;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

@Entity
@Table(name = "invoice_job")
@Where(clause = "is_deleted=0")
public class Invoice extends AuditModel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;

	@JoinColumn(name = "user_id", referencedColumnName = "id")
	@ManyToOne()
	private User userId;

	@JoinColumn(name = "location_id", referencedColumnName = "id")
	@ManyToOne()
	private Location locationId;

	@JoinColumn(name = "job_id", referencedColumnName = "id")
	@ManyToOne()
	private Job jobId;

	@Column(name = "quote_id")
	private Long quoteId;

	@Column(name = "request_id")
	private Long requestId;

	@Column(name = "job_number")
	private String jobNumber;

	@Column(name = "billing_address_user")
	private String billingAddress;

	@Column(name = "invoice_number")
	private String invoiceNumber;

	@Column(name = "inv_job_instruction")
	private String invJobInstruction;

	/* Invoice issue Date */
	@Column(name = "inv_issue_date")
	private Instant invIssueDate;

	@Column(name = "invoice_job_contact")
	private String invoiceJobContact;

	@Column(name = "subtotal")
	private Double subtotal;

	@Column(name = "discount")
	private Double discount;

	@Column(name = "discount_per_value")
	private Double discountPerValue;

	@Column(name = "currency_type")
	private String currencyType;

	@Column(name = "deposit_type")
	private String depositType;

	@Column(name = "deposit_value")
	private Double depositValue;

	@Column(name = "deposit_per_value")
	private Double depositPerValue;

	@Column(name = "final_total")
	private Double finalTotal;

	@Column(name = "tax_amount")
	private Double taxAmount;

	@Column(name = "tax_id")
	private Long taxId;

	@Column(name = "is_deposit")
	private Boolean isDeposit;

	@Column(name = "link_attachment")
	private String linkAttachment;

	@Column(name = "status")
	private String status;

	@Column(name = "work_status_id")
	private Long workStatusId;

	@Column(name = "is_deleted")
	private Boolean isDeleted;

	/* SPRINT 10 new changes for payment balance */
	@Column(name = "available_balance")
	private Double availableBalance;

	@Column(name = "last_pay_date")
	private Instant lastPayDate;

	/* Invoice Custom Instance */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "invoiceId")
	@Where(clause = "is_deleted=0")
	@OrderBy("id ASC")
	private Set<InvoiceCustomInstance> inoviceCustomInstance = new HashSet<>();

	/* Invoice product and service */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "invoiceId")
	@Where(clause = "is_deleted=0")
	@OrderBy("id ASC")
	private Set<InvoiceProductAndService> invoiceProductService = new HashSet<>();

	/* Invoice Internal Notes */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "invoiceId")
//	@Where(clause = "is_deleted=0")
	@OrderBy("id ASC")
	private Set<InvoiceInternalNotes> invoiceInternalNotes = new HashSet<>();

	@Column(name = "service_as_bintype_id")
	private Long serviceAsBintypeId;
	
	@Column(name = "payment_from")
	private String paymentFrom;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public Location getLocationId() {
		return locationId;
	}

	public void setLocationId(Location locationId) {
		this.locationId = locationId;
	}

	public Job getJobId() {
		return jobId;
	}

	public void setJobId(Job jobId) {
		this.jobId = jobId;
	}

	public String getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}

	public String getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getInvJobInstruction() {
		return invJobInstruction;
	}

	public void setInvJobInstruction(String invJobInstruction) {
		this.invJobInstruction = invJobInstruction;
	}

	public Instant getInvIssueDate() {
		return invIssueDate;
	}

	public void setInvIssueDate(Instant invIssueDate) {
		this.invIssueDate = invIssueDate;
	}

	public String getInvoiceJobContact() {
		return invoiceJobContact;
	}

	public void setInvoiceJobContact(String invoiceJobContact) {
		this.invoiceJobContact = invoiceJobContact;
	}

	public Double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getDiscountPerValue() {
		return discountPerValue;
	}

	public void setDiscountPerValue(Double discountPerValue) {
		this.discountPerValue = discountPerValue;
	}

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public String getDepositType() {
		return depositType;
	}

	public void setDepositType(String depositType) {
		this.depositType = depositType;
	}

	public Double getDepositValue() {
		return depositValue;
	}

	public void setDepositValue(Double depositValue) {
		this.depositValue = depositValue;
	}

	public Double getDepositPerValue() {
		return depositPerValue;
	}

	public void setDepositPerValue(Double depositPerValue) {
		this.depositPerValue = depositPerValue;
	}

	public Double getFinalTotal() {
		return finalTotal;
	}

	public void setFinalTotal(Double finalTotal) {
		this.finalTotal = finalTotal;
	}

	public Boolean getIsDeposit() {
		return isDeposit;
	}

	public void setIsDeposit(Boolean isDeposit) {
		this.isDeposit = isDeposit;
	}

	public String getLinkAttachment() {
		return linkAttachment;
	}

	public void setLinkAttachment(String linkAttachment) {
		this.linkAttachment = linkAttachment;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getWorkStatusId() {
		return workStatusId;
	}

	public void setWorkStatusId(Long workStatusId) {
		this.workStatusId = workStatusId;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	/* Invoice Custom, P&S, Internal note */

	public Set<InvoiceCustomInstance> getInoviceCustomInstance() {
		return inoviceCustomInstance;
	}

	public void setInoviceCustomInstance(Set<InvoiceCustomInstance> inoviceCustomInstance) {
		this.inoviceCustomInstance = inoviceCustomInstance;
	}

	public Set<InvoiceProductAndService> getInvoiceProductService() {
		return invoiceProductService;
	}

	public void setInvoiceProductService(Set<InvoiceProductAndService> invoiceProductService) {
		this.invoiceProductService = invoiceProductService;
	}

	public Set<InvoiceInternalNotes> getInvoiceInternalNotes() {
		return invoiceInternalNotes;
	}

	public void setInvoiceInternalNotes(Set<InvoiceInternalNotes> invoiceInternalNotes) {
		this.invoiceInternalNotes = invoiceInternalNotes;
	}

	public Double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(Double taxAmount) {
		this.taxAmount = taxAmount;
	}

	public Long getTaxId() {
		return taxId;
	}

	public void setTaxId(Long taxId) {
		this.taxId = taxId;
	}

	public Double getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(Double availableBalance) {
		this.availableBalance = availableBalance;
	}

	public Instant getLastPayDate() {
		return lastPayDate;
	}

	public void setLastPayDate(Instant lastPayDate) {
		this.lastPayDate = lastPayDate;
	}

	public Long getServiceAsBintypeId() {
		return serviceAsBintypeId;
	}

	public void setServiceAsBintypeId(Long serviceAsBintypeId) {
		this.serviceAsBintypeId = serviceAsBintypeId;
	}

	public Long getQuoteId() {
		return quoteId;
	}

	public void setQuoteId(Long quoteId) {
		this.quoteId = quoteId;
	}

	public Long getRequestId() {
		return requestId;
	}

	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}

	public String getPaymentFrom() {
		return paymentFrom;
	}

	public void setPaymentFrom(String paymentFrom) {
		this.paymentFrom = paymentFrom;
	}

//	@Column(name = "created_by")
//	private Long createdBy;
//	
//	@Column(name = "created_ts")
//	private Instant createdTs;
//	
//	@Column(name = "updated_by")
//	private Long updatedBy;
//	
//	@Column(name = "updated_ts")
//	private Instant updatedTs;

}
