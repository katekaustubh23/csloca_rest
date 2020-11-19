package com.checksammy.loca.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checksammy.loca.repository.JobDriverMappingRepository;
import com.checksammy.loca.service.JobDriverMappingService;

@Service
public class JobDriverMappingServiceImpl implements JobDriverMappingService{
	
	@Autowired
	private JobDriverMappingRepository jobDriverMappingRepository;

}
