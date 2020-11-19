package com.checksammy.loca.service;

import java.util.List;

import com.checksammy.loca.model.InvoiceTaxNumberDisplay;

public interface InvoiceTaxNumberDisplayService {

	List<InvoiceTaxNumberDisplay> getAll();

	InvoiceTaxNumberDisplay saveData(InvoiceTaxNumberDisplay invoiceTaxNumberDisplay);

}
