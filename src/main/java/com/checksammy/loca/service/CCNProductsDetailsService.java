/**
 * 
 */
package com.checksammy.loca.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.checksammy.loca.model.CCNProductsDetails;

/**
 * @author Abhishek Srivastava
 *
 */
public interface CCNProductsDetailsService {
	
	Page<CCNProductsDetails> getAllPaginated(Pageable pageable);
	
	List<CCNProductsDetails> getAll();
	
	CCNProductsDetails save(CCNProductsDetails ccnProductsDetails);

	List<CCNProductsDetails> saveList(List<CCNProductsDetails> ccnProductsDetailList);

	void deleteAllList();

}
