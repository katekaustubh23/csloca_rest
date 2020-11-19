/**
 * 
 */
package com.checksammy.loca.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.checksammy.loca.model.BinType;
import com.checksammy.loca.repository.BinTypeRespository;
import com.checksammy.loca.service.BinTypeService;

/**
 * @author Abhishek Srivastava
 *
 */
@Service
public class BinTypeServiceImpl implements BinTypeService {

	@Autowired
	BinTypeRespository repo;
	
	@Override
	public Page<BinType> getAll(Pageable pageable) {
		pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("name"));
		return repo.findAll(pageable);
	}

	@Override
	public BinType save(BinType binType) {
		
		return repo.save(binType);
	}

	@Override
	public void deleteById(Long id) {
		repo.deleteById(id);

	}

	@Override
	public List<BinType> getFindAll() {
		return repo.findAll();
	}

}
