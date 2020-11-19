package com.checksammy.loca.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.UserFieldInstance;

@Repository
public interface CustomFieldInstanceRepository extends JpaRepository<UserFieldInstance, Long>{

	@Modifying
	@Transactional
	@Query(value="UPDATE custom_field_instance SET is_deleted = 1 WHERE field_instance_id =?1", nativeQuery = true)
	void deleteUserFieldByFTI(Long fieldInstanceId);


}
