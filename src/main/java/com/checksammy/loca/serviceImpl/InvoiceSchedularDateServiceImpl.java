package com.checksammy.loca.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checksammy.loca.model.InvoiceSchedularDate;
import com.checksammy.loca.repository.InvoiceSchedulerDateRepository;
import com.checksammy.loca.service.InvoiceSchedularDateService;

@Service
public class InvoiceSchedularDateServiceImpl implements InvoiceSchedularDateService {

	@Autowired
	private InvoiceSchedulerDateRepository invoiceSchedulerDateRepository;

	@Override
	public InvoiceSchedularDate saveData(InvoiceSchedularDate invoiceSchedularDate) {
		return invoiceSchedulerDateRepository.save(invoiceSchedularDate);
	}

	@Override
	public Optional<InvoiceSchedularDate> findById(Long remindId) {
		return invoiceSchedulerDateRepository.findById(remindId);
	}

	@Override
	public Boolean deleteReminder(Long reminderId) {
		Boolean status = true;
		try {
			invoiceSchedulerDateRepository.deleteById(reminderId);
		} catch (Exception e) {
			status = false;
		}
		return status;
	}

}
