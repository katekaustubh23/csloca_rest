package com.checksammy.loca.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.JobForScheduler;

@Repository
public interface JobForSchedulerRepository extends JpaRepository<JobForScheduler, Serializable> {

//	@Query(value = "SELECT * FROM job WHERE id IN"
//			+ " (SELECT job_id FROM visit_scheduler_dates WHERE job_id IN"
//			+ " (SELECT job_id FROM job_driver_mapping WHERE driver_id = ?1 AND status = 'accept')"
//			+ " AND checked = 1) AND is_deleted = 0", nativeQuery = true)
	@Query(value="SELECT * FROM job WHERE id IN (SELECT job_id FROM job_driver_mapping WHERE "
			+ "driver_id = ?1 AND status = 'accept') AND job_status = 'mark_as_completed' AND is_deleted = 0",  nativeQuery = true)
	List<JobForScheduler> findJobHistoryListByDriverId(Long driverId);

}
