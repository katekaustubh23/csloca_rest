package com.checksammy.loca.repository;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.JobNotificationActionHistory;

@Repository
public interface JobNotificationActionHistoryRepository extends JpaRepository<JobNotificationActionHistory, Serializable>{

	@Query(value="SELECT * FROM job_notification_acton_history WHERE admin_id = ?1 AND job_id = ?2 AND msg_status = 1", nativeQuery = true)
	List<JobNotificationActionHistory> findAdminListAndJobId(Long adminId, Long jobId);

	@Modifying
	@Transactional
	@Query(value="UPDATE job_notification_acton_history SET msg_status = 0 WHERE job_id =?1 AND driver_id =?2", nativeQuery = true)
	void updateNotificationAll(Long jobId, Long driverId);

	@Query(value="SELECT * FROM job_notification_acton_history WHERE admin_id = ?1 AND msg_status = 1", nativeQuery = true)
	List<JobNotificationActionHistory> findByAdminId(Long adminId);

	@Modifying
	@Transactional
	@Query(value="UPDATE job_notification_acton_history SET driver_id = ?2 WHERE id =?1", nativeQuery = true)
	void updateDriverId(Long id, Long driverId);

}
