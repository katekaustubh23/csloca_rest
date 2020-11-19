package com.checksammy.loca.dto;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.checksammy.loca.model.Quote;
import com.checksammy.loca.model.QuotesCustomInstance;
import com.checksammy.loca.model.QuotesInternalNotes;
import com.checksammy.loca.model.QuotesProductAndService;

public class QuoteDto {

	private Long id;

	private Long jobRequestId;

	private String jobQuotesTitle;

	private Long userId;

	private String quoteNumber;

	private Long userCompanyId;

	private Long locationId;

	private Double subTotal;

	private Double discount;
	
	private Double discountPerValue;

	private Double taxAmount;

	private Long taxId;

	private Double finalTotal;

	private Boolean isDeposit;
	
	private Double depositValue;
	
	private Double depositPerValue;

	private String currencyType;

	private String internalNotes;

	private String attachment;

	private String link_note;

	private Boolean is_deleted;

	private String status;

	private String workStatusId;
	
	private String depositType;
	
	private String pdfConfigLink;

	private Set<QuotesCustomInstance> quotesCustomField = new HashSet<>();

	private Set<QuotesProductAndService> quotesProductService = new HashSet<>();

	private Set<QuotesInternalNotes> quotesInternalNotes = new HashSet<>();

	private Long createdBy;

	private Instant createdTs;

	private Long updatedBy;

	private Instant updatedTs;
	
	private String rating;
	
	private Long serviceAsBintypeId;

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public Double getDepositValue() {
		return depositValue;
	}

	public void setDepositValue(Double depositValue) {
		this.depositValue = depositValue;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getJobRequestId() {
		return jobRequestId;
	}

	public void setJobRequestId(Long jobRequestId) {
		this.jobRequestId = jobRequestId;
	}

	public String getJobQuotesTitle() {
		return jobQuotesTitle;
	}

	public void setJobQuotesTitle(String jobQuotesTitle) {
		this.jobQuotesTitle = jobQuotesTitle;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getQuoteNumber() {
		return quoteNumber;
	}

	public void setQuoteNumber(String quoteNumber) {
		this.quoteNumber = quoteNumber;
	}

	public Long getUserCompanyId() {
		return userCompanyId;
	}

	public void setUserCompanyId(Long userCompanyId) {
		this.userCompanyId = userCompanyId;
	}

	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
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

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public String getInternalNotes() {
		return internalNotes;
	}

	public void setInternalNotes(String internalNotes) {
		this.internalNotes = internalNotes;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getLink_note() {
		return link_note;
	}

	public void setLink_note(String link_note) {
		this.link_note = link_note;
	}

	public Boolean getIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(Boolean is_deleted) {
		this.is_deleted = is_deleted;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getWorkStatusId() {
		return workStatusId;
	}

	public void setWorkStatusId(String workStatusId) {
		this.workStatusId = workStatusId;
	}

	public Set<QuotesCustomInstance> getQuotesCustomField() {
		return quotesCustomField;
	}

	public void setQuotesCustomField(Set<QuotesCustomInstance> quotesCustomField) {
		this.quotesCustomField = quotesCustomField;
	}

	public Set<QuotesProductAndService> getQuotesProductService() {
		return quotesProductService;
	}

	public void setQuotesProductService(Set<QuotesProductAndService> quotesProductService) {
		this.quotesProductService = quotesProductService;
	}

	public Set<QuotesInternalNotes> getQuotesInternalNotes() {
		return quotesInternalNotes;
	}

	public void setQuotesInternalNotes(Set<QuotesInternalNotes> quotesInternalNotes) {
		this.quotesInternalNotes = quotesInternalNotes;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
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

	public String getDepositType() {
		return depositType;
	}

	public void setDepositType(String depositType) {
		this.depositType = depositType;
	}

	public String getPdfConfigLink() {
		return pdfConfigLink;
	}

	public void setPdfConfigLink(String pdfConfigLink) {
		this.pdfConfigLink = pdfConfigLink;
	}

	public Double getDepositPerValue() {
		return depositPerValue;
	}

	public void setDepositPerValue(Double depositPerValue) {
		this.depositPerValue = depositPerValue;
	}

	public Double getDiscountPerValue() {
		return discountPerValue;
	}

	public void setDiscountPerValue(Double discountPerValue) {
		this.discountPerValue = discountPerValue;
	}

	public Long getServiceAsBintypeId() {
		return serviceAsBintypeId;
	}

	public void setServiceAsBintypeId(Long serviceAsBintypeId) {
		this.serviceAsBintypeId = serviceAsBintypeId;
	}

	public Quote setQuotes(QuoteDto quoteDto) {
		Quote quote = new Quote();
		quote.setAttachment(quoteDto.getAttachment());
		quote.setCreatedBy(quoteDto.getCreatedBy());
		quote.setCreatedTs(quoteDto.getCreatedTs());
		quote.setCurrencyType(quoteDto.getCurrencyType());
		quote.setDiscount((quoteDto.getDiscount() != null) ? quoteDto.getDiscount() : 0.00);
		quote.setFinalTotal((quoteDto.getFinalTotal() != null) ? quoteDto.getFinalTotal() : 0.00);

		if (quoteDto.getId() != null && quoteDto.getId() > 0) {
			quote.setId(quoteDto.getId());
			quote.setUpdatedBy(quoteDto.getCreatedBy());
			quote.setUpdatedTs(Instant.now());
		}

		quote.setInternalNotes(quoteDto.getInternalNotes());
		quote.setIs_deleted(quoteDto.getIs_deleted());
		quote.setIsDeposit(quoteDto.getIsDeposit());
		quote.setJobQuotesTitle(quoteDto.getJobQuotesTitle());
		quote.setJobRequestId(quoteDto.getJobRequestId());
		quote.setLink_note(quoteDto.getLink_note());
		quote.setQuoteNumber(quoteDto.getQuoteNumber());
		quote.setQuotesCustomField(quoteDto.getQuotesCustomField());
		quote.setQuotesInternalNotes(
				(quoteDto.getQuotesInternalNotes().size() > 0) ? quoteDto.getQuotesInternalNotes() : null);
		quote.setQuotesProductService(
				(quoteDto.getQuotesProductService().size() > 0) ? quoteDto.getQuotesProductService() : null);
		quote.setStatus(quoteDto.getStatus());
		quote.setSubTotal(quoteDto.getSubTotal());
		quote.setTaxAmount(quoteDto.getTaxAmount());
		quote.setTaxId(quoteDto.getTaxId());

		quote.setUserCompanyId(quoteDto.getUserCompanyId());
		quote.setUserId(quoteDto.getUserId());
		quote.setWorkStatusId(quoteDto.getWorkStatusId());
		quote.setPdfConfigLink(quoteDto.getPdfConfigLink());
		quote.setDepositType(quoteDto.getDepositType());
		quote.setDepositValue(quoteDto.getDepositValue());
		quote.setDiscountPerValue(quoteDto.getDiscountPerValue());
		quote.setDepositPerValue(quoteDto.getDepositPerValue());
		quote.setRating(quoteDto.getRating());
		quote.setServiceAsBintypeId(quoteDto.getServiceAsBintypeId());
		return quote;
	}

}
