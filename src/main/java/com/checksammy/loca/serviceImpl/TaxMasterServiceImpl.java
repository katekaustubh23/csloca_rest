package com.checksammy.loca.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checksammy.loca.model.TaxMaster;
import com.checksammy.loca.repository.TaxMasterRepository;
import com.checksammy.loca.service.TaxMasterService;

@Service
public class TaxMasterServiceImpl implements TaxMasterService{
	
	private static final Logger logger = LoggerFactory.getLogger(TaxMasterServiceImpl.class);
	
	@Autowired
	private TaxMasterRepository taxMasterRepository;

	@Override
	public List<TaxMaster> getAll() {
		return taxMasterRepository.findAll();
	}

	@Override
	public List<TaxMaster> saveData(List<TaxMaster> taxMaster2) {
		return taxMasterRepository.saveAll(taxMaster2);
	}

	@Override
	public Optional<TaxMaster> getSingleTax(Long taxId) {
		logger.debug("Single data");
		return taxMasterRepository.findById(taxId);
	}

	@Override
	public void deleteTax(Long taxId) {
		logger.debug("Single data");
		taxMasterRepository.deleteById(taxId);
	}

}
