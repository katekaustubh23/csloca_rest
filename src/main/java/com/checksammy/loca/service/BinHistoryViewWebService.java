package com.checksammy.loca.service;

import java.util.List;

import com.checksammy.loca.exception.ResourceNotFoundException;
import com.checksammy.loca.model.BinHistoryViewWeb;

public interface BinHistoryViewWebService {
	
	Long getTotalNoOfBins();

	BinHistoryViewWeb getDataByBinLocationId(Long binLocationId);

	List<BinHistoryViewWeb> getWebBinHistoryList() throws ResourceNotFoundException;

}
