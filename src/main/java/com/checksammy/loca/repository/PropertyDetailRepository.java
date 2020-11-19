/**
 * 
 */
package com.checksammy.loca.repository;

import java.io.Serializable;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.Location;
import com.checksammy.loca.model.PropertyDetails;

/**
 * @author Abhishek Srivastava
 *
 */
@Repository
public interface PropertyDetailRepository extends JpaRepository<PropertyDetails, Serializable> {
	
	@Modifying
	@Transactional
	@Query(value="update property_details set is_deleted = 1 where id=?1", nativeQuery = true)
	public void deletePropertyDetailById(Long id);

	@Query(value="SELECT * FROM property_details WHERE id IN (SELECT property_detail_id FROM location_propertydetails WHERE location_id =?1)", nativeQuery = true)
	public PropertyDetails findByLocationId(Integer propertyManagement);

	public PropertyDetails findByName(String string);

//	public PropertyDetails findByLocationPropertyMapping(Location locationId);

}
