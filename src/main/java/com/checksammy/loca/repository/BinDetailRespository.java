package com.checksammy.loca.repository;

import java.time.Instant;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.BinDetail;

@Repository
public interface BinDetailRespository extends JpaRepository<BinDetail, Long>
{

	@Modifying
	@Transactional
	@Query(value="update bin_detail set is_deleted = 1 where bin_location_id = ?1 and created_ts = ?2", nativeQuery=true)
	public void deleteBinDetailByBinLocationId(Long binLocationId, Instant creationTS);

	@Query(value="SELECT * FROM bin_detail WHERE bin_location_id =?1 ", nativeQuery=true)
	public List<BinDetail> findByLocationId(Long binLocationId);
	
}
