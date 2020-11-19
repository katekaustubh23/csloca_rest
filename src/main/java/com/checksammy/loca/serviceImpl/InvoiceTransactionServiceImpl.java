package com.checksammy.loca.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checksammy.loca.model.InvoiceTransaction;
import com.checksammy.loca.repository.InvoiceTransactionRepository;
import com.checksammy.loca.service.InvoiceTransactionService;

@Service
public class InvoiceTransactionServiceImpl implements InvoiceTransactionService{

	@Autowired
	private InvoiceTransactionRepository invoiceTransRepository;

	@Override
	public InvoiceTransaction saveTransaction(InvoiceTransaction invoiceTransaction) {
		return invoiceTransRepository.save(invoiceTransaction);
	}
}
