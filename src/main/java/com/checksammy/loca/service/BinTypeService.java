/**
 * 
 */
package com.checksammy.loca.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.checksammy.loca.model.BinType;

/**
 * @author Abhishek Srivastava
 *
 */
public interface BinTypeService {

	Page<BinType> getAll(Pageable pageable);

	BinType save(BinType binType);

	void deleteById(Long id);

	List<BinType> getFindAll();

}
