package com.checksammy.loca.service;

import com.checksammy.loca.exception.ResourceNotFoundException;
import com.checksammy.loca.model.BinLocation;

public interface BinLocationService {

	BinLocation save(BinLocation binLocation);
	
	String getBinPhotosByBinLocationId(Long binLocationId) throws ResourceNotFoundException;

	void delete(Long binLocationId);

//	Boolean updateRating(Long binLocationId, Long rating);
//
//	void updateAvgBinTypeRating(Long binTypeId);

}
