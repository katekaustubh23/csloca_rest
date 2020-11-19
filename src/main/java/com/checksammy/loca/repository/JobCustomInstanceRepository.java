package com.checksammy.loca.repository;

import java.io.Serializable;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.JobCustomInstance;

@Repository
public interface JobCustomInstanceRepository extends JpaRepository<JobCustomInstance, Serializable>{

	@Modifying
	@Transactional
	@Query(value="UPDATE job_custom_instance SET is_deleted = 1 WHERE field_instance_id =?1", nativeQuery = true)
	void updateIsDeletedTag(Long fieldInstanceId);

}
