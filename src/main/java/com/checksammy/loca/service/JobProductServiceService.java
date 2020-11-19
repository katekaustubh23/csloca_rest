package com.checksammy.loca.service;

import java.util.List;
import java.util.Set;

import com.checksammy.loca.dto.JobProductAndServiceUpdateDto;
import com.checksammy.loca.model.JobProductService;
import com.checksammy.loca.model.VisitJobProductAndServiceMap;

public interface JobProductServiceService {

	List<JobProductService> saveAndUpdateJObProAndService(JobProductAndServiceUpdateDto saveJobProAndSer);

	Boolean deleteById(Long productId);

	List<VisitJobProductAndServiceMap> saveAllDataWithVisitMapping(Set<JobProductService> jobProductService, Long id);

	List<JobProductService> findDataByVistiIds(Long id);

	List<JobProductService> findByVisitandJob(Long visitId);

	List<JobProductService> findByCompleteJObId(Long jobId);


}
