package com.checksammy.loca.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.Location;
import com.checksammy.loca.model.LocationPropertyDetails;

@Repository
public interface LocationPropertyDetailRepository extends JpaRepository<LocationPropertyDetails, Long>{
	
	@Modifying
	@Transactional
	@Query(value="delete from location_propertydetails where location_id=?1", nativeQuery = true)
	public void deleteByLocationId(Long locationId);
	
	@Modifying
	@Transactional
	@Query(value="delete from location_propertydetails where property_detail_id=?1", nativeQuery = true)
	public void deleteByPropMgmtCompId(Long propMgmtCompId);

	@Query(value="SELECT * FROM location_propertydetails WHERE property_detail_id =?1", nativeQuery = true)
	public List<LocationPropertyDetails> findPropDetailId(Integer propertyManagement);

	@Query(value="SELECT * FROM location_propertydetails WHERE location_id=?1 AND property_detail_id =?2", nativeQuery = true)
	public LocationPropertyDetails getByLocationAndPropId(Long long1, Long companyId);

}
