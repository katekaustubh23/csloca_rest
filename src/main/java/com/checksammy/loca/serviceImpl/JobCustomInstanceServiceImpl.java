package com.checksammy.loca.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checksammy.loca.repository.JobCustomInstanceRepository;
import com.checksammy.loca.service.JobCustomInstanceService;

@Service
public class JobCustomInstanceServiceImpl implements JobCustomInstanceService{

	@Autowired
	private JobCustomInstanceRepository jobCustomInstanceRepository;

	@Override
	public void deleteByFieldInstanceId(Long fieldInstanceId) {
		jobCustomInstanceRepository.updateIsDeletedTag(fieldInstanceId);		
	}
}
