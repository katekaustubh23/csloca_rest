package com.checksammy.loca.dto;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.checksammy.loca.model.Location;
import com.checksammy.loca.model.Quote;
import com.checksammy.loca.model.QuotesProductAndService;
import com.checksammy.loca.model.User;

public class QuoteSendDto {

	private Long id;

	private Long jobRequestId;

	private String jobQuotesTitle;

	private Long userId;

	private User user;

	private String quoteNumber;

	private Long userCompanyId;

	private Location locationId;

	private Double subTotal;

	private Double discount;

	private Double taxAmount;

	private Long taxId;

	private Double finalTotal;

	private Double depositValue;

	private Double depositPerValue;

	private Boolean isDeposit;

	private String currencyType;

	private String internalNotes;

	private String attachment;

	private String link_note;

	private Boolean is_deleted;

	private String status;

	private String workStatusId;

	private String depositType;

	private String pdfConfigLink;
	
	private Double discountPerValue;
	
    private Long createdBy;    

    private Instant createdTs;
    
    private String rating;

	/* Custom field */
	private List<QuotesCustomInstanceDto> quotesCustomField;

	/* Quotes product and service */
	private Set<QuotesProductAndService> quotesProductService = new HashSet<>();

	/* Quotes Internal Notes */
	private Set<QuotesInternalNotesDto> quotesInternalNotes = new HashSet<>();
	
	private Long serviceAsBintypeId;
	
	private String serviceAsBintype;
	
	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
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

	public Location getLocationId() {
		return locationId;
	}

	public void setLocationId(Location locationId) {
		this.locationId = locationId;
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

	public Set<QuotesProductAndService> getQuotesProductService() {
		return quotesProductService;
	}

	public void setQuotesProductService(Set<QuotesProductAndService> quotesProductService) {
		this.quotesProductService = quotesProductService;
	}

	public Set<QuotesInternalNotesDto> getQuotesInternalNotes() {
		return quotesInternalNotes;
	}

	public void setQuotesInternalNotes(Set<QuotesInternalNotesDto> quotesInternalNotes) {
		this.quotesInternalNotes = quotesInternalNotes;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Double getDiscountPerValue() {
		return discountPerValue;
	}

	public void setDiscountPerValue(Double discountPerValue) {
		this.discountPerValue = discountPerValue;
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

	public List<QuotesCustomInstanceDto> getQuotesCustomField() {
		return quotesCustomField;
	}

	public void setQuotesCustomField(List<QuotesCustomInstanceDto> quotesCustomField) {
		this.quotesCustomField = quotesCustomField;
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

	public QuoteSendDto getQuotesData(Quote quote, List<QuotesInternalNotesDto> quotesInternalNotesDtos,
			User quoteUser, List<QuotesCustomInstanceDto> quotesCustomInstanceDtos) {
		QuoteSendDto quotesSendDto = new QuoteSendDto();
		quotesSendDto.setAttachment(quote.getAttachment());
		quotesSendDto.setCurrencyType(quote.getCurrencyType());
		quotesSendDto.setDepositPerValue((quote.getDepositPerValue() != null) ? quote.getDepositPerValue() : 0.00);
		quotesSendDto.setDepositType(quote.getDepositType());
		quotesSendDto.setDepositValue((quote.getDepositValue() != null) ? quote.getDepositValue() : 0.00);
		quotesSendDto.setDiscount((quote.getDiscount() != null) ? quote.getDiscount() : 0.00);
		quotesSendDto.setFinalTotal(quote.getFinalTotal());
		quotesSendDto.setId(quote.getId());
		quotesSendDto.setInternalNotes(quote.getInternalNotes());
		quotesSendDto.setIs_deleted(quote.getIs_deleted());
		quotesSendDto.setIsDeposit(quote.getIsDeposit());
		quotesSendDto.setJobQuotesTitle(quote.getJobQuotesTitle());
		quotesSendDto.setJobRequestId((quote.getJobRequestId() != null) ? quote.getJobRequestId() : 0);
		quotesSendDto.setLink_note(quote.getLink_note());
		quotesSendDto.setLocationId(quote.getLocationId());
		quotesSendDto.setPdfConfigLink(quote.getPdfConfigLink());
		quotesSendDto.setQuoteNumber(quote.getQuoteNumber());
		quotesSendDto.setQuotesCustomField(quotesCustomInstanceDtos);
		quotesSendDto.setQuotesInternalNotes(new HashSet<>(quotesInternalNotesDtos));
		quotesSendDto.setQuotesProductService(quote.getQuotesProductService());
		quotesSendDto.setStatus(quote.getStatus());
		quotesSendDto.setSubTotal(quote.getSubTotal());
		quotesSendDto.setTaxAmount(quote.getTaxAmount());
		quotesSendDto.setTaxId(quote.getTaxId());
		quotesSendDto.setUser(quoteUser);
		quotesSendDto.setUserId(quote.getUserId());
		quotesSendDto.setWorkStatusId(quote.getWorkStatusId());
		quotesSendDto.setDiscountPerValue((quote.getDiscountPerValue() != null) ? quote.getDiscountPerValue() : 0.00);
		quotesSendDto.setCreatedBy(quote.getCreatedBy());
		quotesSendDto.setCreatedTs(quote.getCreatedTs());
		quotesSendDto.setRating(quote.getRating());
		quotesSendDto.setServiceAsBintypeId(quote.getServiceAsBintypeId());
		return quotesSendDto;
	}

}
