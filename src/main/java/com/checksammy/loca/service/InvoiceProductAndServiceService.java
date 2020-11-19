package com.checksammy.loca.service;

import org.springframework.web.multipart.MultipartFile;

public interface InvoiceProductAndServiceService {

	String saveRequestAttachments(Long invoiceId, Long invoiceProductId, MultipartFile[] files);

	String deleteById(Long productId);

}
