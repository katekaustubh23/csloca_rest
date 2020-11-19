package com.checksammy.loca.repository;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Serializable>{

	@Modifying
	@Transactional
	@Query(value="UPDATE invoice_job SET job_id =?2, user_id =?3, location_id =?4 WHERE id =?1 ", nativeQuery = true)
	void updateUserAndLocation(Long id, Long jobId, Long userId, Long locationId);

	@Modifying
	@Transactional
	@Query(value="UPDATE invoice_job SET is_deleted = 1 WHERE job_id =?1", nativeQuery = true)
	void deleteByJobId(Long jobId);

	@Modifying
	@Transactional
	@Query(value="UPDATE invoice_job SET status = ?2, work_status_id =?3, available_balance =?4, last_pay_date =?5,payment_from =?6  WHERE id =?1", nativeQuery = true)
	void updateInvoiceStatus(Long invoiceId, String status, Long statusId, Double double1, Instant paidDate, String string);

	@Modifying
	@Transactional
	@Query(value="UPDATE invoice_job SET status = ?2, work_status_id =?3 WHERE id =?1", nativeQuery = true)
	void updatePaymentStatus(Long invoiceId, String status, Long statusId);

	@Query(value="SELECT * FROM invoice_job WHERE job_id = ?1", nativeQuery = true)
	List<Invoice> findByJobId(Long jobId);

	@Query(value="SELECT * FROM invoice_job WHERE (created_ts BETWEEN CAST(?1 AS DATE) AND CAST(?2 AS DATE)) AND status = ?3 AND is_deleted = 0 ORDER BY last_pay_date DESC", nativeQuery = true)
	List<Invoice> findFilteredData(String startDate, String endDate, String completed);

}
