package com.checksammy.loca.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checksammy.loca.repository.QuotesCustomInstanceRepository;
import com.checksammy.loca.service.QuotesCustomInstanceService;

@Service
public class QuotesCustomInstanceServiceImpl implements QuotesCustomInstanceService{

	private static final Logger logger = LoggerFactory.getLogger(QuotesCustomInstanceServiceImpl.class);

	@Autowired
	private QuotesCustomInstanceRepository quotesCustomInstanceRepository;

	@Override
	public void deleteFromQuoteCustom(Long fieldInstanceId) {
		quotesCustomInstanceRepository.deleteByFieldInstanceId(fieldInstanceId);		
	}
}
