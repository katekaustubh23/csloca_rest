package com.checksammy.loca.service;

import com.checksammy.loca.model.Invoice;
import com.checksammy.loca.model.PaymentLinkHashCode;

public interface PaymentLinkHashCodeService {

	PaymentLinkHashCode saveData(PaymentLinkHashCode paymentLinkHashCode);

	void completedPaymentOnInvoice(String hashCode);

	Invoice findInvoiceData(String hashCode);

	void deleteByAllSameParam(Long invoiceId, Long jobId, String string);

	Integer findDataPresent(String hashCode);

	void deleteByInvoiceAndJobId(Long id, Long jobId);

}
