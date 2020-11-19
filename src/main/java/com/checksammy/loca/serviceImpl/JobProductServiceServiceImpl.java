package com.checksammy.loca.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checksammy.loca.dto.JobProductAndServiceUpdateDto;
import com.checksammy.loca.model.JobProductService;
import com.checksammy.loca.model.VisitJobProductAndServiceMap;
import com.checksammy.loca.repository.JobProductServiceRepository;
import com.checksammy.loca.repository.JobRepository;
import com.checksammy.loca.service.JobProductServiceService;
import com.checksammy.loca.service.VisitJobProductAndServiceMapService;

@Service
public class JobProductServiceServiceImpl implements JobProductServiceService {
	private static final Logger logger = LoggerFactory.getLogger(JobProductServiceServiceImpl.class);
	@Autowired
	private JobProductServiceRepository jobProductServiceRepository;

	@Autowired
	private VisitJobProductAndServiceMapService visitJobProductAndServiceMapService;

	@Autowired
	private JobRepository jobRepository;

	@Override
	public List<JobProductService> saveAndUpdateJObProAndService(JobProductAndServiceUpdateDto saveJobProAndSer) {
		List<JobProductService> jobProductServices = new ArrayList<JobProductService>();
		jobProductServices = jobProductServiceRepository.saveAll(saveJobProAndSer.getJobProductAndService());
		jobRepository.updateJobFinalTotal(saveJobProAndSer.getJobId(), saveJobProAndSer.getFinalTotal());
		return jobProductServices;
	}

	@Override
	public Boolean deleteById(Long productId) {
		Boolean status = false;
		try {
			jobProductServiceRepository.deleteToUpdate(productId);
			status = true;
		} catch (Exception e) {
			logger.debug(e.getCause().getMessage());
			status = false;
		}
		return status;
	}

	@Override
	public List<VisitJobProductAndServiceMap> saveAllDataWithVisitMapping(Set<JobProductService> jobProductService,
			Long visitId) {
		List<JobProductService> jobProductServices = jobProductServiceRepository.saveAll(jobProductService);
		List<VisitJobProductAndServiceMap> list = new ArrayList<VisitJobProductAndServiceMap>();
		for (JobProductService jobProductService2 : jobProductServices) {
			visitJobProductAndServiceMapService.deleteMapping(jobProductService2.getJobId(), visitId);
			VisitJobProductAndServiceMap visitJobProductAndServiceMap = new VisitJobProductAndServiceMap();
			visitJobProductAndServiceMap.setJobId(jobProductService2.getJobId());
			visitJobProductAndServiceMap.setJobProductId(jobProductService2.getId());
			visitJobProductAndServiceMap.setVisitId(visitId);
			visitJobProductAndServiceMap.setQty(jobProductService2.getQuantity().doubleValue());
			visitJobProductAndServiceMap.setUnitCost(jobProductService2.getUnitCost());
			visitJobProductAndServiceMap.setFinalCost(jobProductService2.getTotalCost());

			list.add(visitJobProductAndServiceMap);
		}
		list = visitJobProductAndServiceMapService.saveMapping(list);
		return list;
	}

	@Override
	public List<JobProductService> findDataByVistiIds(Long visitId) {
		List<JobProductService> jobProductServices = new ArrayList<JobProductService>();
		List<VisitJobProductAndServiceMap> productServices = visitJobProductAndServiceMapService.findByVisitId(visitId);
		for (VisitJobProductAndServiceMap visitJobProductAndServiceMap : productServices) {
			JobProductService newJobProductService = new JobProductService();
			Optional<JobProductService> jobProductService = jobProductServiceRepository
					.findById(visitJobProductAndServiceMap.getJobProductId());
			if (jobProductService.isPresent()) {
				newJobProductService.setProductServiceId(jobProductService.get().getProductServiceId());
				newJobProductService.setJobId(jobProductService.get().getJobId());
				newJobProductService.setNotes(jobProductService.get().getNotes());
				newJobProductService.setId(jobProductService.get().getId());
				newJobProductService.setPicture(jobProductService.get().getPicture());
				newJobProductService.setQuantity(visitJobProductAndServiceMap.getQty().intValue());
				newJobProductService.setUnitCost(visitJobProductAndServiceMap.getUnitCost());
				newJobProductService.setTotalCost(visitJobProductAndServiceMap.getFinalCost());
//				newJobProductService = jobProductService.get();
				jobProductServices.add(newJobProductService);
			}
		}
		return jobProductServices;
	}

	@Override
	public List<JobProductService> findByVisitandJob(Long visitId) {
		return jobProductServiceRepository.findByVisitByJob(visitId);
	}

	@Override
	public List<JobProductService> findByCompleteJObId(Long jobId) {
		return jobProductServiceRepository.findByJobIdCompleted(jobId);
	}
}
