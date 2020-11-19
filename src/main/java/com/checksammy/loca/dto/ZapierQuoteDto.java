package com.checksammy.loca.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.checksammy.loca.model.Location;
import com.checksammy.loca.model.ProductService;
import com.checksammy.loca.model.Quote;
import com.checksammy.loca.model.QuotesProductAndService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class ZapierQuoteDto {

	private Integer id;

	private String quote_number;

	private String state;

	private String job_description;
	
	private List<ZapierUserDto> client;
	
	private List<ZapierListDto> line_items;

//	private Instant created_at;
//
//	private Instant updated_at;
//
//	private String scheduled_at;
//
//	private String approved_at;
//
//	private String changes_requested_at;
//
//	private String won_at;
//
//	private String discount_type;
//
//	private Double discount_amount;
//
//	private Double discount_rate;
//
//	private Double cost;
//
//	private Double tax;
//
//	private Double deposit_rate;
//
//	private Double deposit_amount;
//
//	private String deposit_type;
//
//	private String message;
//
//	private String disclaimer;
//
//	private Boolean has_signature;
//
//	private ZapierPropertyDto property;
//

//
//	private HashMap<String, Object> line_items;

	public List<ZapierListDto> getList() {
		return line_items;
	}

	public void setList(List<ZapierListDto> line_items) {
		this.line_items = line_items;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getQuote_number() {
		return quote_number;
	}

	public void setQuote_number(String quote_number) {
		this.quote_number = quote_number;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getJob_description() {
		return job_description;
	}

	public void setJob_description(String job_description) {
		this.job_description = job_description;
	}

//	public Instant getCreated_at() {
//		return created_at;
//	}
//
//	public void setCreated_at(Instant created_at) {
//		this.created_at = created_at;
//	}
//
//	public Instant getUpdated_at() {
//		return updated_at;
//	}
//
//	public void setUpdated_at(Instant updated_at) {
//		this.updated_at = updated_at;
//	}
//
//	public String getScheduled_at() {
//		return scheduled_at;
//	}
//
//	public void setScheduled_at(String scheduled_at) {
//		this.scheduled_at = scheduled_at;
//	}
//
//	public String getApproved_at() {
//		return approved_at;
//	}
//
//	public void setApproved_at(String approved_at) {
//		this.approved_at = approved_at;
//	}
//
//	public String getChanges_requested_at() {
//		return changes_requested_at;
//	}
//
//	public void setChanges_requested_at(String changes_requested_at) {
//		this.changes_requested_at = changes_requested_at;
//	}
//
//	public String getWon_at() {
//		return won_at;
//	}
//
//	public void setWon_at(String won_at) {
//		this.won_at = won_at;
//	}
//
//	public String getDiscount_type() {
//		return discount_type;
//	}
//
//	public void setDiscount_type(String discount_type) {
//		this.discount_type = discount_type;
//	}
//
//	public Double getDiscount_amount() {
//		return discount_amount;
//	}
//
//	public void setDiscount_amount(Double discount_amount) {
//		this.discount_amount = discount_amount;
//	}
//
//	public Double getDiscount_rate() {
//		return discount_rate;
//	}
//
//	public void setDiscount_rate(Double discount_rate) {
//		this.discount_rate = discount_rate;
//	}
//
//	public Double getCost() {
//		return cost;
//	}
//
//	public void setCost(Double cost) {
//		this.cost = cost;
//	}
//
//	public Double getTax() {
//		return tax;
//	}
//
//	public void setTax(Double tax) {
//		this.tax = tax;
//	}
//
//	public Double getDeposit_rate() {
//		return deposit_rate;
//	}
//
//	public void setDeposit_rate(Double deposit_rate) {
//		this.deposit_rate = deposit_rate;
//	}
//
//	public Double getDeposit_amount() {
//		return deposit_amount;
//	}
//
//	public void setDeposit_amount(Double deposit_amount) {
//		this.deposit_amount = deposit_amount;
//	}
//
//	public String getDeposit_type() {
//		return deposit_type;
//	}
//
//	public void setDeposit_type(String deposit_type) {
//		this.deposit_type = deposit_type;
//	}
//
//	public String getMessage() {
//		return message;
//	}
//
//	public void setMessage(String message) {
//		this.message = message;
//	}
//
//	public String getDisclaimer() {
//		return disclaimer;
//	}
//
//	public void setDisclaimer(String disclaimer) {
//		this.disclaimer = disclaimer;
//	}
//
//	public Boolean getHas_signature() {
//		return has_signature;
//	}
//
//	public void setHas_signature(Boolean has_signature) {
//		this.has_signature = has_signature;
//	}
//
//	public ZapierPropertyDto getProperty() {
//		return property;
//	}
//
//	public void setProperty(ZapierPropertyDto property) {
//		this.property = property;
//	}
//
	public List<ZapierUserDto> getClient() {
		return client;
	}

	public void setClient(List<ZapierUserDto> client) {
		this.client = client;
	}
//
//	public HashMap<String, Object> getLine_items() {
//		return line_items;
//	}
//
//	public void setLine_items(HashMap<String, Object> line_items) {
//		this.line_items = line_items;
//	}

	public Quote addedQuote(ZapierQuoteDto zapierQuote, List<Location> newLocation) {

		Quote quoteNew = new Quote();
		quoteNew.setQuoteNumber(zapierQuote.getQuote_number().toString());
//		quoteNew.setCreatedTs(zapierQuote.getCreated_at());
//		quoteNew.setUpdatedTs(zapierQuote.getUpdated_at());

		quoteNew.setStatus(zapierQuote.getState());
		quoteNew.setJobQuotesTitle(zapierQuote.getJob_description());
		quoteNew.setIsDeposit(false);
		quoteNew.setIs_deleted(false);
		quoteNew.setWorkStatusId("1");
		quoteNew.setCreatedBy(Long.valueOf(102));
//		quoteNew.setCurrencyType(zapierQuote.getDiscount_type());
//		quoteNew.setDiscount(zapierQuote.getDiscount_rate());
//		quoteNew.setDiscountPerValue(zapierQuote.getDiscount_amount());
//		quoteNew.setFinalTotal(zapierQuote.getCost());
//		quoteNew.setDepositValue(zapierQuote.getDeposit_rate());
//		quoteNew.setDepositPerValue(zapierQuote.getDeposit_amount());
//		quoteNew.setDepositType(zapierQuote.getDeposit_type());
//		quoteNew.setTaxAmount(zapierQuote.getTax());
//		quoteNew.setUserCompanyId(Long.valueOf(0));
//		quoteNew.setSubTotal(zapierQuote.getCost());
		
		return quoteNew;
	}

	public List<QuotesProductAndService> addedQuoteProduct(ZapierQuoteDto zapierQuote,
			List<ProductService> productService, Quote quoteNew) {
		List<QuotesProductAndService> quotesProductAndServices = new ArrayList<QuotesProductAndService>();
		HashMap<String, QuotesProductAndService> quoteHashMap = new HashMap<String, QuotesProductAndService>();
//		for (int i = 0; i < zapierQuote.getLine_items().size(); i++) {
//			System.out.println(i);
////			ObjectMapper objMapper = new ObjectMapper();
//			ZapierProductServiceDto zapierPrimaryEmail = new ZapierProductServiceDto();
//			
//			Gson g = new Gson();
//			String convertedToString = String.valueOf(zapierQuote.getLine_items().get(String.valueOf(i)));
////			ZapierPrimaryEmail p = g.fromJson(convertedToString, ZapierPrimaryEmail.class);
////			logger.info("List ->" + p);
//			zapierPrimaryEmail = g.fromJson(convertedToString, ZapierProductServiceDto.class);
//			
////			zapierPrimaryEmail = objMapper.convertValue(zapierQuote.getLine_items().get(String.valueOf(i)), ZapierProductServiceDto.class);
//			
//			String nameProduct = zapierPrimaryEmail.getName();
//			Optional<ProductService> filteredProAndService = productService.stream()
//					.filter(filterData -> filterData.getName().equalsIgnoreCase(nameProduct)).findAny();
//			if (filteredProAndService.isPresent()) {
//				QuotesProductAndService quotesProductAndService = new QuotesProductAndService();
//				quotesProductAndService.setIsDeleted(false);
//				quotesProductAndService.setIsOptional(false);
//				quotesProductAndService.setProductServiceId(filteredProAndService.get().getId());
//				quotesProductAndService
//						.setQuantity(zapierPrimaryEmail.getQty().intValue());
//				quotesProductAndService.setUnitCost(zapierPrimaryEmail.getUnit_cost());
//				quotesProductAndService.setTotalCost(zapierPrimaryEmail.getCost());
//				quotesProductAndService.setNotes(zapierPrimaryEmail.getDescription());
//				quotesProductAndService.setQuotesId(quoteNew.getId());
//				quotesProductAndService.setIsRecommended(false);
//				quotesProductAndServices.add(quotesProductAndService);
//			}
//		}
		return quotesProductAndServices;
	}

	public Quote addedQuoteTestPorpose(ZapierQuoteDto zapierQuote) {
		Quote quote = new Quote();
		quote.setQuoteNumber(zapierQuote.getQuote_number().toString());
		quote.setJobQuotesTitle(zapierQuote.getJob_description());
		quote.setUserId(Long.valueOf(102));
		quote.setUserCompanyId(Long.valueOf(0));
		quote.setCreatedBy(Long.valueOf(102));
		quote.setCreatedTs(Instant.now());
		quote.setIsDeposit(false);
		quote.setIs_deleted(false);
		quote.setStatus(zapierQuote.getState());
		quote.setWorkStatusId("1");
		return quote;
	}

}
