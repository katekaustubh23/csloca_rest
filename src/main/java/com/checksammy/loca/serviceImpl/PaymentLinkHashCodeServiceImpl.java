package com.checksammy.loca.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checksammy.loca.model.Invoice;
import com.checksammy.loca.model.PaymentLinkHashCode;
import com.checksammy.loca.repository.InvoiceRepository;
import com.checksammy.loca.repository.PaymentLinkHashCodeRepository;
import com.checksammy.loca.service.PaymentLinkHashCodeService;

@Service
public class PaymentLinkHashCodeServiceImpl implements PaymentLinkHashCodeService {

	@Autowired
	private PaymentLinkHashCodeRepository paymentLinkHCRepository;
	
	@Autowired
	private InvoiceRepository invocieRepository;

	@Override
	public PaymentLinkHashCode saveData(PaymentLinkHashCode paymentLinkHashCode) {
		return paymentLinkHCRepository.save(paymentLinkHashCode);
	}

	@Override
	public void completedPaymentOnInvoice(String hashCode) {
		paymentLinkHCRepository.updateDeleteTag(hashCode);
	}

	@Override
	public Invoice findInvoiceData(String hashCode) {
		PaymentLinkHashCode paymentLinkHashCode = paymentLinkHCRepository.findByHashCode(hashCode);
		if (paymentLinkHashCode != null) {
			Optional<Invoice> invoice = invocieRepository.findById(paymentLinkHashCode.getInvoiceId());
			return invoice.get();
		} else {
			return null;
		}

	}

	@Override
	public void deleteByAllSameParam(Long invoiceId, Long jobId, String hashCode) {
		paymentLinkHCRepository.deleteByAllSameParam(invoiceId, jobId, hashCode);
	}

	@Override
	public Integer findDataPresent(String hashCode) {
		Integer status = paymentLinkHCRepository.existsData(hashCode);
		return status;
	}

	@Override
	public void deleteByInvoiceAndJobId(Long invoiceId, Long jobId) {
		// TODO Auto-generated method stub
		paymentLinkHCRepository.deleteByInvoiceAndJobId(invoiceId, jobId);
	}
}
