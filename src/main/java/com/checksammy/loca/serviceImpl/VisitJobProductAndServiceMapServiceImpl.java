package com.checksammy.loca.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checksammy.loca.model.VisitJobProductAndServiceMap;
import com.checksammy.loca.repository.VisitJobProductAndServiceMapRepository;
import com.checksammy.loca.service.VisitJobProductAndServiceMapService;

@Service
public class VisitJobProductAndServiceMapServiceImpl implements VisitJobProductAndServiceMapService{

	@Autowired
	private VisitJobProductAndServiceMapRepository visitMapRepo;
	
	@Override
	public List<VisitJobProductAndServiceMap> saveMapping(List<VisitJobProductAndServiceMap> list) {
		return visitMapRepo.saveAll(list);
	}

	@Override
	public void deleteMapping(Long jobId, Long visitId) {
		visitMapRepo.deleteByJobIdAndVisitId(jobId, visitId);
	}

	@Override
	public List<VisitJobProductAndServiceMap> findByVisitId(Long visitId) {
		return visitMapRepo.findByVisitId(visitId);
	}

	@Override
	public void updateMarkFlag(Long visitId, Boolean checked) {
		visitMapRepo.updateStatus(visitId, checked);
	}

	@Override
	public void deleteByJobIdWithOutChecked(Long id) {
		visitMapRepo.deleteByJobIdWhereUnChecked(id);
	}


}
