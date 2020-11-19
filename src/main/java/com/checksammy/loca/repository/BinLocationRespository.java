package com.checksammy.loca.repository;

import java.time.Instant;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.BinLocation;

@Repository
public interface BinLocationRespository extends JpaRepository<BinLocation, Long>
{
	
	@Query(value="SELECT attachments FROM bin_location WHERE id = ?1 and is_deleted = 0", nativeQuery = true)
	public String getBinPhotosByBinLocationId(Long binLocationId);

	@Modifying
	@Transactional
	@Query(value="update bin_location set is_deleted = 1 where id = ?1 and created_ts = ?2", nativeQuery=true)
	public void deleteBinLocationById(Long id, Instant creationTS);

	@Modifying
	@Transactional
	@Query(value="UPDATE bin_location SET rating = ?2 WHERE id = ?1 ", nativeQuery=true)
	public void updateBinLocation(Long binLocationId, Long rating);
}
