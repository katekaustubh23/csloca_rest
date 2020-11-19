/**
 * 
 */
package com.checksammy.loca.repository;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.BinDestination;

/**
 * @author Abhishek Srivastava
 *
 */
@Repository
public interface BinDestinationRepository extends JpaRepository<BinDestination, Serializable>{

	@Query(value="SELECT * FROM bin_destination WHERE id =?1 AND is_deleted = 0", nativeQuery=true)
	Optional<BinDestination> findByIdIsDeleted(Long binDestinationId);

}
