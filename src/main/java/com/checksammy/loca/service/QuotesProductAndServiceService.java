package com.checksammy.loca.service;

import org.springframework.web.multipart.MultipartFile;

public interface QuotesProductAndServiceService {

	String saveRequestAttachments(Long QuoteId, Long quoteProductId2, MultipartFile[] files);

	String deleteById(Long quoProductId);

}
