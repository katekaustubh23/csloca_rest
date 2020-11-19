/**
 * 
 */
package com.checksammy.loca.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.checksammy.loca.model.BinContent;

/**
 * @author Abhishek Srivastava
 *
 */
public interface BinContentService {
	
	Page<BinContent> getAll(Pageable pageable);
	
	BinContent save(BinContent binContents);
	
	List<BinContent> saveAll(List<BinContent> binContents);
	
	//void deleteById(Long id);
	
	void deleteByIdAndBinTypeId(Long id, Long binTypeId);

}
