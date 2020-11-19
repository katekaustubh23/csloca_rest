package com.checksammy.loca.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checksammy.loca.repository.InvoiceCustomInstanceRepository;
import com.checksammy.loca.service.InvoiceCustomInstanceService;

@Service
public class InvoiceCustomInstanceServiceImpl implements InvoiceCustomInstanceService{
	
	@Autowired
	private InvoiceCustomInstanceRepository instanceRepository;

	@Override
	public void deleteByFieldInstanceId(Long fieldInstanceId) {
		instanceRepository.deletedByFieldInstanceId(fieldInstanceId);
	}

}
