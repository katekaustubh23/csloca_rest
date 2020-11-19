/**
 * 
 */
package com.checksammy.loca.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.BinHistoryCardView;

/**
 * @author Abhishek Srivastava
 *
 */
@Repository
public interface BinHistoryCardViewRepository extends JpaRepository<BinHistoryCardView, Serializable>{

	@Query(value="select * from vgetbinhistorycards where created_by=?1 and last_modified_ts > DATE_SUB(now(), INTERVAL ?2 DAY) order by last_modified_ts DESC", nativeQuery = true)
	List<BinHistoryCardView> getBinHistoryCards(Long userId, Pageable pageable, Long noOfDays);

	@Query(value="select * from vgetbinhistorycards where last_modified_ts > DATE_SUB(now(), INTERVAL ?1 DAY) order by last_modified_ts DESC", nativeQuery = true)
	List<BinHistoryCardView> getBinHistoryCardsList(Long noOfDays);
}
