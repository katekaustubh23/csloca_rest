/**
 * 
 */
package com.checksammy.loca.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.checksammy.loca.exception.ResourceNotFoundException;
import com.checksammy.loca.model.BinHistoryCardView;
import com.checksammy.loca.repository.BinHistoryCardViewRepository;
import com.checksammy.loca.service.BinHistoryCardViewService;
import com.checksammy.loca.service.ConfigurationSettingsService;

/**
 * @author Abhishek Srivastava
 *
 */
@Service
public class BinHistoryCardViewServiceImpl implements BinHistoryCardViewService {
	
	@Autowired
	BinHistoryCardViewRepository repo;
	
	@Autowired
	ConfigurationSettingsService configSettingsService;

	@Override
	public Page<BinHistoryCardView> getBinHistoryCardByUser(Pageable pageable, Long userId) {
		//return repo.getBinHistoryCardByUser(userId);
		BinHistoryCardView binHistoryCardView = new BinHistoryCardView();
		binHistoryCardView.setDriverId(userId);
		
		Example<BinHistoryCardView> example = Example.of(binHistoryCardView);
		return repo.findAll(example, pageable);
	}

	@Override
	public Page<BinHistoryCardView> getBinHistoryCards(Pageable pageable, Long userId) throws ResourceNotFoundException {
		
		//how old data needs to be shown to each driver
		String noOfDays = "";
		noOfDays = configSettingsService.getValueByKey("driver.binhistory.days");
		if(noOfDays.isBlank())
			noOfDays = "30";
		final Page<BinHistoryCardView> page = new PageImpl<>(repo.getBinHistoryCards(userId, pageable, Long.parseLong(noOfDays)));
		
		return page;
	}

	@Override
	public List<BinHistoryCardView> getBinHistoryCardsList() throws ResourceNotFoundException {
		//how old data needs to be shown to each driver
				String noOfDays = "";
				noOfDays = configSettingsService.getValueByKey("driver.binhistory.days");
				if(noOfDays.isBlank())
					noOfDays = "30";
				final List<BinHistoryCardView> page = repo.getBinHistoryCardsList(Long.parseLong(noOfDays));
				
				return page;
	}

}
