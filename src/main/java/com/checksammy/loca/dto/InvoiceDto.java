package com.checksammy.loca.dto;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.checksammy.loca.model.Invoice;
import com.checksammy.loca.model.InvoiceCustomInstance;
import com.checksammy.loca.model.InvoiceInternalNotes;
import com.checksammy.loca.model.InvoiceProductAndService;
import com.checksammy.loca.model.Job;
import com.checksammy.loca.model.Location;
import com.checksammy.loca.model.User;

public class InvoiceDto {

	private Long id;

	/* user */
	private Long userId;

	/* Location Id for Location ...for service address */
	private Long locationId;

	/* For JOb Id */
	private Long jobId;
	
	private Long quoteId;
	
	private Long requestId;

	private String jobNumber;

	/* Billing address */
	private String billingAddress;

	private String invoiceNumber;

	private String invJobInstruction;

	/* Invoice issue Date */
	private Instant invIssueDate;

	private String invoiceJobContact;

	private Double subtotal;

	private Double discount;

	private Double discountPerValue;

	private String currencyType;

	private String depositType;

	private Double depositValue;

	private Double depositPerValue;

	private Double finalTotal;

	private Boolean isDeposit;

	private Double taxAmount;

	private Long taxId;

	private String linkAttachment;

	private String status;

	private Long workStatusId;

	private Boolean isDeleted;

	private Long createdBy;

	private Instant createdTs;

	private Long updatedBy;

	private Instant updatedTs;

	/* Invoice Custom Instance */
	private Set<InvoiceCustomInstance> inoviceCustomInstance = new HashSet<>();

	/* Invoice Product & Services */
	private Set<InvoiceProductAndService> invoiceProductService = new HashSet<>();

	/* Invoice Internal_notes */
	private Set<InvoiceInternalNotesDto> invoiceInternalNotes = new HashSet<>();

	/* Invoice Custom Instance Dto */
	private Set<InvoiceCustomInstanceDto> invoiceCustomInstanceDto = new HashSet<>();

	private User user;

	private Location location;

	private Job job;
	
	private String sendStatus;
	
	private Double availableBalance;
	
	private Instant lastPayDate;
	
	private Long serviceAsBintypeId;
	
	private String serviceAsBintype;
	
	private List<Long> visitId;
	
	private String paymentFrom;

	public String getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
	}

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

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
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

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Instant getUpdatedTs() {
		return updatedTs;
	}

	public void setUpdatedTs(Instant updatedTs) {
		this.updatedTs = updatedTs;
	}

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

	public Set<InvoiceInternalNotesDto> getInvoiceInternalNotes() {
		return invoiceInternalNotes;
	}

	public void setInvoiceInternalNotes(Set<InvoiceInternalNotesDto> invoiceInternalNotes) {
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public Set<InvoiceCustomInstanceDto> getInvoiceCustomInstanceDto() {
		return invoiceCustomInstanceDto;
	}

	public void setInvoiceCustomInstanceDto(Set<InvoiceCustomInstanceDto> invoiceCustomInstanceDto) {
		this.invoiceCustomInstanceDto = invoiceCustomInstanceDto;
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

	public String getServiceAsBintype() {
		return serviceAsBintype;
	}

	public void setServiceAsBintype(String serviceAsBintype) {
		this.serviceAsBintype = serviceAsBintype;
	}

	public List<Long> getVisitId() {
		return visitId;
	}

	public void setVisitId(List<Long> visitId) {
		this.visitId = visitId;
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

	/* Set Invoice Data from Dto */
	public Invoice setInvoiceData(InvoiceDto invoiceSave) {
		Invoice invoice = new Invoice();
		invoice.setBillingAddress(invoiceSave.getBillingAddress());

		if (invoiceSave.getId() != null && invoiceSave.getId() > 0) {
			invoice.setId(invoiceSave.getId());
			invoice.setUpdatedBy(invoiceSave.getCreatedBy());
			invoice.setUpdatedTs(Instant.now());
		}
		invoice.setCreatedBy(invoiceSave.getCreatedBy());
		invoice.setCreatedTs(invoiceSave.getCreatedTs());
		invoice.setCurrencyType(invoiceSave.getCurrencyType());
		invoice.setDepositPerValue(invoiceSave.getDepositPerValue());
		invoice.setDepositType(invoiceSave.getDepositType());
		invoice.setDepositValue(invoiceSave.getDepositValue());
		invoice.setDiscount(invoiceSave.getDiscount());
		invoice.setDiscountPerValue(invoiceSave.getDiscountPerValue());
		invoice.setFinalTotal(invoiceSave.getFinalTotal());

		invoice.setInoviceCustomInstance(invoiceSave.getInoviceCustomInstance());
		invoice.setInvIssueDate(invoiceSave.getInvIssueDate());
		invoice.setInvJobInstruction(invoiceSave.getInvJobInstruction());
//		invoice.setInvoiceInternalNotes(invoiceSave.getInvoiceInternalNotes());
		invoice.setInvoiceNumber(invoiceSave.getInvoiceNumber());
		invoice.setInvoiceJobContact(invoiceSave.getInvoiceJobContact());
		invoice.setInvoiceProductService(invoiceSave.getInvoiceProductService());
		invoice.setIsDeleted(invoiceSave.getIsDeleted());
		invoice.setIsDeposit(invoiceSave.getIsDeposit());
//		invoice.setJobId(jobId); // update by static query
		invoice.setJobNumber(invoiceSave.getJobNumber());
		invoice.setLinkAttachment(invoiceSave.getLinkAttachment());
//		invoice.setLocationId(locationId); // update by static query
		invoice.setStatus(invoiceSave.getStatus());
		invoice.setSubtotal(invoiceSave.getSubtotal());
//		invoice.setUserId(userId); // update by static query
		invoice.setWorkStatusId(invoiceSave.getWorkStatusId());
		invoice.setTaxAmount(invoiceSave.getTaxAmount());
		invoice.setTaxId(invoiceSave.getTaxId());
		/*Srpint 10 new changes*/
		invoice.setAvailableBalance(invoiceSave.getAvailableBalance());
		
		invoice.setLastPayDate(invoiceSave.getLastPayDate());
		invoice.setServiceAsBintypeId(invoiceSave.getServiceAsBintypeId());
		invoice.setQuoteId(invoiceSave.getQuoteId());
		invoice.setRequestId(invoiceSave.getRequestId());
		
		Set<InvoiceInternalNotes> setInvoiceInternalNotes = new HashSet<InvoiceInternalNotes>();
		for (InvoiceInternalNotesDto internalNotesDto : invoiceSave.getInvoiceInternalNotes()) {
			InvoiceInternalNotes invoiceInternalNotes = new InvoiceInternalNotes();
			invoiceInternalNotes.setAttachment(internalNotesDto.getAttachment());
			invoiceInternalNotes.setCreatedBy(internalNotesDto.getCreatedBy());
			invoiceInternalNotes.setCreatedTs(internalNotesDto.getCreatedTs());
			invoiceInternalNotes.setId(internalNotesDto.getId());
			invoiceInternalNotes.setInvoiceId(
					(internalNotesDto.getInvoiceId() != null) ? invoiceInternalNotes.getInvoiceId() : null);
			invoiceInternalNotes.setNotes(internalNotesDto.getNotes());
			if (internalNotesDto.getId() != null && internalNotesDto.getId() > 0) {
				invoiceInternalNotes.setUpdatedBy(internalNotesDto.getCreatedBy());
				invoiceInternalNotes.setUpdatedTs(Instant.now());
			}
			setInvoiceInternalNotes.add(invoiceInternalNotes);
		}
		invoice.setInvoiceInternalNotes(setInvoiceInternalNotes);
		invoice.setPaymentFrom(invoiceSave.getPaymentFrom());
		return invoice;
	}

	public InvoiceDto getInvoiceData(Invoice invoice, List<InvoiceInternalNotesDto> invoiceInternalNotesDtos,
			List<InvoiceCustomInstanceDto> invoiceCustomInstanceDtos) {
		InvoiceDto invoiceDto = new InvoiceDto();
		invoiceDto.setBillingAddress(invoice.getBillingAddress());
		invoiceDto.setCreatedBy(invoice.getCreatedBy());
		invoiceDto.setCreatedTs(invoice.getCreatedTs());
		invoiceDto.setCurrencyType(invoice.getCurrencyType());
		invoiceDto.setDepositPerValue(invoice.getDepositPerValue());
		invoiceDto.setDepositType(invoice.getDepositType());
		invoiceDto.setDepositValue(invoice.getDepositValue());
		invoiceDto.setDiscount(invoice.getDiscount());
		invoiceDto.setDiscountPerValue(invoice.getDiscountPerValue());
		invoiceDto.setFinalTotal(invoice.getFinalTotal());
		invoiceDto.setId(invoice.getId());

		invoiceDto.setInoviceCustomInstance(invoice.getInoviceCustomInstance());
		invoiceDto.setInvIssueDate(invoice.getInvIssueDate());
		invoiceDto.setInvJobInstruction(invoice.getInvJobInstruction());
		invoiceDto.setInvoiceInternalNotes(new HashSet<InvoiceInternalNotesDto>(invoiceInternalNotesDtos));
		invoiceDto.setInvoiceNumber(invoice.getInvoiceNumber());
		invoiceDto.setInvoiceJobContact(invoice.getInvoiceJobContact());
		invoiceDto.setInvoiceProductService(invoice.getInvoiceProductService());
		invoiceDto.setIsDeleted(invoice.getIsDeleted());
		invoiceDto.setIsDeposit(invoice.getIsDeposit());
		invoiceDto.setJob(invoice.getJobId()); // update by static query
		invoiceDto.setJobId((invoice.getJobId() != null ? invoice.getJobId().getId():0L));
		invoiceDto.setJobNumber(invoice.getJobNumber());
		invoiceDto.setLinkAttachment(invoice.getLinkAttachment());
		invoiceDto.setLocation(invoice.getLocationId()); // update by static query
		invoiceDto.setStatus(invoice.getStatus());
		invoiceDto.setSubtotal(invoice.getSubtotal());
		invoiceDto.setUser(invoice.getUserId()); // update by static query
		invoiceDto.setWorkStatusId(invoice.getWorkStatusId());
		invoiceDto.setTaxAmount(invoice.getTaxAmount());
		invoiceDto.setTaxId(invoice.getTaxId());
		invoiceDto.setUpdatedBy(invoice.getCreatedBy());
		invoiceDto.setUpdatedTs(invoice.getUpdatedTs());
		invoiceDto.setInvoiceCustomInstanceDto(new HashSet<InvoiceCustomInstanceDto>(invoiceCustomInstanceDtos));
		/*Srpint 10 new changes*/
		invoiceDto.setAvailableBalance(invoice.getAvailableBalance());
		invoiceDto.setLastPayDate(invoice.getLastPayDate());
		invoiceDto.setQuoteId(invoice.getQuoteId());
		invoiceDto.setRequestId(invoice.getRequestId());
		
		invoiceDto.setServiceAsBintypeId(invoice.getServiceAsBintypeId());
		invoiceDto.setPaymentFrom(invoice.getPaymentFrom());
		return invoiceDto;
	}

}
