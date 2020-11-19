/**
 * 
 */
package com.checksammy.loca.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.checksammy.loca.model.BinContent;
import com.checksammy.loca.repository.BinContentRepository;
import com.checksammy.loca.service.BinContentService;

/**
 * @author Abhishek Srivastava
 *
 */
@Service
public class BinContentServiceImpl implements BinContentService {
	
	@Autowired
	BinContentRepository repo;

	@Override
	public Page<BinContent> getAll(Pageable pageable) {
		
		return repo.findAll(pageable);
	}

	@Override
	public BinContent save(BinContent binContent) {
		
		return repo.save(binContent);
	}
	
	@Override
	public List<BinContent> saveAll(List<BinContent> binContents) {
		
		return repo.saveAll(binContents);
	}

	@Override
	public void deleteByIdAndBinTypeId(Long id, Long binTypeId){
		repo.deleteByIdAndBinTypeId(id, binTypeId);

	}

}
