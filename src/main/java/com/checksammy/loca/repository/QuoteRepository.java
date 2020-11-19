package com.checksammy.loca.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.Quote;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long>{

	@Modifying
	@Transactional
	@Query(value="UPDATE req_quotes SET is_deleted=1 WHERE id=?1", nativeQuery = true)
	void deleteToUpdate(Long quotesId);

	@Modifying
	@Transactional
	@Query(value="UPDATE req_quotes SET work_status_id =?3, status =?2 WHERE id =?1", nativeQuery = true)
	void updateStatus(Long quotesId, String statusName, Long id);

	@Modifying
	@Transactional
	@Query(value="UPDATE req_quotes SET location_id =?2 WHERE id =?1", nativeQuery = true)
	void updateLocationId(Long id, Long locationId);

	@Modifying
	@Transactional
	@Query(value="UPDATE req_quotes SET link_note =?2 WHERE id =?1", nativeQuery = true)
	void updatelinkNote(Long quoteId, String linkNote);

	@Modifying
	@Transactional
	@Query(value="UPDATE req_quotes SET status =?2 WHERE id =?1", nativeQuery = true)
	void changeStatus(Long quoteID, String status);

}
