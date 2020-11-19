package com.checksammy.loca.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.TaxGroupMapping;

@Repository
public interface TaxGroupMappingRepository extends JpaRepository<TaxGroupMapping, Long>{

	@Modifying
	@Transactional
	@Query(value="DELETE FROM tax_group_mapping WHERE tax_group_id =?1", nativeQuery = true)
	void deletedByTaxGroupId(Long id);

}
