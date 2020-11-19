package com.checksammy.loca.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checksammy.loca.model.TaxGroupMapping;
import com.checksammy.loca.repository.TaxGroupMappingRepository;
import com.checksammy.loca.service.TaxGroupMappingService;

@Service
public class TaxGroupMappingServiceImpl implements TaxGroupMappingService{

	@Autowired
	TaxGroupMappingRepository taxGroupMappingRepository;

	@Override
	public void deleteMappingByTaxGrpId(Long id) {
		taxGroupMappingRepository.deletedByTaxGroupId(id);
	}

	@Override
	public TaxGroupMapping saveMapping(TaxGroupMapping taxGroupMapping) {
		return taxGroupMappingRepository.save(taxGroupMapping);
	}
}
