package com.checksammy.loca.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checksammy.loca.exception.ResourceNotFoundException;
import com.checksammy.loca.model.BinHistoryViewWeb;
import com.checksammy.loca.repository.BinHistoryViewWebRepository;
import com.checksammy.loca.service.BinHistoryViewWebService;
import com.checksammy.loca.service.ConfigurationSettingsService;

@Service
public class BinHistoryViewWebServiceImpl implements BinHistoryViewWebService{

	@Autowired
	private BinHistoryViewWebRepository binHistoryViewWebRepository;
	
	@Autowired
	ConfigurationSettingsService configSettingsService;

	@Override
	public List<BinHistoryViewWeb> getWebBinHistoryList() throws ResourceNotFoundException {
		final List<BinHistoryViewWeb> page = binHistoryViewWebRepository.getWebBinHistoryList();
		return page;
	}

	@Override
	public Long getTotalNoOfBins() {
		return binHistoryViewWebRepository.count();
	}

	@Override
	public BinHistoryViewWeb getDataByBinLocationId(Long binLocationId) {
		return binHistoryViewWebRepository.findByBinLocationId(binLocationId);
	}
}
