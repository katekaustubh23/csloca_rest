package com.checksammy.loca.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checksammy.loca.model.VisitFrequencyJobMaster;
import com.checksammy.loca.repository.VisitFrequencyJobMasterRepository;
import com.checksammy.loca.service.VisitFrequencyJobMasterService;

@Service
public class VisitFrequencyJobMasterServiceImpl implements VisitFrequencyJobMasterService{

	@Autowired
	private VisitFrequencyJobMasterRepository visitFreqJobMasterRepo;

	@Override
	public List<VisitFrequencyJobMaster> getAllList() {
		return visitFreqJobMasterRepo.findAll();
	}
}
