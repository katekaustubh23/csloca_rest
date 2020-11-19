package com.checksammy.loca.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.LocationManager;

@Repository
public interface LocationManagerRepository extends JpaRepository<LocationManager, Long>{

	@Modifying
	@Transactional
	@Query(value="UPDATE location_manager_maintenance_details SET location_id = ?2 WHERE id =?1", nativeQuery = true)
	void updateLocationId(Long locationManagerId, Long locationId);

}
