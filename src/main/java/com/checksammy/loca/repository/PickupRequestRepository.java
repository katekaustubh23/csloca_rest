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

import com.checksammy.loca.model.PickupRequest;

/**
 * @author Abhishek Srivastava
 *
 */
@Repository
public interface PickupRequestRepository extends JpaRepository<PickupRequest, Serializable>{
	
	@Modifying
	@Transactional
	@Query(value="update pickup_request set is_deleted = 1 where id=?1", nativeQuery = true)
	public void deleteByPickupReqestId(Long id);

}
