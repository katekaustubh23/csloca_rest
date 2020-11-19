package com.checksammy.loca.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

	@Modifying
	@Transactional
	@Query(value = "update location set is_deleted = 1 where id=?1", nativeQuery = true)
	public void deleteLocationById(Long id);

	@Query(value = "SELECT * FROM location WHERE id IN (SELECT location_id FROM location_propertydetails WHERE property_detail_id =?1) AND is_deleted = 0", nativeQuery = true)
	public List<Location> finByCompanyId(Long companyId);

	public Location findByPropertyName(String centerName);

	@Query(value = "SELECT * FROM location WHERE is_deleted = 0 AND id IN "
			+ "(SELECT location_id FROM location_propertydetails WHERE property_detail_id IN "
			+ "(SELECT id FROM property_details WHERE is_deleted = 0 AND name IN "
			+ "(SELECT company_name FROM user WHERE id = ?1)))", nativeQuery = true)
	public List<Location> findByUserId(Long userId);

	@Query(value="SELECT * FROM location WHERE id IN "
			+ "(SELECT location_id FROM location_propertydetails WHERE property_detail_id IN"
			+ " (SELECT id FROM property_details WHERE name = ?1));", nativeQuery = true)
	public List<Location> findByCompanyNameOfUser(String company_name);

}
