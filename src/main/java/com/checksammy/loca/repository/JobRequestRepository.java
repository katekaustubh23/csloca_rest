package com.checksammy.loca.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.JobRequest;

@Repository
public interface JobRequestRepository extends JpaRepository<JobRequest, Long>{

	@Modifying
	@Transactional
	@Query(value="UPDATE job_request SET is_deleted = 1 WHERE id =?1", nativeQuery = true)
	void updateDeleteTag(Long requestId);

	@Query(value="SELECT attachments FROM job_request WHERE id =?1", nativeQuery = true)
	String getRequestAttachment(Long requestId);

	@Modifying
	@Transactional
	@Query(value="UPDATE job_request SET attachments = ?2 WHERE id =?1", nativeQuery = true)
	void updateAttachmentsById(Long requestId, String attachmentsName);

	@Modifying
	@Transactional
	@Query(value="UPDATE job_request SET user_id = ?2 WHERE id =?1", nativeQuery = true)
	void updateRequestRowByUserId(Long requestId, Long userId);

	@Modifying
	@Transactional
	@Query(value="UPDATE job_request SET status = ?2 WHERE id =?1", nativeQuery = true)
	void updateStatus(Long jobRequestId, String status);

	@Modifying
	@Transactional
	@Query(value="UPDATE job_request SET location_id = ?2 WHERE id =?1", nativeQuery = true)
	void updateLocationId(Long id, Long locationId);

}
