package com.checksammy.loca.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checksammy.loca.repository.LocationManagerRepository;
import com.checksammy.loca.service.LocationManagerService;

@Service
public class LocationManagerServiceImpl implements LocationManagerService{

	@Autowired
	private LocationManagerRepository locaManagerRepo;
}
