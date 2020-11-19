package com.checksammy.loca.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checksammy.loca.model.ApplicationVersion;
import com.checksammy.loca.repository.ApplicationVersionRepository;
import com.checksammy.loca.service.ApplicationVersionService;

@Service
public class ApplicationVersionServiceImpl implements ApplicationVersionService{

	@Autowired
	private ApplicationVersionRepository appRepository;

	@Override
	public List<ApplicationVersion> getList() {
		return appRepository.findAll();
	}
}
