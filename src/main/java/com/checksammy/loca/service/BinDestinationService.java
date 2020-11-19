/**
 * 
 */
package com.checksammy.loca.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.checksammy.loca.model.BinDestination;

/**
 * @author Abhishek Srivastava
 *
 */
public interface BinDestinationService {
	
	public Page<BinDestination> getAllBinDestinations(Pageable pageable);
	
	public BinDestination add(BinDestination binDestination);
	
	public void deleteById(Long id);

	public List<BinDestination> getFindAll();
	
	
}
