package com.checksammy.loca.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checksammy.loca.model.CsServicesRequest;
import com.checksammy.loca.model.JobRequest;
import com.checksammy.loca.repository.CsServicesRequestRepository;
import com.checksammy.loca.service.CsServicesRequestService;

@Service
public class CsServicesRequestServiceImpl implements CsServicesRequestService{

	@Autowired
	private CsServicesRequestRepository csServicesRequestRepository;

	@Override
	public void deleteData(JobRequest newJobRequest) {
		csServicesRequestRepository.deleteByRequestId(newJobRequest.getId());
	}

	@Override
	public void saveData(List<CsServicesRequest> newServiceList) {
		csServicesRequestRepository.saveAll(newServiceList);
	}
}
