package com.checksammy.loca.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checksammy.loca.model.WorkStatus;
import com.checksammy.loca.repository.WorkStatusRepository;
import com.checksammy.loca.service.WorkStatusService;

@Service
public class WorkStatusServiceImpl implements WorkStatusService{

	@Autowired
	private WorkStatusRepository workStatusRepository;

	@Override
	public List<WorkStatus> getAllWorkStatus() {
		return workStatusRepository.findAll();
	}

	@Override
	public Optional<WorkStatus> getDataById(Long statusId) {
		return workStatusRepository.findById(statusId);
	}
}
