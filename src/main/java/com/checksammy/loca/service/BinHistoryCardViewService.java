/**
 * 
 */
package com.checksammy.loca.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.checksammy.loca.exception.ResourceNotFoundException;
import com.checksammy.loca.model.BinHistoryCardView;

/**
 * @author Abhishek Srivastava
 *
 */
public interface BinHistoryCardViewService {
	
	Page<BinHistoryCardView> getBinHistoryCardByUser(Pageable pageable, Long userId);
	Page<BinHistoryCardView> getBinHistoryCards(Pageable pageable, Long userId) throws ResourceNotFoundException;
	List<BinHistoryCardView> getBinHistoryCardsList() throws ResourceNotFoundException;

}
