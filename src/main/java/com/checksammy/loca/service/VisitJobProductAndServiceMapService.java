package com.checksammy.loca.service;

import java.util.List;

import com.checksammy.loca.model.VisitJobProductAndServiceMap;

public interface VisitJobProductAndServiceMapService {

	List<VisitJobProductAndServiceMap> saveMapping(List<VisitJobProductAndServiceMap> list);

	void deleteMapping(Long jobId, Long visitId);

	List<VisitJobProductAndServiceMap> findByVisitId(Long visitId);

	void updateMarkFlag(Long jobScheduleId, Boolean checked);

	void deleteByJobIdWithOutChecked(Long id);


}
