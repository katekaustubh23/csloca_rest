/**
 * 
 */
package com.checksammy.loca.repository;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.BinContent;

/**
 * @author Abhishek Srivastava
 *
 */
@Repository
public interface BinContentRepository extends JpaRepository<BinContent, Serializable> {	

	@Modifying
	@Transactional
	@Query(value="delete from bin_content where id=?1 and bin_type_id = ?2", nativeQuery = true)
	void deleteByIdAndBinTypeId(Long id, Long binTypeId);

	@Query(value = "SELECT * FROM bin_content WHERE bin_type_id = ?1 AND is_deleted = 0", nativeQuery = true)
	List<BinContent> getListByBinTypeId(Long integer);
}
