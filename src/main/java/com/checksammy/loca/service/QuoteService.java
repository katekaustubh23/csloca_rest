package com.checksammy.loca.service;

import java.util.HashMap;
import java.util.List;

import com.checksammy.loca.dto.QuoteDto;
import com.checksammy.loca.dto.QuoteSendDto;
import com.checksammy.loca.dto.ZapierQuoteDto;
import com.checksammy.loca.model.Quote;

public interface QuoteService {

	List<QuoteSendDto> getAll();

	Quote saveData(QuoteDto quote);

	QuoteSendDto getById(Long quotesId);

	Boolean deleteById(Long quotesId);

	String changeStatus(Long quotesId, Long statusId);

	Quote saveDataZapier(List<HashMap<String, String>> zapierQuote);

	Quote saveDataZapierTestLimitedData(ZapierQuoteDto zapierQuote);

}
