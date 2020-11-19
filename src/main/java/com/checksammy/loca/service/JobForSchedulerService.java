package com.checksammy.loca.service;

import java.util.List;
import java.util.Optional;

import com.checksammy.loca.dto.JobForSchedulerDto;
import com.checksammy.loca.model.JobForScheduler;

public interface JobForSchedulerService {

	Optional<JobForScheduler> findById(Long jobId);

	List<JobForSchedulerDto> getJobHistoryList(Long driverId);

}
