package com.checksammy.loca.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checksammy.loca.exception.ResourceNotFoundException;
import com.checksammy.loca.model.BinLocation;
import com.checksammy.loca.model.BinLocationCustomInstance;
import com.checksammy.loca.repository.BinLocationCustomInstanceRepository;
import com.checksammy.loca.repository.BinLocationRespository;
import com.checksammy.loca.service.BinLocationService;

@Service
public class BinLocationServiceImpl implements BinLocationService {

	@Autowired
	private BinLocationRespository binLocationRespository;
	
	@Autowired
	private BinLocationCustomInstanceRepository binCustomInstanceRepository;

	@Override
	public BinLocation save(BinLocation binLocation) {
		binLocation = binLocationRespository.save(binLocation);
		if(binLocation.getBinCustomField() != null && binLocation.getBinCustomField().size() > 0) {
			for (BinLocationCustomInstance binCustomField : binLocation.getBinCustomField()) {
				binCustomField.setBinLocationId(binLocation.getId());
				binCustomField = binCustomInstanceRepository.save(binCustomField);
			}
		}
		
		return binLocation;
	}

	@Override
	public String getBinPhotosByBinLocationId(Long binLocationId) throws ResourceNotFoundException {
		return binLocationRespository.getBinPhotosByBinLocationId(binLocationId);
	}

	@Override
	public void delete(Long binLocationId) {
		binLocationRespository.deleteById(binLocationId);
	}

	/**
	 * TODO: Rating functionality
	 */
//	@Override
//	public Boolean updateRating(Long binLocationId, Long rating) {
//		Boolean status;
//		try {
//			binLocationRespository.updateBinLocation(binLocationId, rating);
//			status = true;
//		} catch (Exception e) {
//			status = false;
//		}
//		return status;
//	}

//	@Override
//	public void updateAvgBinTypeRating(Long binTypeId) {
//		List<BinLocation> binLocations = binLocationRespository.
//	}

}
