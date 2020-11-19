package com.checksammy.loca.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.JobRequestCustomField;

@Repository
public interface JobRequestCustomFieldRepository extends JpaRepository<JobRequestCustomField, Long>{

	@Modifying
	@Transactional
	@Query(value="UPDATE request_custom_field SET request_id = ?1 WHERE id IN ?2", nativeQuery = true)
	void updateRequestCustomField(Long id, List<Long> reqCustomFieldIds);

	@Modifying
	@Transactional
	@Query(value="UPDATE request_custom_field SET is_deleted = 1 WHERE field_instance_id = ?1", nativeQuery = true)
	void deleteFromRequestCustom(Long fieldInstanceId);

}
