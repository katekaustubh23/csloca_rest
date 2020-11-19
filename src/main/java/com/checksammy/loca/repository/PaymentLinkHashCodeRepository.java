package com.checksammy.loca.repository;

import java.io.Serializable;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.PaymentLinkHashCode;

@Repository
public interface PaymentLinkHashCodeRepository extends JpaRepository<PaymentLinkHashCode, Serializable>{

	@Transactional
	@Modifying
	@Query(value="UPDATE payment_link_code SET is_deleted = 1 WHERE hash_code =?1", nativeQuery = true)
	void updateDeleteTag(String hashCode);

	@Query(value="SELECT * FROM payment_link_code WHERE hash_code =?1 AND is_deleted = 0", nativeQuery = true)
	PaymentLinkHashCode findByHashCode(String hashCode);

	@Transactional
	@Modifying
	@Query(value="DELETE FROM payment_link_code WHERE invoice_id =?1 AND job_id =?2 AND hash_code =?3 AND is_deleted = 0", nativeQuery = true)
	void deleteByAllSameParam(Long invoiceId, Long jobId, String hashCode);

	@Query(value="SELECT CASE WHEN COUNT(id) > 0 THEN TRUE ELSE FALSE END FROM payment_link_code WHERE hash_code =?1 AND is_deleted = 1", nativeQuery = true)
	Integer existsData(String hashCode);

	@Transactional
	@Modifying
	@Query(value="DELETE FROM payment_link_code WHERE invoice_id =?1 AND job_id =?2", nativeQuery = true)
	void deleteByInvoiceAndJobId(Long invoiceId, Long jobId);

}
