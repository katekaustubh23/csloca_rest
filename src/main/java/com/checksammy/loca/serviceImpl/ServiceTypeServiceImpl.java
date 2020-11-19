package com.checksammy.loca.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checksammy.loca.model.ServiceType;
import com.checksammy.loca.repository.ServiceTypeRepository;
import com.checksammy.loca.service.ServiceTypeService;

@Service
public class ServiceTypeServiceImpl implements ServiceTypeService{

	@Autowired
	private ServiceTypeRepository serviceTypeRepository;

	@Override
	public List<ServiceType> getAll() {
		return serviceTypeRepository.findAll();
	}
}
