package com.checksammy.loca.repository;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.VisitSchedulerDate;

@Repository
public interface VisitSchedulerDateRepository extends JpaRepository<VisitSchedulerDate, Serializable>{

	@Modifying
	@Transactional
	@Query(value="DELETE FROM visit_scheduler_dates WHERE job_id =?1 AND checked = 0", nativeQuery = true)
	void deleteByJobId(Long id);

	List<VisitSchedulerDate> findByJobId(Long id);

	@Query(value="SELECT * FROM visit_scheduler_dates WHERE DATE(sch_current_date) = CURRENT_DATE()", nativeQuery = true)
	List<VisitSchedulerDate> findByCurrentDate();

	@Modifying
	@Transactional
	@Query(value="UPDATE visit_scheduler_dates SET checked = ?2, checked_date =?3, checked_by = ?4, check_by_name =?5 WHERE id =?1", nativeQuery = true)
	void updateCheckMark(Long jobScheduleId, Boolean checked, Instant checkDate, Long userId, String name);

	@Query(value="SELECT * FROM visit_scheduler_dates WHERE job_id=?1 AND checked = b'1'", nativeQuery = true)
	List<VisitSchedulerDate> findByJobIdOnlyCompleted(Long id);

	@Modifying
	@Transactional
	@Query(value="UPDATE visit_scheduler_dates SET notify = 1 WHERE id =?1", nativeQuery = true)
	void updateCompleteReminder(Long id);

	@Modifying
	@Transactional
	@Query(value="UPDATE visit_scheduler_dates SET driver_reminder = '2' WHERE id =?1", nativeQuery = true)
	void updateCustomerReminder(Long id);
	
	@Query(value="SELECT * FROM visit_scheduler_dates WHERE sch_current_date BETWEEN CAST(?1 AS DATE) AND CAST(?2 AS DATE)", nativeQuery = true)
	List<VisitSchedulerDate> findVisiterList(String startDate, String endDate);
	
	@Modifying
	@Transactional
	@Query(value="UPDATE visit_scheduler_dates SET notify = 1 WHERE id =?1", nativeQuery = true)
	void updateCompleteDriverReminder(Long id);

	@Modifying
	@Transactional
	@Query(value="UPDATE visit_scheduler_dates SET checked = ?2, checked_by = ?3, check_by_name =?4 WHERE id =?1", nativeQuery = true)
	void updateUnchecked(Long visitSchedulerId, Boolean checked, Long userId, String name);

	@Modifying
	@Transactional
	@Query(value="DELETE FROM visit_scheduler_dates WHERE id =?1", nativeQuery = true)
	void deleteByVisitId(Long visitId);

	@Query(value="SELECT * FROM visit_scheduler_dates WHERE job_id =?1 AND sch_current_date >= DATE(?2) AND checked = 0", nativeQuery = true)
	List<VisitSchedulerDate> findFutureVisit(Long jobId, Date date);

	@Query(value="SELECT * FROM visit_scheduler_dates WHERE job_id =?1 AND paid_on_flag = 0", nativeQuery = true)
	List<VisitSchedulerDate> findVisitByUpcoming(Long jobId, Date time);

	@Modifying
	@Transactional
	@Query(value="UPDATE visit_scheduler_dates SET paid_on_flag = 1 WHERE id IN (?1)", nativeQuery = true)
	void updatePaidOn(List<Long> visitId);

	@Query(value="SELECT * FROM visit_scheduler_dates WHERE id IN (?1) AND paid_on_flag = 0", nativeQuery = true)
	List<VisitSchedulerDate> findAllVisitWithComplete(List<Long> visitIds);

	@Modifying
	@Transactional
	@Query(value="UPDATE visit_scheduler_dates SET paid_on_flag = 1 WHERE id = ?1", nativeQuery = true)
	void updatePaidOnSingle(Long visitId);

	@Modifying
	@Transactional
	@Query(value="UPDATE visit_scheduler_dates SET paid_on_flag = 1 WHERE job_id = ?1 AND checked = 1", nativeQuery = true)
	void updatePaidOnForCompleted(Long jobId);


}
