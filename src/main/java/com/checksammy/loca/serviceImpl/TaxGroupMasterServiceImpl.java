package com.checksammy.loca.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checksammy.loca.model.TaxGroupMaster;
import com.checksammy.loca.repository.TaxGroupMasterRepository;
import com.checksammy.loca.service.TaxGroupMasterService;

@Service
public class TaxGroupMasterServiceImpl implements TaxGroupMasterService{
	
	@Autowired
	private TaxGroupMasterRepository taxGroupMasterRepository;

	@Override
	public List<TaxGroupMaster> getAllTaxGroup() {
		return taxGroupMasterRepository.findAll();
	}

	@Override
	public TaxGroupMaster saveData(TaxGroupMaster taxGrpMaster) {
		return taxGroupMasterRepository.save(taxGrpMaster);
	}

}
