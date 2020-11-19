package com.checksammy.loca.service;

import java.util.List;

import com.checksammy.loca.model.CsServicesRequest;
import com.checksammy.loca.model.JobRequest;

public interface CsServicesRequestService {

	void deleteData(JobRequest newJobRequest);

	void saveData(List<CsServicesRequest> newServiceList);

}
