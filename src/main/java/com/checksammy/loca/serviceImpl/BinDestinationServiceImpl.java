/**
 * 
 */
package com.checksammy.loca.serviceImpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.checksammy.loca.model.BinDestination;
import com.checksammy.loca.repository.BinDestinationRepository;
import com.checksammy.loca.service.BinDestinationService;

/**
 * @author Abhishek Srivastava
 *
 */
@Service
public class BinDestinationServiceImpl implements BinDestinationService {
	
	private static final Logger logger = LoggerFactory.getLogger(BinDestinationServiceImpl.class);
	
	@Autowired
	BinDestinationRepository repo;

	@Override
	public Page<BinDestination> getAllBinDestinations(Pageable pageable) {
		logger.debug("inside BinDestinationServiceImpl.getAllBinDestinations() Method : Get all bin destinations ");
		pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("name"));
		return repo.findAll(pageable);
	}

	@Override
	public BinDestination add(BinDestination binDestination) {
		return repo.save(binDestination);
	}

	@Override
	public void deleteById(Long id) {
		repo.deleteById(id);
		
	}

	@Override
	public List<BinDestination> getFindAll() {
		return repo.findAll();
	}

}
