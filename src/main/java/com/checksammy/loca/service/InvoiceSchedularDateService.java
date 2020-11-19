package com.checksammy.loca.service;

import java.util.Optional;

import com.checksammy.loca.model.InvoiceSchedularDate;

public interface InvoiceSchedularDateService {

	InvoiceSchedularDate saveData(InvoiceSchedularDate invoiceSchedularDate);

	Optional<InvoiceSchedularDate> findById(Long remindId);

	Boolean deleteReminder(Long reminderId);

}
