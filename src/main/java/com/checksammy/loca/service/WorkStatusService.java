package com.checksammy.loca.service;

import java.util.List;
import java.util.Optional;

import com.checksammy.loca.model.WorkStatus;

public interface WorkStatusService {

	List<WorkStatus> getAllWorkStatus();

	Optional<WorkStatus> getDataById(Long statusId);

}
