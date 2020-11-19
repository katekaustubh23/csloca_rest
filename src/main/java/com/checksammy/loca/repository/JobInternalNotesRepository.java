package com.checksammy.loca.repository;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.checksammy.loca.model.JobInternalNotes;

@Repository
public interface JobInternalNotesRepository extends JpaRepository<JobInternalNotes, Serializable> {

	@Query(value = "SELECT attachment FROM job_internal_notes WHERE id =?1", nativeQuery = true)
	String getRequestAttachment(Long internalNotesId);

	@Modifying
	@Transactional
	@Query(value = "UPDATE job_internal_notes SET attachment = ?2 WHERE id =?1", nativeQuery = true)
	void updateAttachmentsById(Long internalNotesId, String attachmentsName);

	@Query(value = "SELECT * FROM job_internal_notes WHERE job_id =?1", nativeQuery = true)
	List<JobInternalNotes> findByJobId(Long jobId);

	@Query(value = "SELECT * FROM job_internal_notes WHERE job_id =?1 AND internal_link LIKE ?2", nativeQuery = true)
	List<JobInternalNotes> findByjobIdAndType(Long jobId, String type);

	@Modifying
	@Transactional
	@Query(value = "UPDATE job_internal_notes SET notes = ?2, attachment = ?3 ,updated_by =?4, updated_ts = ?5  WHERE id =?1", nativeQuery = true)
	void updateNoteData(Long id, String notes, String attachment, Long updatedBy, Instant updateDate);

}
