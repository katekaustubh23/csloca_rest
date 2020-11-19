package com.checksammy.loca.repository;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.JobDriverMapping;

@Repository
public interface JobDriverMappingRepository extends JpaRepository<JobDriverMapping, Serializable>{

	@Modifying
	@Transactional
	@Query(value="DELETE FROM job_driver_mapping WHERE job_id =?1", nativeQuery = true)
	void deleteByJobId(Long id);

	@Modifying
	@Transactional
	@Query(value="DELETE FROM job_driver_mapping WHERE job_id =?1 AND driver_id =?2", nativeQuery = true)
	void deleteByJobAndDriverId(Long id, Long driverId);

	@Modifying
	@Transactional
	@Query(value="UPDATE job_driver_mapping SET status =?3 WHERE job_id =?1 AND driver_id =?2", nativeQuery = true)
	void updateUserActionStatus(Long id, Long driverId, String accept);

	List<JobDriverMapping> findByDriverId(Long driverId);

}
