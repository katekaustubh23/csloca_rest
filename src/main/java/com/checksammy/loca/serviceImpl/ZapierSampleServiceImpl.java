package com.checksammy.loca.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checksammy.loca.model.ZapierList;
import com.checksammy.loca.model.ZapierSample;
import com.checksammy.loca.repository.ZapierSampleRepository;
import com.checksammy.loca.repository.ZapierListRepository;
import com.checksammy.loca.service.ZapierSampleService;


@Service
public class ZapierSampleServiceImpl implements ZapierSampleService{

	@Autowired
	private ZapierSampleRepository zapierSampleRepository;
	
	@Autowired
	private ZapierListRepository zapierListRepository;

	@Override
	public ZapierSample saveData(ZapierSample zapierSample) {
		// TODO Auto-generated method stub
		return zapierSampleRepository.save(zapierSample);
	}

	@Override
	public ZapierList saveDate(ZapierList zapierList) {
		// TODO Auto-generated method stub
		return zapierListRepository.save(zapierList);
	}
}
