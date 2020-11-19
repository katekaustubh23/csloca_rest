/**
 * 
 */
package com.checksammy.loca.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.checksammy.loca.model.CCNProductsDetails;
import com.checksammy.loca.repository.CCNProductsDetailsRepository;
import com.checksammy.loca.service.CCNProductsDetailsService;

/**
 * @author Abhishek Srivastava
 *
 */
@Service
public class CCNProductsDetailsServiceImpl implements CCNProductsDetailsService{
	
	@Autowired
	CCNProductsDetailsRepository repo;

	@Override
	public Page<CCNProductsDetails> getAllPaginated(Pageable pageable) {
		return repo.findAll(pageable);
	}

	@Override
	public List<CCNProductsDetails> getAll() {
		return repo.findAll();
	}

	@Override
	public CCNProductsDetails save(CCNProductsDetails ccnProductsDetails) {
		return repo.save(ccnProductsDetails);
	}

	@Override
	public List<CCNProductsDetails> saveList(List<CCNProductsDetails> ccnProductsDetailList) {
		return repo.saveAll(ccnProductsDetailList);
	}

	@Override
	public void deleteAllList() {
		repo.deleteAll();
	}

}
