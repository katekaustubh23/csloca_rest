package com.checksammy.loca.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.CompanyCustomField;

@Repository
public interface CompanyCustomFieldRepository extends JpaRepository<CompanyCustomField, Long>{

	@Modifying
	@Transactional
	@Query(value="UPDATE company_custom_field SET company_id = ?1 WHERE id IN ?2 AND is_deleted = 0", nativeQuery = true)
	void updateRequestCustomField(Long id, List<Long> companyCustomFieldId);

	@Modifying
	@Transactional
	@Query(value="UPDATE company_custom_field SET is_deleted = 1 WHERE field_instance_id =?1", nativeQuery = true)
	void deleteByFieldInstanceId(Long fieldInstanceId);

}
