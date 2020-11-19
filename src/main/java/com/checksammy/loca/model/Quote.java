package com.checksammy.loca.model;

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
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Where;

@Entity
@Table(name = "req_quotes",uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "quote_number"
            })})
@Where(clause = "is_deleted=0")
public class Quote extends AuditModel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Long id;

	@Column(name = "job_request_id")
	private Long jobRequestId;

	@Column(name = "job_quotes_title")
	private String jobQuotesTitle;

	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "quote_number")
	private String quoteNumber;

	@Column(name = "user_company_id")
	private Long userCompanyId;

	@JoinColumn(name = "location_id", referencedColumnName = "id")
	@ManyToOne()
	private Location locationId;

	@Column(name = "subtotal")
	private Double subTotal;

	@Column(name = "discount")
	private Double discount;
	
	@Column(name = "discount_per_value")
	private Double discountPerValue;

	@Column(name = "tax_amount")
	private Double taxAmount;

	@Column(name = "tax_id")
	private Long taxId;
	
	@Column(name = "final_total")
	private Double finalTotal;
	
	@Column(name = "deposit_value")
	private Double depositValue;
	
	@Column(name = "deposit_per_value")
	private Double depositPerValue;

	@Column(name = "is_deposit") //
	private Boolean isDeposit;
	
	@Column(name = "currency_type")
	private String currencyType;

	@Column(name = "internal_notes")
	private String internalNotes;

	@Column(name = "attachment")
	private String attachment;

	@Column(name = "link_note")
	private String link_note;

	@Column(name = "is_deleted")
	private Boolean is_deleted;

	@Column(name = "status")
	private String status;
	
	@Column(name = "work_status_id")
	private String workStatusId;
	
	@Column(name = "deposit_type")
	private String depositType;
	
	@Column(name = "pdf_config_link")
	private String pdfConfigLink;
	
	@Column(name = "rating")
	private String rating;

	/* Custom field */
	@OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "quoteId")
	@Where(clause = "is_deleted=0")
	@OrderBy("id ASC")
	private Set<QuotesCustomInstance> quotesCustomField = new HashSet<>();
	
	
	/* Quotes product and service */
	@OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "quotesId")
	@Where(clause = "is_deleted=0")
	@OrderBy("id ASC")
	private Set<QuotesProductAndService> quotesProductService = new HashSet<>();
	
	/* Quotes Internal Notes */
	@OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "quoteId")
//	@Where(clause = "is_deleted=0")
	@OrderBy("id ASC")
	private Set<QuotesInternalNotes> quotesInternalNotes = new HashSet<>();
	
	@Column(name = "service_as_bintype_id")
	private Long serviceAsBintypeId;

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

	public Boolean getIsDeposit() {
		return isDeposit;
	}

	public void setIsDeposit(Boolean isDeposit) {
		this.isDeposit = isDeposit;
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

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public String getQuoteNumber() {
		return quoteNumber;
	}

	public void setQuoteNumber(String quoteNumber) {
		this.quoteNumber = quoteNumber;
	}

	public String getWorkStatusId() {
		return workStatusId;
	}

	public void setWorkStatusId(String workStatusId) {
		this.workStatusId = workStatusId;
	}

	public Double getFinalTotal() {
		return finalTotal;
	}

	public void setFinalTotal(Double finalTotal) {
		this.finalTotal = finalTotal;
	}

	public Set<QuotesInternalNotes> getQuotesInternalNotes() {
		return quotesInternalNotes;
	}

	public void setQuotesInternalNotes(Set<QuotesInternalNotes> quotesInternalNotes) {
		this.quotesInternalNotes = quotesInternalNotes;
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

	public Double getDiscountPerValue() {
		return discountPerValue;
	}

	public void setDiscountPerValue(Double discountPerValue) {
		this.discountPerValue = discountPerValue;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public Long getServiceAsBintypeId() {
		return serviceAsBintypeId;
	}

	public void setServiceAsBintypeId(Long serviceAsBintypeId) {
		this.serviceAsBintypeId = serviceAsBintypeId;
	}


//	public Quote setQuotes(Quote quote) {
//		Quote newQuotes = new Quote();
//		newQuotes.setAttachment(attachment);
//		newQuotes.setCreatedBy(createdBy);
//		newQuotes.setCreatedTs(createdTs);
//		newQuotes.set
//		return null;
//	}
	
}
