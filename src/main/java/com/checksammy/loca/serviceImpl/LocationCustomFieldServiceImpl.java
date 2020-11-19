package com.checksammy.loca.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checksammy.loca.repository.LocationCustomFieldRepository;
import com.checksammy.loca.service.LocationCustomFieldService;

@Service
public class LocationCustomFieldServiceImpl implements LocationCustomFieldService{

	@Autowired
	private LocationCustomFieldRepository locaCustomFieldRepository;

	@Override
	public void deleteFromLocationCustom(Long fieldInstanceId) {
		locaCustomFieldRepository.deleteByFieldInstanceId(fieldInstanceId);	
	}
}
