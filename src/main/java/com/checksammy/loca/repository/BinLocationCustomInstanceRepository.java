package com.checksammy.loca.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.BinLocationCustomInstance;

@Repository
public interface BinLocationCustomInstanceRepository extends JpaRepository<BinLocationCustomInstance, Long>{

	List<BinLocationCustomInstance> findByBinLocationId(Long binLocationId);

	@Modifying
	@Transactional
	@Query(value="UPDATE bin_location_custom_field_instance SET is_deleted = 1 WHERE field_instance_id =?1", nativeQuery = true)
	void updateDelete(Long fieldInstanceId);

}
