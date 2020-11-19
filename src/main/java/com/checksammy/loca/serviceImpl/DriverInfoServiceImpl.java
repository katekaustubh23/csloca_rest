package com.checksammy.loca.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checksammy.loca.model.DriverInfo;
import com.checksammy.loca.repository.DriverInfoRepository;
import com.checksammy.loca.service.DriverInfoService;

@Service
public class DriverInfoServiceImpl implements DriverInfoService{

	@Autowired
	private DriverInfoRepository driverInfoRepository;

	@Override
	public DriverInfo save(DriverInfo driverInfo) {
		return driverInfoRepository.save(driverInfo);
	}
}
