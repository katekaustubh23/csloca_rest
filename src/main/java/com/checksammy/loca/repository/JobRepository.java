package com.checksammy.loca.repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.Job;
import com.checksammy.loca.model.VisitSchedulerDate;

@Repository
public interface JobRepository extends JpaRepository<Job, Serializable> {

	@Modifying
	@Transactional
	@Query(value = "UPDATE job SET location_id =?2 WHERE id =?1", nativeQuery = true)
	void updateLocationId(Long id, Long locationId);

	@Modifying
	@Transactional
	@Query(value = "UPDATE job SET is_deleted=1 WHERE id=?1", nativeQuery = true)
	void deleteToUpdate(Long jobId);

	@Modifying
	@Transactional
	@Query(value = "UPDATE job SET final_total =?2 WHERE id =?1", nativeQuery = true)
	void updateJobFinalTotal(Long jobId, Double finalTotal);

	@Modifying
	@Transactional
	@Query(value = "UPDATE job SET assign_member_ids =?2 WHERE id =?1", nativeQuery = true)
	void updateAssignUserNewList(Long id, String joinedList);

	@Query(value = "SELECT * FROM job WHERE id IN (SELECT job_id FROM job_driver_mapping WHERE driver_id =?1) AND is_deleted = 0", nativeQuery = true)
	List<Job> findByAssignDriverId(Long driverId);

	List<Job> findByUserId(Long customerId);

	@Modifying
	@Transactional
	@Query(value = "UPDATE job SET job_status =?2 WHERE id =?1", nativeQuery = true)
	void changeStatus(Long jobId, String changeStatus);

	@Modifying
	@Transactional
	@Query(value = "UPDATE job SET assign_user_reminder_type =?2 WHERE id =?1", nativeQuery = true)
	void updateJobDriverReminder(Long jobId, String assignUserReminderType);

	@Query(value = "SELECT rating FROM job WHERE id =?1 AND is_deleted = 0", nativeQuery = true)
	Double findRatingByJobId(Long jobId);

	@Modifying
	@Transactional
	@Query(value = "UPDATE job SET job_title =?2, job_instruction =?3,assign_member_ids =?4, team_assign_member_ids =?5 WHERE id =?1", nativeQuery = true)
	void updateJobTitleAndInstuction(Long jobId, String jobTitle, String jobInstruction, String driverId, String teamId);

	@Query(value = "SELECT * FROM job WHERE user_id =?1 AND is_deleted = 0", nativeQuery = true)
	List<Job> findByUserAndNonDeleteList(Long userId);

	@Query(value = "SELECT * FROM job WHERE id IN (SELECT job_id FROM visit_scheduler_dates WHERE id =?1) AND is_deleted = 0", nativeQuery = true)
	Job findByVisitId(Long visitSchedulerId);

}
