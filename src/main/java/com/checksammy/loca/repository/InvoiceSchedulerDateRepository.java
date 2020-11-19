package com.checksammy.loca.repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.SortedSet;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.InvoiceSchedularDate;

@Repository
public interface InvoiceSchedulerDateRepository extends JpaRepository<InvoiceSchedularDate, Serializable> {
	
	@Modifying
	@Transactional
	@Query(value="DELETE FROM invoice_scheduler_dates WHERE job_id =?1 AND checked = 0", nativeQuery = true)
	void deleteByJobId(Long id);

	@Query(value="select * FROM invoice_scheduler_dates WHERE job_id =?1 AND checked = 0", nativeQuery = true)
	List<InvoiceSchedularDate> findInvoiceSchedule(Long jobId);

	@Query(value="select * FROM invoice_scheduler_dates WHERE job_id =?1 AND inv_sch_start_date >= DATE(?2)", nativeQuery = true)
	List<InvoiceSchedularDate> findInvoiceScheduleAndUpComingDate(Long jobId, Date currentDate);

}
