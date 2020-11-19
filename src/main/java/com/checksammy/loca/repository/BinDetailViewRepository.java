/**
 * 
 */
package com.checksammy.loca.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.BinDetailsView;

/**
 * @author Abhishek Srivastava
 *
 */
@Repository
public interface BinDetailViewRepository extends JpaRepository<BinDetailsView, Serializable>{
	
	@Query(value="SELECT * FROM vgetbindetails WHERE bin_location_id = ?1", nativeQuery = true)
	List<BinDetailsView> getBinDetailsFromViewByBinLocationId(Long binLocationId);

}
