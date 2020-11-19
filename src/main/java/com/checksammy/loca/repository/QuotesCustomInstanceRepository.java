package com.checksammy.loca.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.QuotesCustomInstance;

@Repository
public interface QuotesCustomInstanceRepository extends JpaRepository<QuotesCustomInstance, Long>{

	@Modifying
	@Transactional
	@Query(value="UPDATE quotes_custom_instance SET is_deleted = 1 WHERE field_instance_id =?1", nativeQuery = true)
	void deleteByFieldInstanceId(Long fieldInstanceId);

}
