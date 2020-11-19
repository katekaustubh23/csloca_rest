package com.checksammy.loca.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checksammy.loca.model.InvoiceTaxNumberDisplay;
import com.checksammy.loca.repository.InvoiceTaxNumberDisplayRepository;
import com.checksammy.loca.service.InvoiceTaxNumberDisplayService;

@Service
public class InvoiceTaxNumberDisplayServiceImpl implements InvoiceTaxNumberDisplayService{

	@Autowired
	private InvoiceTaxNumberDisplayRepository invDisplayRepository;

	@Override
	public List<InvoiceTaxNumberDisplay> getAll() {
		return invDisplayRepository.findAll();
	}

	@Override
	public InvoiceTaxNumberDisplay saveData(InvoiceTaxNumberDisplay invoiceTaxNumberDisplay) {
		return invDisplayRepository.save(invoiceTaxNumberDisplay);
	}
}
