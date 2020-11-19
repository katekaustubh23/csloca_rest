package com.checksammy.loca.repository;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.BinHistoryViewWeb;

@Repository
public interface BinHistoryViewWebRepository extends JpaRepository<BinHistoryViewWeb, Serializable>{

	@Query(value="select * from vweb_getallbinhistory order by last_modified_ts DESC", nativeQuery = true)
	List<BinHistoryViewWeb> getWebBinHistoryList();

	@Query(value="select * from vweb_getallbinhistory where last_modified_ts > DATE_SUB(now(), INTERVAL ?1 DAY) order by last_modified_ts DESC", nativeQuery = true)
	List<BinHistoryViewWeb> findBinTypeAndDate(String binType, Date selectedDate);

	BinHistoryViewWeb findByBinLocationId(Long binLocationId);

	@Query(value="SELECT * FROM vweb_getallbinhistory WHERE created_ts > DATE(?1) AND created_ts < ?2 AND bin_type_id =?3", nativeQuery = true)
	List<BinHistoryViewWeb> findByFormToDateAndBinType(Instant fromDate, Date endDate, Long binType);

}
