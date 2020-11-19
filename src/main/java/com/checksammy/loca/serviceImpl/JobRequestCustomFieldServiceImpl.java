package com.checksammy.loca.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checksammy.loca.repository.JobRequestCustomFieldRepository;
import com.checksammy.loca.service.JobRequestCustomFieldService;

@Service
public class JobRequestCustomFieldServiceImpl implements JobRequestCustomFieldService{

	@Autowired
	JobRequestCustomFieldRepository requestCustomFieldRepository;

	@Override
	public void deleteFromRequestCustom(Long fieldInstanceId) {
		requestCustomFieldRepository.deleteFromRequestCustom(fieldInstanceId);
	}
}
