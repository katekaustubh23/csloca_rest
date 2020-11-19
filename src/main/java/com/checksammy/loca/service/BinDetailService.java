package com.checksammy.loca.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.Instant;
import java.util.List;

import javax.validation.Valid;

import org.springframework.core.io.Resource;

import com.checksammy.loca.exception.ResourceNotFoundException;
import com.checksammy.loca.model.BinDetail;
import com.checksammy.loca.model.BinDetailsView;

public interface BinDetailService 
{

	BinDetail save(@Valid BinDetail bindetail);
	
	BinDetail findById(Long id) throws ResourceNotFoundException;

	List<BinDetail> showList();

	void removeById(Long id, Instant creationTS) throws ResourceNotFoundException;

	List<BinDetail> saveList(List<BinDetail> binDetails);
	
	List<BinDetailsView> getBinDetailView(Long binLocationId) throws ResourceNotFoundException;
	
	List<Resource> getBinPhotos(Long binLocationId) throws IOException, MalformedURLException;

	List<BinDetail> getBinDetailsByLocationId(Long binLocationId);

	void deleteAllByBinLocationId(List<BinDetail> binDetails);
	
}
