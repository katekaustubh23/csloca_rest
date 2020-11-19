package com.checksammy.loca.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checksammy.loca.model.TransFlow;
import com.checksammy.loca.repository.TransFlowRepository;
import com.checksammy.loca.service.TransFlowService;

@Service
public class TransFlowServiceImpl implements TransFlowService{

	@Autowired
	private TransFlowRepository transFlowRepository;

	@Override
	public List<TransFlow> getAllList() {
		return transFlowRepository.findAll();
	}
}
